package org.indyscala.streams.j8

import org.indyscala.streams.support.UnitSpec

class DemoSpec extends UnitSpec("JAVA 8") {

  "Stream setup" should "happen quickly (5 runs < 150ms)" in {
    val runs = 5

    val t0 = System.currentTimeMillis
    for (i <- 1 to runs) Demo.prepareLines
    val elapsed = System.currentTimeMillis - t0

    info(s"$elapsed ms for $runs runs (5 inputs per run)")
    // elapsed should be < 150 // won't compile; missing implicit conversion? (discuss)
    elapsed.toInt should be < 150
    info(""" "less lazy" than scala version""")
  }

  "Counting lines" should "complete without error" in {
    val r = Demo.countLines()
    info(s"counted ${r.getCount()} lines")
  }

  "Parsing JSON (simple)" should "complete without error" in {
    val r = Demo.countJson()
    info(s"counted ${r.getCount()} lines")
  }

  "Parsing JSON (grouped)" should "complete without error" in {
    val r = Demo.countJsonGrouped()
    info(s"counted ${r.getCount()} lines")
    info(s"parsed ${r.get("parsed")} lines")
    info(s"errors ${r.get("errors")}")
  }
}
