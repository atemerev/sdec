lazy val scala212 = "2.12.8"
lazy val scala213 = "2.13.0"
lazy val supportedScalaVersions = List(scala213, scala212)

ThisBuild / organization := "ai.reactivity"
ThisBuild / version      := "1.0.0"
ThisBuild / scalaVersion := scala213

lazy val sdec = (project in file("."))
  .settings(
    crossScalaVersions := supportedScalaVersions
  )

