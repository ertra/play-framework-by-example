// Play framework sbt configuration

// put the content of the conf folder inside the application jar file
PlayKeys.externalizeResources := false

// for running JPA in the production
PlayKeys.externalizeResourcesExcludes += baseDirectory.value / "conf" / "META-INF" / "persistence.xml"

lazy val root = (project in file("."))
  .enablePlugins(PlayJava)
  //.enablePlugins(PlayNettyServer).disablePlugins(PlayPekkoHttpServer) // uncomment to use the Netty backend
  .settings(
    scalaVersion := "2.13.16",
    version := "1.1",
    name := """play-framework-by-example""",
    maintainer := """tomas.zeman@gmail.com""",
    libraryDependencies ++= Seq(
      guice,
      ehcache,
      cacheApi,
      filters,
      javaWs,
      javaJpa,
      "com.h2database" % "h2" % "2.3.232",
      "org.hibernate.orm" % "hibernate-core" % "6.6.8.Final",
      "com.github.scribejava" % "scribejava-core" % "8.3.3",
      "com.github.scribejava" % "scribejava-apis" % "8.3.3",
      "com.google.apis" % "google-api-services-gmail" % "v1-rev110-1.25.0",
      "com.google.api-client" % "google-api-client" % "2.7.2"
    ),

    javacOptions ++= Seq(
      "-encoding", "UTF-8",
      "-parameters",
      "-Xlint:unchecked",
      "-Xlint:deprecation",
      "-Werror"
    )
 )

// dont include documentation in the production build
Compile / doc / sources := Seq.empty
Compile / packageDoc / publishArtifact := false
