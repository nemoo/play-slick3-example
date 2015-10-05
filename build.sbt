name := """play-slick-example"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

routesGenerator := InjectedRoutesGenerator

libraryDependencies ++= Seq(
    cache,
    "com.typesafe.slick" %% "slick" % "3.0.1",
    "com.typesafe.play" %% "play-slick" % "1.0.1",
    "com.typesafe.play" %% "play-slick-evolutions" % "1.0.1",
    "com.h2database" % "h2" % "1.3.175"

)

resolvers += "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"


fork in run := true

fork in run := true