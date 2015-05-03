package models

import play.api.db.slick.Config.driver.simple._

case class Project(id: Long, name: String)

class ProjectsTable(tag: Tag) extends Table[Project](tag, "PROJECT") {
  def * = (id, name) <> (Project.tupled, Project.unapply)
  def ? = (id.?, name.?).shaped.<>({ r => import r._; _1.map(_ => Project.tupled((_1.get, _2.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

  val id: Column[Long] = column[Long]("ID", O.AutoInc, O.PrimaryKey)
  val name: Column[String] = column[String]("NAME")
}

object Projects extends DAO {

  def findById(id: Long)(implicit s: Session): Option[Project] =
    Projects.filter(_.id === id).firstOption

  def findByName(name: String)(implicit session: Session): Option[Project] =
    Projects.filter(_.name === name).firstOption
    
  def findTasks(id: Long)(implicit session: Session): List[Task] =
    Tasks
      .filter(_.project === id)
      .list

  def all(implicit session: Session): List[Project] =
    Projects.list

  def insert(a: Project)(implicit session: Session): Long =
    (Projects returning Projects.map(_.id)) += a

} 