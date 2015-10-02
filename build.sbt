organization := "org.indyscala.streams"

name := "indyscala-streams"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.7"

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

libraryDependencies ++= Seq(
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.6.2"
  , "org.scalatest" %% "scalatest" % "2.2.4" % "test"
)
