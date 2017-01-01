package com.jamondouglas.domainmodeling.model.Account

import java.util.Date

/**
  * Created by jamon on 12/18/16.
  */


trait Account{
  type Amount = BigDecimal
}

case class X extends Account

object Account {
  type Amount = BigDecimal
  case class Balance(val amount:Amount)
  case class Account(no: String, name: String, created:Date, as_of: Option[Long], balance:Balance)
}
