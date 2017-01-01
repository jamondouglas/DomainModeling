package com.jamondouglas.typeclasses

import com.jamondouglas.typeclasses.ADT.SemiGroup

/**
  * Created by jamon on 12/14/16.
  */

object ADT {
  def isIdempotent[A](func: A => A, a: A): Boolean = {
    val op = func(a)
    val op2 = func compose func
    op == op2(a)
  }

  trait SemiGroup[A] {
    def append(a: A, b: A): A
  }

}


object Main extends App {
  case class Person(name: String, age: Int)
  implicit val personSemiGroup = new SemiGroup[Person] {
    override def append(a: Person, b: Person): Person = Person(a.name, a.age + b.age)
  }
  println("What kind of person ::"+ personSemiGroup.append(Person("jam",35), Person("Kati",29)))
}
