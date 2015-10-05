package org.indyscala.streams.scala

object Demo {
  def countLines(): Result = {
    val count = prepareLines().count((String) => true)
    Result(count=Some(count))
  }

  def prepareLines(): Iterator[String] = {
    import org.indyscala.streams.support.StreamSupport.findInputs
    import scala.collection.JavaConverters._
    import scala.io.Source

    val empty = Set[String]().iterator
    findInputs
      .asScala
      .map(Source.fromInputStream(_).getLines())
      .foldLeft(empty)(_ ++ _)
  }

  case class Result(count: Option[Long] = None, parsed: Option[Long] = None, errors: Option[Long] = None)
}
