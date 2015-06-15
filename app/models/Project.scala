package models

import slick.driver.MySQLDriver.api._
import slick.lifted.Rep

case class Project(id: Long, name: String)

class ProjectsTable(tag: Tag) extends Table[Project](tag, "PROJECT") {
  def * = (id, name) <> (Project.tupled, Project.unapply)
  def ? = (id.?, name.?).shaped.<>({ r => import r._; _1.map(_ => Project.tupled((_1.get, _2.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

  val id: Rep[Long] = column[Long]("ID", O.AutoInc, O.PrimaryKey)
  val name: Rep[String] = column[String]("NAME")
}

object Projects extends DAO {

  def findById(id: Long): DBIO[Option[Project]] =
    Projects.filter(_.id === id).firstOption

  def findByName(name: String): DBIO[Option[Project]] =
    Projects.filter(_.name === name).firstOption
    
  def findTasks(id: Long): DBIO[List[Task]] =
    Tasks
      .filter(_.project === id)
      .list

  def all: DBIO[List[Project]] =
    Projects.list

  def insert(a: Project): DBIO[Long] =
    (Projects returning Projects.map(_.id)) += a

} 