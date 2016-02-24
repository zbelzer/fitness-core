name := """fitness-ui"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.7"

lazy val core = project in file("../fitness-core")
lazy val ui = (project in file(".")).enablePlugins(PlayScala).dependsOn(core)

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  specs2 % Test
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"


// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
