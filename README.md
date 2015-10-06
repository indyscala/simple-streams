# Simple Streaming Demos: Java 8 Stream, Scala Iterable and scalaz-stream

## Disclaimer

*Caution*: The code here was written as I explored Java 8 [Streams](http://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html), Scala [Iterators](http://www.scala-lang.org/api/current/index.html#scala.collection.Iterator) and [scalaz-stream](https://github.com/scalaz/scalaz-stream), each for the first time.  It contains mistakes, memory leaks, and other gremlins.  We discussed several of them during the [October Indy Scala meeting](http://www.meetup.com/IndyScala/events/224887884/).

## Prerequisites

 * Java 8 JDK
 * sbt 0.13.x

## Examples

### Run Test Suite

```
$ export FILES=src/test/resources/mock_energy_monitor_data.json.gz
$ sbt
> test

[info] DemoSpec:
[info] + ==============================  JAVA 8  ============================== 
[info] Finding inputs
[info] - should happen quickly (5 runs < 50ms) *** FAILED ***
[info]   57 was not less than 50 (UnitSpec.scala:16)
[info]   + 57 ms for 5 runs (1 inputs per run) 
[info] Stream setup
[info] - should happen quickly (5 runs < 150ms) *** FAILED ***
[info]   261 was not less than 150 (DemoSpec.scala:16)
[info]   + 261 ms for 5 runs (5 inputs per run) 
[info] Counting lines
[info] - should complete without error
[info]   + counted 1000 lines 
[info] Parsing JSON (simple)
[info] - should complete without error
[info]   + counted 1000 lines 
[info] Parsing JSON (grouped)
[info] - should complete without error
[info]   + counted 1000 lines 
[info]   + parsed 999 lines 
[info]   + errors 1 
[info] DemoSpec:
[info] + ==============================  SCALA  ============================== 
[info] Finding inputs
[info] - should happen quickly (5 runs < 50ms) *** FAILED ***
[info]   57 was not less than 50 (UnitSpec.scala:16)
[info]   + 57 ms for 5 runs (1 inputs per run) 
[info] Stream setup
[info] - should happen quickly (5 runs < 50ms)
[info]   + 33 ms for 5 runs (1 inputs per run) 
[info] Counting lines
[info] - should complete without error
[info]   + counted 1000 lines 
[info] Parsing JSON (simple)
[info] - should complete without error
[info]   + counted 1000 lines 
[info] Parsing JSON (grouped)
[info] - should complete without error
[info]   + counted 1000 lines 
[info]   + parsed 999 lines 
[info]   + errors 1 
[info] DemoSpec:
[info] + ==============================  SSTREAMS  ============================== 
[info] Finding inputs
[info] - should happen quickly (5 runs < 50ms) *** FAILED ***
[info]   56 was not less than 50 (UnitSpec.scala:16)
[info]   + 56 ms for 5 runs (1 inputs per run) 
[info] Counting lines
[info] - should complete without error
[info]   + counted 1000 lines 
[info] Parsing JSON (simple)
[info] - should complete without error
[info]   + counted -1 lines 
[info] Run completed in 849 milliseconds.
[info] Total number of tests run: 13
[info] Suites: completed 3, aborted 0
[info] Tests: succeeded 9, failed 4, canceled 0, ignored 0, pending 0
[info] *** 4 TESTS FAILED ***
[error] Failed tests:
```

### Single Test Spec with Timing

```
$ export FILES=src/test/resources/mock_energy_monitor_data.json.gz
$ sbt
> test-only org.indyscala.streams.j8.DemoSpec -- -oD

[info] DemoSpec:
[info] + ==============================  JAVA 8  ============================== 
[info] Finding inputs
[info] - should happen quickly (5 runs < 50ms) (50 milliseconds)
[info]   + 39 ms for 5 runs (1 inputs per run) 
[info] Stream setup
[info] - should happen quickly (5 runs < 150ms) (115 milliseconds)
[info]   + 115 ms for 5 runs (5 inputs per run) 
[info]   +  "less lazy" than scala version 
[info] Counting lines
[info] - should complete without error (2 milliseconds)
[info]   + counted 1000 lines 
[info] Parsing JSON (simple)
[info] - should complete without error (32 milliseconds)
[info]   + counted 1000 lines 
[info] Parsing JSON (grouped)
[info] - should complete without error (10 milliseconds)
[info]   + counted 1000 lines 
[info]   + parsed 999 lines 
[info]   + errors 1 
[info] Run completed in 331 milliseconds.
[info] Total number of tests run: 5
[info] Suites: completed 1, aborted 0
[info] Tests: succeeded 5, failed 0, canceled 0, ignored 0, pending 0
[info] All tests passed.
[success] Total time: 1 s, completed Oct 6, 2015 3:16:36 PM
```

## Java

Java version I used during [the presentation](http://www.meetup.com/IndyScala/events/224887884/):

```
$ java -version
openjdk version "1.8.0_60"
OpenJDK Runtime Environment (build 1.8.0_60-b24)
OpenJDK 64-Bit Server VM (build 25.60-b23, mixed mode)
```
