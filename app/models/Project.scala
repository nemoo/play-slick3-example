package models

import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class Project(id: Long, name: String)

class ProjectRepo @Inject()(taskRepo: TaskRepo)(protected val dbConfigProvider: DatabaseConfigProvider) {

  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db
  import dbConfig.driver.api._
  private val Projects = TableQuery[ProjectsTable]

  private def _findById(id: Long): DBIO[Option[Project]] =
    Projects.filter(_.id === id).result.headOption

  def findById(id: Long): Future[Option[Project]] =
    db.run(_findById(id))

  def findByName(name: String): Future[Option[Project]] =
    db.run(Projects.filter(_.name === name).result.headOption)

  def all: Future[List[Project]] =
    db.run(Projects.to[List].result)

  def create(name: String): Future[Long] = {
    val project = Project(0, name)
    db.run(Projects returning Projects.map(_.id) += project)
  }

  def addTask(color: String, projectId: Long): Future[Long] = {
    val interaction = (for {
      Some(project) <- _findById(projectId)
      id <- taskRepo.insert(Task(0, color, TaskStatus.ready, project.id))
    }yield id).transactionally

    db.run(interaction)
  }


  private class ProjectsTable(tag: Tag) extends Table[Project](tag, "PROJECT") {

    def id = column[Long]("ID", O.AutoInc, O.PrimaryKey)
    def name = column[String]("NAME")

    def * = (id, name) <> (Project.tupled, Project.unapply _)
    def ? = (id.?, name.?).shaped.<>({ r => import r._; _1.map(_ => Project.tupled((_1.get, _2.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

  }
}