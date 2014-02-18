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
        val aid = Activities.insert(Activity(99, "first"))
        val aid2 = Activities.insert(Activity(99, "second"))
        Activities.insert(Activity(99, "third"))
   
        Seq(
          Item(99, "blue", aid),
          Item(99, "red", aid),
          Item(99, "green", aid),
          Item(99, "white", aid2),
          Item(99, "black", aid2)).foreach(Items.insert)


      }
    }
  }
}