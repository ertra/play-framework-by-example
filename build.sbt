name := """play-java"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.6"

crossScalaVersions := Seq("2.11.12", "2.12.4")

libraryDependencies += guice
libraryDependencies += ehcache
libraryDependencies += filters
libraryDependencies += javaWs

// For demo of the database stuff
libraryDependencies += "com.h2database" % "h2" % "1.4.197"
//libraryDependencies += evolutions
//libraryDependencies += javaJdbc
libraryDependencies += javaJpa
// To connect to MySQL or PostgreSQL later
//libraryDependencies += "mysql" % "mysql-connector-java" % "6.0.6"
//libraryDependencies += "org.postgresql" % "postgresql" % "42.1.1"
libraryDependencies += "org.hibernate" % "hibernate-core" % "5.3.7.Final"

// for production DB
PlayKeys.externalizeResources := false

// for Facebook OAuth login demo and also OAuth library we use for Google: scribe (https://github.com/scribejava/scribejava)
libraryDependencies += "com.github.scribejava" % "scribejava-core" % "6.1.0"
libraryDependencies += "com.github.scribejava" % "scribejava-apis" % "6.1.0"
libraryDependencies += "com.github.scribejava" % "scribejava" % "6.1.0"

// for Gmail OAuth demo and some Google API examples
libraryDependencies += "com.google.apis" % "google-api-services-gmail" % "v1-rev96-1.25.0"
libraryDependencies += "com.google.apis" % "google-api-services-plus" % "v1-rev562-1.25.0"
libraryDependencies += "com.google.apis" % "google-api-services-people" % "v1-rev374-1.25.0"
libraryDependencies += "com.google.api-client" % "google-api-client" % "1.27.0"
