package org.indyscala.streams.scala

import org.indyscala.streams.support.UnitSpec

class DemoSpec extends UnitSpec("SCALA") {

  "Stream setup" should "happen quickly (5 runs < 50ms)" in {
    val runs = 5

    val t0 = System.currentTimeMillis
    for (i <- 1 to runs) Demo.prepareLines
    val elapsed = System.currentTimeMillis - t0

    info(s"$elapsed ms for $runs runs (${UnitSpec.inputCount} inputs per run)")
    elapsed.toInt should be < 50
  }

  "Counting lines" should "complete without error" in {
    val r = Demo.countLines()
    info(s"counted ${r.count.get} lines")
  }
}
