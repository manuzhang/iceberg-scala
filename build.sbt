ThisBuild / organization := "org.apache.iceberg"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.14"

lazy val root = (project in file("."))
  .settings(
    name := "iceberg-scala",
    libraryDependencies ++= Seq(
      "org.apache.iceberg" % "iceberg-api" % "1.6.1",
      "org.apache.iceberg" % "iceberg-core" % "1.6.1"
    )
  )
