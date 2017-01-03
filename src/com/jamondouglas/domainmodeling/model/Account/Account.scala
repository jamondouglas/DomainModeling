package com.jamondouglas.domainmodeling.model.Account

import java.util.{Date, Calendar}

import com.jamondouglas.domainmodeling.model.Account.common.Amount

/**
  * Created by jamon on 12/18/16.
  */

object common {
  type Amount = BigDecimal
  case class Balance(amt: Amount)
  def today = Calendar.getInstance.getTime

}
import common._
// sealed class can only be extended in same file
sealed trait Account{
  def no: String
  def name: String
  def dateOpen: Option[Date]
  def dateClosed: Option[Date]
  def balance: Balance
}
case class CheckingAccount(no: String, name: String, dateOpen:Option[Date], dateClosed:Option[Date], balance: Balance) extends Account
case class SavingsAccount(no:String, name:String, dateOpen:Option[Date], dateClosed: Option[Date], balance: Balance) extends Account

object Account {
  //  case class Account(no: String, name: String, created:Date, as_of: Option[Long], balance:Balance)
  def makeCheckingAccount(no:String,  name:String, dateOpen: Option[Date], dateClosed: Option[Date], balance: Balance): Either[Error,CheckingAccount] = {
    if(dateClosed.get before dateOpen.getOrElse(today)) Left(new Error("Closing date can't be before openind date"))
    else Right(CheckingAccount(no, name, dateOpen, dateClosed, balance))
  }

  def makeSavingAccountt(no:String,  name:String, dateOpen: Option[Date], dateClosed: Option[Date], balance: Balance ): Either[Error,SavingsAccount] = {
    if(dateClosed.get before dateOpen.getOrElse(today)) Left(new Error("Closing date can't be before openind date"))
    else Right(SavingsAccount(no, name, dateOpen, dateClosed, balance))
  }
}
