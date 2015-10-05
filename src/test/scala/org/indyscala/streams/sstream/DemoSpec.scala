package org.indyscala.streams.sstream

import org.indyscala.streams.support.UnitSpec

class DemoSpec extends UnitSpec("SSTREAMS") {

  "Counting lines" should "complete without error" in {
    val r = Demo.countLines()
    info(s"counted ${r.count.get} lines")
  }

  "Parsing JSON (simple)" should "complete without error" in {
    val r = Demo.countJson()
    info(s"counted ${r.count.get} lines")
  }
}
