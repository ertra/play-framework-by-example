name := """play-java"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.2"

libraryDependencies += guice
libraryDependencies += ehcache
libraryDependencies += filters
libraryDependencies += javaWs
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.0"

// For demo of the database stuff
libraryDependencies += "com.h2database" % "h2" % "1.4.194"
//libraryDependencies += evolutions
//libraryDependencies += javaJdbc
libraryDependencies += javaJpa
// To connect to MySQL or PostgreSQL later
//libraryDependencies += "mysql" % "mysql-connector-java" % "6.0.6"
//libraryDependencies += "org.postgresql" % "postgresql" % "42.1.1"
libraryDependencies += "org.hibernate" % "hibernate-core" % "5.2.5.Final"

// for production DB
// PlayKeys.externalizeResources := false

// for Facebook OAuth login demo and also OAuth library we use for Google: scribe (https://github.com/scribejava/scribejava)
libraryDependencies += "com.github.scribejava" % "scribejava-core" % "4.1.1"
libraryDependencies += "com.github.scribejava" % "scribejava-apis" % "4.1.1"
libraryDependencies += "com.github.scribejava" % "scribejava" % "4.1.1"

// for Gmail OAuth demo and some Google API examples
libraryDependencies += "com.google.apis" % "google-api-services-gmail" % "v1-rev67-1.22.0"
libraryDependencies += "com.google.apis" % "google-api-services-plus" % "v1-rev527-1.22.0"
libraryDependencies += "com.google.apis" % "google-api-services-people" % "v1-rev124-1.22.0"
libraryDependencies += "com.google.api-client" % "google-api-client" % "1.22.0"
