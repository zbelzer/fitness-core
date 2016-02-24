name := "fitness-core"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.7"

lazy val core = project in file(".")

initialCommands in console := "import com.fivebyfive.fitness.model._;"
initialCommands in console += "import com.fivebyfive.fitness.strategy._;"
initialCommands in console += "import com.fivebyfive.fitness.application._;"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test"
libraryDependencies += "com.github.nscala-time" %% "nscala-time" % "2.6.0"
libraryDependencies += "com.nrinaudo" %% "tabulate" % "0.1.7"
libraryDependencies += "org.apache.commons" % "commons-math3" % "3.3"
libraryDependencies += "com.github.wookietreiber" %% "scala-chart" % "latest.integration"
libraryDependencies += "com.beachape" %% "enumeratum" % "1.3.7"
libraryDependencies += "com.github.nscala-time" %% "nscala-time" % "2.6.0"
