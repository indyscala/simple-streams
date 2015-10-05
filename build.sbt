organization := "org.indyscala.streams"

name := "indyscala-streams"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.7"

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

libraryDependencies ++= Seq(
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.6.2"
  , "org.json4s" %% "json4s-native" % "3.3.0"
  , "org.slf4j" % "slf4j-api" % "1.7.12"

  // test deps
  , "org.scalatest" %% "scalatest" % "2.2.4" % "test"
  , "org.slf4j" % "slf4j-log4j12" % "1.7.12"
)
