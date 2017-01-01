package com.jamondouglas.domainmodeling.model.service
import com.jamondouglas.domainmodeling.model.Account.Account.{Account, Amount, Balance}
import java.util.{Calendar, Date}
//import com.jamondouglas.domainmodeling.model.Account.Account.Account._
/**
  * Created by jamon on 12/18/16.
  */

trait AccountService {
  def open(no:String, name:String, asOf:Date): Option[Account]

  def close(account: Account, closingDate:Option[Date]): Option[Account]

  def debit(amount: Amount, acct:Account): Option[Account]

  def credit(amount: Amount, acct: Account): Option[Account]

  def balance(acct:Account):Option[Balance]

  def transfer(debtor:Account, creditor:Account, amount:Amount):Option[(Account, Account, Amount)]
}

object AccountService {
  def today = Calendar.getInstance().getTime
  def open(no:String, name: String, openingDate:Option[Date]): Option[Account] = {
    if(no.isEmpty || name.isEmpty) None
    if(openingDate.getOrElse(today) before today) None
    else Some(Account(no,name,openingDate.get,None, Balance(0)))
  }

  def debit(acct: Account, amount: Amount):Option[Account]= {
    if(amount > acct.balance.amount) None
    else
      Some(
        Account(
          acct.no,
          acct.name,
          acct.created,
          Option(System.currentTimeMillis()),
          Balance(acct.balance.amount - amount)))
  }
  def credit(acct: Account, amount: BigDecimal): Account = Account(acct.no, acct.name, acct.created,Some(System.currentTimeMillis()),Balance(amount + acct.balance.amount))

  def balance(acct:Account):Option[Balance] = Some(acct.balance)

  def transfer( from:Account, to:Account, amount:Amount):Option[(Account, Account, Amount)] = for {
    debitAcct <- debit(from, amount)
    creditAcct <- credit(to, amount)
  } yield Some(debitAcct, creditAcct, amount)
}
