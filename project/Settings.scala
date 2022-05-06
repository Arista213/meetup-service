import com.typesafe.sbt.SbtNativePackager.Docker
import com.typesafe.sbt.packager.Keys.{daemonUser, dockerBaseImage, dockerExposedPorts, packageName}
import sbt.Keys._
import sbt._

object Settings {

  val commonSettings =
    Seq(
      scalaVersion := "2.13.8",
      packageName in Docker := "meetup-service",
      version := (version in ThisBuild).value,
      version in Docker := version.value,
      dockerBaseImage := "openjdk:11-jre",
      dockerExposedPorts := Seq(8080),
      daemonUser in Docker := "root",
      Compile / mainClass := Some("Main")
    )
}
