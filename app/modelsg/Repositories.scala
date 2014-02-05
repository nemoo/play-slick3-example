package modelsg
import scala.slick.model.ForeignKeyAction
import scala.slick.jdbc.{ GetResult => GR }

import play.api.libs.json._
import org.joda.time.DateTime

import scala.slick.driver.JdbcProfile
import play.api.db.slick.Config
import play.api.db.slick.Profile

class ActivityRepo(override val profile: JdbcProfile) extends Tables with Profile {
  import profile.simple._
//  private val Activities = TableQuery[Activities]
  private val Activities = new TableQuery(tag => new Activities(tag))

  def findByName(name: String)(implicit session: Session) =
    Activities.filter(_.name === name).first
 
} 

class ItemRepo(override val profile: JdbcProfile) extends Tables with Profile {
  import profile.simple._

  private val Items = new TableQuery(tag => new Items(tag))

  def findByColor(color: String) =
    Items.filter(_.color === color)
 
} 

object current {
  lazy val activityRepo = new ActivityRepo(Config.driver)
  lazy val itemRepo = new ItemRepo(Config.driver)
}
