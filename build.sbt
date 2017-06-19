name := """play-java"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.11"

//libraryDependencies += javaJdbc
libraryDependencies += cache
//libraryDependencies += javaWs

// for Facebook OAuth login demo and also OAuth library we use for Google: scribe (https://github.com/scribejava/scribejava)
libraryDependencies += "com.github.scribejava" % "scribejava-core" % "4.1.1"
libraryDependencies += "com.github.scribejava" % "scribejava-apis" % "4.1.1"
libraryDependencies += "com.github.scribejava" % "scribejava" % "4.1.1"

// for Gmail OAuth demo and some API examples
libraryDependencies += "com.google.apis" % "google-api-services-gmail" % "v1-rev67-1.22.0"
libraryDependencies += "com.google.apis" % "google-api-services-plus" % "v1-rev527-1.22.0"
libraryDependencies += "com.google.apis" % "google-api-services-people" % "v1-rev124-1.22.0"
libraryDependencies += "com.google.api-client" % "google-api-client" % "1.22.0"




