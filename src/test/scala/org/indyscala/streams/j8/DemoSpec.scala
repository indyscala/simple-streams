package org.indyscala.streams.j8

import scala.collection.JavaConversions._

import org.indyscala.streams.support.StreamSupport._

import org.scalatest.{FlatSpec, Matchers}

class DemoSpec extends FlatSpec with Matchers {
  "Finding inputs" should "happen quickly (5 runs < 50ms)" in {
    val runs = 5
    var inputCount = 0

    val t0 = System.currentTimeMillis

    // val inputCount = runs.foldLeft(0)((sum, _) => sum + findInputs.size)
    for (i <- 1 to runs) {
      inputCount += findInputs.size
    }

    val elapsed = System.currentTimeMillis - t0

    info(s"$elapsed ms for $inputCount files across $runs runs (${inputCount / runs} per run)")
    // elapsed should be < 50 // won't compile; missing implicit conversion? (discuss)
    elapsed.toInt should be < 50
  }

  "Counting lines" should "complete without error" in {
    val lines = Demo.countLines()
    info(s"counted $lines lines")
  }
}
