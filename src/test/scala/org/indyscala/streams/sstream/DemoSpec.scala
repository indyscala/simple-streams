package org.indyscala.streams.sstream

import org.indyscala.streams.support.UnitSpec

class DemoSpec extends UnitSpec("SSTREAMS") {

  "Counting lines" should "complete without error" in {
    val count = Demo.countLines()
    info(s"counted $count lines")
  }
}
