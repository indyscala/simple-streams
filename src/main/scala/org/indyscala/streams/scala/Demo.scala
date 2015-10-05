package org.indyscala.streams.scala

import org.json4s.JsonAST.JValue

import scala.util.Try

object Demo {
  def countLines(): Result = {
    val count = prepareLines()
      .count(allElements)

    Result(
      count=Some(count))
  }

  def countJson(): Result = {
    val count = prepareJson()
      .count(allElements)

    Result(
      count=Some(count))
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

  private def prepareJson(): Iterator[Try[JValue]] = {
    prepareLines()
      .map(parseJson(_))
  }

  private def parseJson(json: String): Try[JValue] = {
    import org.json4s._
    import org.json4s.jackson.JsonMethods._
    implicit val formats = DefaultFormats

    Try(parse(json))
  }

  private def allElements[A](x: A): Boolean = true

  case class Result(count: Option[Long] = None, parsed: Option[Long] = None, errors: Option[Long] = None)
}
