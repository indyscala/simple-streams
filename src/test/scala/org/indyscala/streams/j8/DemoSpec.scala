package org.indyscala.streams.j8

import org.indyscala.streams.support.StreamSupport._

import org.scalatest.{FlatSpec, Matchers}

class DemoSpec extends FlatSpec with Matchers {
  "Finding inputs" should "happen quickly (5 runs < 50ms)" in {
    val runs = 5
    val t0 = System.currentTimeMillis
    for (i <- 0 until runs) findInputs
    val elapsed = System.currentTimeMillis - t0
    // elapsed should be < 50 // won't compile; missing implicit conversion? (discuss)
    elapsed.toInt should be < 50
  }

  "Counting lines" should "complete without error" in {
    val lines = Demo.countLines()
    info(s"counted $lines lines")
  }
}
