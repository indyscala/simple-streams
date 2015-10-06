package org.indyscala.streams.sstream

import org.indyscala.streams.support.StreamSupport.findInputs

import org.json4s.JsonAST.JValue

import scala.collection.JavaConverters._
import scala.io.Codec.UTF8

import scalaz.concurrent.Task
import scalaz.stream._
import scalaz.{-\/,\/-}

object Demo {
  val NoLines: Process[Task,String] = Process.halt

  def countLines(): Result = {
    val lines: Process[Task,String] = findInputs   // findInputs is Iterable[InputStream]
      .asScala
      .foldLeft(NoLines)(_ ++ io.linesR(_)(UTF8))

    val counter: Task[Option[Int]] = lines
      .map(_ => 1)
      .sum
      .runLast

    val count = counter.run

    Result(
      count=count.map(_.toLong))
  }

  def countJson(): Result = {
    val lines: Process[Task,String] = findInputs
      .asScala
      .foldLeft(NoLines)(_ ++ io.linesR(_)(UTF8))

    // The approach here is naive.  runLog() hangs on to
    // the entire stream.
    val counter = lines
      .map(parseJson)
      .map(_ => 1)
      .sum
      .runLog           // Don't do this for big streams if
                        // you care about memory use!

    // Hit the edge of my scalaz-stream knowledge.  The code
    // below is wrong.
    val count: Int = counter.attemptRun match {
      case -\/ (t) => -1
      case \/- (a) => a.head
    }

    Result(
      count=Some(count.toLong))
  }

  private def parseJson(json: String): JValue = {
    import org.json4s._
    import org.json4s.jackson.JsonMethods._
    implicit val formats = DefaultFormats

    parse(json)
  }

  case class Result(count: Option[Long] = None, parsed: Option[Long] = None, errors: Option[Long] = None)
}
