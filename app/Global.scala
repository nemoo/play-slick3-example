import java.text.SimpleDateFormat
import play.api._
import models._
//import driver.simple._
//import play.api.db.slick._
//import play.api.Play.current

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    
    
    InitialData.insert()
  }

}

/**
 * Initial set of data to be imported
 * in the sample application.
 */
object InitialData {

  val sdf = new SimpleDateFormat("yyyy-MM-dd")

  def insert() {
//    DB.withSession { implicit s: Session =>
//      if (Tasks.count == 0) {
//        val aid = Projects.insert(Project(99, "first"))
//        val aid2 = Projects.insert(Project(99, "second"))
//        Projects.insert(Project(99, "third"))
//
//        Seq(
//          Task(99, "blue", aid),
//          Task(99, "red", aid),
//          Task(99, "green", aid),
//          Task(99, "white", aid2),
//          Task(99, "black", aid2)).foreach(Tasks.insert)
//
//
//      }
//    }
  }
}