name := """play-slick-example"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

routesGenerator := InjectedRoutesGenerator

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

libraryDependencies ++= Seq(
    "com.typesafe.play" %% "play-slick" % "2.0.2",
    "com.typesafe.play" %% "play-slick-evolutions" % "2.0.2",
    "com.h2database" % "h2" % "1.4.192",
    "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % "test",
    specs2 % Test
)

resolvers += "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"

fork in run := true
