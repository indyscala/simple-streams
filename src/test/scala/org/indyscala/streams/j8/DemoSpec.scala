package org.indyscala.streams.j8

import org.indyscala.streams.support.UnitSpec

class DemoSpec extends UnitSpec("JAVA 8") {

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
