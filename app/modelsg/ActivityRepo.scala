package modelsg
import scala.slick.model.ForeignKeyAction
import scala.slick.jdbc.{ GetResult => GR }

import play.api.libs.json._
import org.joda.time.DateTime

import scala.slick.driver.JdbcProfile
import play.api.db.slick.Config
import play.api.db.slick.Profile

class ActivityRepo(override val profile: JdbcProfile) extends Tables2 with Profile {
  import profile.simple._
  private val Activities = TableQuery[Activities]

  def findByName(name: String) =
    Activities.filter(_.name === name)
 
} 

object current {
  val activityRepo = new ActivityRepo(Config.driver)
}
