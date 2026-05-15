ThisBuild / organization := "org.apache.iceberg"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.14"

ThisBuild / resolvers += "Apache Iceberg 1.10.2 staging" at "https://repository.apache.org/content/repositories/orgapacheiceberg-1281/"

lazy val icebergVersion = "1.10.2"

lazy val root = (project in file("."))
  .settings(
    name := "iceberg-scala",
    libraryDependencies ++= Seq(
      "org.apache.iceberg" % "iceberg-api" % icebergVersion,
      "org.apache.iceberg" % "iceberg-core" % icebergVersion
    )
  )
