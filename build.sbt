import Dependencies.dependencies
import Settings.commonSettings

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .enablePlugins(JavaAppPackaging)
  .enablePlugins(DockerPlugin)
  .settings(commonSettings)
  .settings(
    name := "meetup-service",
    libraryDependencies ++= dependencies
  )