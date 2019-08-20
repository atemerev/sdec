package ai.reactivity.sdec

import org.scalatest.FunSuite
import Decimal._

class DecimalTests extends FunSuite {
  test("basic1") {
    val d1 = 0.2 * 0.2
    val d2 = 0.04
    val r1: Decimal = 0.2 * 0.2
    val r2: Decimal = 0.04
    assert(d1 !== d2, "double inexact")
    assert(r1 === r2, "decimal exact")
  }

  test("map key") {
    val map = collection.mutable.Map.empty[Decimal, Decimal]

    map += ((0.2 * 0.2).toDecimal() -> 0.04)
  }
}

