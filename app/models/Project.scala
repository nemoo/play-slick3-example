package models

import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import scala.concurrent.ExecutionContext.Implicits.global

case class Project(id: Long, name: String)

class ProjectRepo @Inject()(taskRepo: TaskRepo)(protected val dbConfigProvider: DatabaseConfigProvider) {

  val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig.driver.api._
  private val Projects = TableQuery[ProjectsTable]


  def findById(id: Long): DBIO[Option[Project]] =
    Projects.filter(_.id === id).result.headOption

  def findByName(name: String): DBIO[Option[Project]] =
    Projects.filter(_.name === name).result.headOption

  def all: DBIO[List[Project]] =
    Projects.to[List].result

  private def insert(Project: Project): DBIO[Long] =
    Projects returning Projects.map(_.id) += Project

  def create(name: String): DBIO[Long] = {
    val project = Project(0, name)
    insert(project)
  }

  def addTask(color: String, projectId: Long): DBIO[Long] =
    (for {
      Some(project) <- findById(projectId)
      id <- taskRepo.insert(Task(0, color, project.id))
    }yield id).transactionally


  private class ProjectsTable(tag: Tag) extends Table[Project](tag, "PROJECT") {

    def id = column[Long]("ID", O.AutoInc, O.PrimaryKey)
    def name = column[String]("NAME")

    def * = (id, name) <> (Project.tupled, Project.unapply _)
    def ? = (id.?, name.?).shaped.<>({ r => import r._; _1.map(_ => Project.tupled((_1.get, _2.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

  }
}