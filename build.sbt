ThisBuild / organization := "org.apache.iceberg"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.14"

ThisBuild / resolvers += "Apache Iceberg 1.11.0 staging" at "https://repository.apache.org/content/repositories/orgapacheiceberg-1282"

lazy val root = (project in file("."))
  .settings(
    name := "iceberg-scala",
    libraryDependencies ++= Seq(
      "org.apache.iceberg" % "iceberg-api" % "1.11.0",
      "org.apache.iceberg" % "iceberg-core" % "1.11.0"
    )
  )
