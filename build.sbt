name := """play-slick-example"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.5"

libraryDependencies ++= Seq(
    jdbc,
    "jp.t2v" %% "play2-auth" % "0.12.0",
    "jp.t2v" %% "play2-auth-test" % "0.12.0" % "test",
    "mysql" % "mysql-connector-java" % "5.1.28",     
  	"com.typesafe.slick" %% "slick" % "2.1.0",
    "com.typesafe.slick" %% "slick-codegen" % "2.1.0",
  	"org.slf4j" % "slf4j-nop" % "1.6.4",
  	"com.h2database" % "h2" % "1.3.170",
  	"org.scalatest" % "scalatest_2.11" % "2.2.1" % "test",
  	"com.typesafe.play" %% "play-slick" % "0.8.0" 
  )

resolvers += "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"
