import sbt._
import sbt.Keys._

object SampleBuild extends Build {

  lazy val root = Project(
    id = "scala-parser-study",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      organization := "com.github.j5ik2o",
      version := "0.0.1-SNAPSHOT",
      scalaVersion := "2.10.3",
      libraryDependencies ++= Seq(
        "org.specs2" %% "specs2" % "2.3.4" % "test"
      )
    )
  )
}
