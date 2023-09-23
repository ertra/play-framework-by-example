
PlayKeys.externalizeResources := false

lazy val root = (project in file("."))
  .enablePlugins(PlayJava)
  .settings(
    scalaVersion := "2.13.12",
    version := "1.0",
    name := """play-framework-by-example""",
    maintainer := """tomas.zeman@gmail.com""",
    libraryDependencies ++= Seq(
      guice,
      jdbc,
      ehcache,
      filters,
      javaWs,
      javaJpa,
      "com.h2database" % "h2" % "1.4.200",
      "org.hibernate" % "hibernate-core" % "5.3.7.Final",
      "com.github.scribejava" % "scribejava-core" % "6.1.0",
      "com.github.scribejava" % "scribejava-apis" % "6.1.0",
      "com.github.scribejava" % "scribejava" % "6.1.0",
      "com.google.apis" % "google-api-services-gmail" % "v1-rev96-1.25.0",
      "com.google.apis" % "google-api-services-plus" % "v1-rev562-1.25.0",
      "com.google.apis" % "google-api-services-people" % "v1-rev374-1.25.0",
      "com.google.api-client" % "google-api-client" % "1.27.0"
    )
    //javacOptions ++= Seq("-Xlint:unchecked", "-Xlint:deprecation", "-Werror")
 )
