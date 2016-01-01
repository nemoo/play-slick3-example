package services

import javax.inject.Inject
import models._
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits.defaultContext


class ProjectService @Inject()(projectRepo: ProjectRepo, taskRepo: TaskRepo)(protected val dbConfigProvider: DatabaseConfigProvider)
{
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db
  import dbConfig.driver.api._

  def create(name: String): Future[Long] =
    db.run(projectRepo.create(name))

  def all: Future[List[Project]] =
    db.run(projectRepo.all)

  def projectDetails(id: Long): Future[(Project, List[Task])] = {
    val query = for {
      Some(project) <-  projectRepo.findById(id)
      tasks <- taskRepo.findByProjectId(id)
    }yield (project, tasks)

    db.run(query)
  }

  def addTask(color: String, projectId: Long): Future[Long] =
    dbConfig.db.run(projectRepo.addTask(color, projectId))
}
