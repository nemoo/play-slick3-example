package services

import javax.inject.Inject
import models._
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits.defaultContext


class ProjectService @Inject()(projectDAO: ProjectRepo, taskDAO: TaskRepo)(protected val dbConfigProvider: DatabaseConfigProvider)
{
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db
  import dbConfig.driver.api._

  def createProject(name: String): Future[Long] = {
    val project = Project(0, name)
    db.run(projectDAO.insert(project))
  }

  def all: Future[List[Project]] =
    db.run(projectDAO.all)

  def projectDetails(id: Long): Future[(Project, List[Task])] = {
    val query = for {
      Some(project) <-  projectDAO.findById(id)
      tasks <- taskDAO.findByProjectId(id)
    }yield (project, tasks)

    db.run(query)
  }

  def addTaskToProject(color: String, projectId: Long): Future[Long] = {

    val query = (for {
      Some(project) <-  projectDAO.findById(projectId)
      id <- taskDAO.insert(Task(0, color, project.id))
    }yield id).transactionally

    dbConfig.db.run(query)
  }

}
