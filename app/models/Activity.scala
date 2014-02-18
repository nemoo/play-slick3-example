package models

import play.api.db.slick.Config.driver.simple._

case class Activity(id: Long, name: String)

class ActivitiesTable(tag: Tag) extends Table[Activity](tag, "ACTIVITY") {
  def * = (id, name) <> (Activity.tupled, Activity.unapply)
  def ? = (id.?, name.?).shaped.<>({ r => import r._; _1.map(_ => Activity.tupled((_1.get, _2.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

  val id: Column[Long] = column[Long]("ID", O.AutoInc, O.PrimaryKey)
  val name: Column[String] = column[String]("NAME")
}

object Activities extends DAO {

  def findByName(name: String)(implicit session: Session) =
    Activities.filter(_.name === name).firstOption

  def all(implicit session: Session) =
    Activities.list

  def insert(a: Activity)(implicit session: Session): Long =
    (Activities returning Activities.map(_.id)) += a

} 