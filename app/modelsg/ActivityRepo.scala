package modelsg
import modelsg.Tables2._
import scala.slick.model.ForeignKeyAction
import scala.slick.jdbc.{ GetResult => GR }

import play.api.libs.json._
import org.joda.time.DateTime

import scala.slick.driver.JdbcProfile
import scala.slick.lifted.TableQuery
import play.api.db.slick.DB


class ActivityRepo(override val profile: JdbcProfile) extends Tables2 {
  import profile.simple._

  def findByName(name: String)(implicit session: Session) =
    Activities.filter(_.name === name)
 
}

object current {
  val activityRepo = new ActivityRepo(DB(play.api.Play.current).driver)
}
