import sbt._

object Dependencies {
  val CatsCore = "2.7.0"
  val CatsEffect = "3.3.11"
  val DockerVersion = "8.16.0"
  val CatsVersion = "2.7.0"
  val TapirVersion = "1.0.0-M8"
  val Http4sVersion = "0.23.11"
  val CirceVersion = "0.14.1"
  val DoobieVersion = "1.0.0-RC1"
  val NscalaTimeVersion = "2.30.0"
  val ScalatestVersion = "3.2.12"

  val dependencies: Seq[ModuleID] =
    Seq(
      "com.github.nscala-time" %% "nscala-time" % NscalaTimeVersion,
      "org.typelevel" %% "cats-core" % CatsCore,
      "org.typelevel" %% "cats-effect" % CatsEffect,
      "com.spotify" % "docker-client" % DockerVersion,
      "org.typelevel" %% "cats-core" % CatsVersion,
      "com.softwaremill.sttp.tapir" % "tapir-core_2.13" % TapirVersion,
      "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-bundle" % TapirVersion,
      "com.softwaremill.sttp.tapir" %% "tapir-http4s-server" % TapirVersion,
      "com.softwaremill.sttp.tapir" %% "tapir-json-circe" % TapirVersion,
      "org.http4s" %% "http4s-ember-server" % Http4sVersion,
      "org.http4s" %% "http4s-ember-client" % Http4sVersion,
      "org.http4s" %% "http4s-circe" % Http4sVersion,
      "org.http4s" %% "http4s-dsl" % Http4sVersion,
      "io.circe" %% "circe-generic" % CirceVersion,
      "org.tpolecat" %% "doobie-hikari" % DoobieVersion,
      "org.tpolecat" %% "doobie-core" % DoobieVersion,
      "org.tpolecat" %% "doobie-postgres" % DoobieVersion,
      "org.tpolecat" %% "doobie-specs2" % DoobieVersion,
      "org.scalactic" %% "scalactic" % ScalatestVersion,
      "org.scalatest" %% "scalatest" % ScalatestVersion % "test"
    )
}
