import sbt._
import Keys._
import play.Project._
//import com.typesafe.sbt.SbtScalariform.scalariformSettings

object ApplicationBuild extends Build {

  val appName         = "play-slick-example"
  val appVersion      = "1.0-SNAPSHOT"
    

  val appDependencies = Seq(
    // Add your project dependencies here,
    jdbc,
    anorm,
    "mysql" % "mysql-connector-java" % "5.1.28",     
	"com.typesafe.slick" %% "slick" % "2.0.1-RC1",
	"com.h2database" % "h2" % "1.3.170",
	"org.scalatest" %% "scalatest" % "2.0" % "test",
	"com.typesafe.play" %% "play-slick" % "0.6.0.1"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
   // resolvers += "svnkit repo" at "http://maven.tmatesoft.com/content/repositories/releases/"
	resolvers += "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"
      
  ) //.dependsOn(RootProject(file("../play-slick/")))
  //.settings(scalariformSettings:_*)

}
