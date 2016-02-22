name := "fitness"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.7"

lazy val core = Project(id = "core", base = file("fitness-core"))
lazy val ui = Project(id = "ui", base = file("fitness-ui")).dependsOn(core)
