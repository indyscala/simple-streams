package org.indyscala.streams.sstream

import org.indyscala.streams.support.StreamSupport.findInputs

import scala.collection.JavaConverters._
import scala.io.Codec.UTF8

import scalaz.concurrent.Task
import scalaz.stream._

object Demo {

  def countLines(): Int = {
    val empty: Process[Task,String] = Process()
    val lines: Process[Task,String] = findInputs   // findInputs is Iterable[InputStream]
      .asScala
      .foldLeft(empty)(_ ++ io.linesR(_)(UTF8))

    val countLines: Task[Option[Int]] = lines
      .map(_ => 1)
      .sum
      .runLast

    countLines.run.getOrElse(-1)
  }
}
