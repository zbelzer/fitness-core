name := "fitness-core"
organization := "com.fivebyfive"

scalaVersion := "2.11.8"
scalacOptions ++= Seq("-deprecation", "-feature")

lazy val core = project in file(".")

initialCommands in console := "import com.fivebyfive.fitness.model._;"
initialCommands in console += "import com.fivebyfive.fitness.strategy._;"
initialCommands in console += "import com.fivebyfive.fitness.application._;"

publishTo := Some(Resolver.file("file",  new File(Path.userHome.absolutePath+"/.m2/repository")))

libraryDependencies ++= Seq(
  "commons-io" % "commons-io" % "2.4",
  "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test",
  "com.github.nscala-time" %% "nscala-time" % "2.6.0",
  "com.nrinaudo" %% "kantan.csv-generic" % "0.1.13",
  "com.nrinaudo" %% "kantan.csv-joda-time" % "0.1.13",
  "org.apache.commons" % "commons-math3" % "3.3",
  "com.github.wookietreiber" %% "scala-chart" % "latest.integration",
  "com.beachape" %% "enumeratum" % "1.3.7",
  "com.github.nscala-time" %% "nscala-time" % "2.6.0",
  "io.backchat.inflector" %% "scala-inflector" % "1.3.5",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.4.0"
)
