name := """play-java"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.11"

//libraryDependencies += javaJdbc
libraryDependencies += cache
//libraryDependencies += javaWs

// for Facebook login demo
libraryDependencies += "com.github.scribejava" % "scribejava-core" % "4.1.1"
libraryDependencies += "com.github.scribejava" % "scribejava-apis" % "4.1.1"
libraryDependencies += "com.github.scribejava" % "scribejava" % "4.1.1"


