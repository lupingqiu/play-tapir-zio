name := """play-tapir-zio"""
organization := "com.qiulp"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.6"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test

libraryDependencies ++= Seq(
  "mysql" % "mysql-connector-java" % "8.0.17",
  "io.getquill" %% "quill-jdbc" % "3.9.0",
  "io.getquill" %% "quill-jdbc-zio" % "3.9.0",
  "io.getquill" %% "quill-zio" % "3.9.0",
  evolutions,jdbc
)


//libraryDependencies += "dev.zio" %% "zio" % "1.0.0-RC12-1"
libraryDependencies += "dev.zio" %% "zio" % "1.0.9"


val tapirVersion = "0.19.0-M4"
// Tapir
libraryDependencies += "com.softwaremill.sttp.tapir" %% "tapir-core" % tapirVersion
libraryDependencies += "com.softwaremill.sttp.tapir" %% "tapir-play-server" % tapirVersion
  // Tapir OpenAPI
libraryDependencies += "com.softwaremill.sttp.tapir" %% "tapir-openapi-docs" % tapirVersion
libraryDependencies += "com.softwaremill.sttp.tapir" %% "tapir-openapi-circe-yaml" % tapirVersion
libraryDependencies += "com.softwaremill.sttp.tapir" %% "tapir-json-circe" % tapirVersion
libraryDependencies += "com.softwaremill.sttp.tapir" %% "tapir-json-play" % tapirVersion

// Swagger UI for Play
// You can also host Swagger UI by yourself and get rid of this dependency
libraryDependencies += "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-play" % tapirVersion
//libraryDependencies += "com.softwaremill.sttp.tapir" %% "tapir-zio" % tapirVersion  excludeAll(
//  ExclusionRule(organization = "dev.zio")
//)


//libraryDependencies += "com.fasterxml.jackson.dataformat" % "jackson-dataformat-yaml" % "2.10.5"


// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.qiulp.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.qiulp.binders._"
