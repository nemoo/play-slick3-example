import java.text.SimpleDateFormat
import play.api._
import models._
import play.api.db.slick._
import play.api.Play.current

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
    DB.withSession { implicit s: Session =>
      if (Items.count == 0) {
        Seq(
          Item(99, "blue"),
          Item(99, "red"),
          Item(99, "green")).foreach(Items.insert)


      }
    }
  }
}