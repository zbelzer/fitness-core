name := "fitness-core"
organization := "com.fivebyfive"

scalaVersion := "2.11.7"
scalacOptions ++= Seq("-deprecation", "-feature")

lazy val core = project in file(".")

initialCommands in console := "import com.fivebyfive.fitness.model._;"
initialCommands in console += "import com.fivebyfive.fitness.strategy._;"
initialCommands in console += "import com.fivebyfive.fitness.application._;"

import sbtrelease.ReleasePlugin.autoImport._
import sbtrelease.ReleaseStateTransformations._

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,              // : ReleaseStep
  inquireVersions,                        // : ReleaseStep
  runTest,                                // : ReleaseStep
  setReleaseVersion,                      // : ReleaseStep
  commitReleaseVersion,                   // : ReleaseStep, performs the initial git checks
  tagRelease,                             // : ReleaseStep
  setNextVersion,                         // : ReleaseStep
  commitNextVersion,                      // : ReleaseStep
  pushChanges                             // : ReleaseStep, also checks that an upstream branch is properly configured
)

libraryDependencies ++= Seq(
  "commons-io" % "commons-io" % "2.4",
  "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test",
  "com.github.nscala-time" %% "nscala-time" % "2.6.0",
  "com.nrinaudo" %% "tabulate" % "0.1.7",
  "org.apache.commons" % "commons-math3" % "3.3",
  "com.github.wookietreiber" %% "scala-chart" % "latest.integration",
  "com.beachape" %% "enumeratum" % "1.3.7",
  "com.github.nscala-time" %% "nscala-time" % "2.6.0",
  "io.backchat.inflector" %% "scala-inflector" % "1.3.5"
)
