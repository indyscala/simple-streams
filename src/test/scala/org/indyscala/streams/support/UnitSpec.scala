package org.indyscala.streams.support

import org.scalatest.{FlatSpec, Matchers}

abstract class UnitSpec(tech: String) extends FlatSpec with Matchers {
  alert(s"==============================  $tech  ==============================")

  "Finding inputs" should "happen quickly (5 runs < 50ms)" in {
    val runs = 5

    val t0 = System.currentTimeMillis
    for (i <- 1 to runs) StreamSupport.findInputs
    val elapsed = System.currentTimeMillis - t0

    info(s"$elapsed ms for $runs runs (${UnitSpec.inputCount} inputs per run)")
    elapsed.toInt should be < 50
  }
}

object UnitSpec {
  import scala.collection.JavaConversions._

  val inputCount = StreamSupport.findInputs.size
}
