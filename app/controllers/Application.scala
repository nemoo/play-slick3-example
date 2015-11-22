package controllers

import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.Action
import play.api.mvc.Controller
import models.{Project, TaskDAO, ProjectDAO, Task}
import slick.driver.JdbcProfile


class Application @Inject()(projectDAO: ProjectDAO, taskDAO: TaskDAO)
                           (protected val dbConfigProvider: DatabaseConfigProvider)
                           extends Controller {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig.driver.api._


  def addTaskToProject(color: String, projectId: Long) = Action.async { implicit rs =>

    val query = (for {
      Some(project) <-  projectDAO.findById(projectId)
      id <- taskDAO.insert(Task(0, color, project.id))
    }yield id).transactionally

    dbConfig.db.run(query).map{ taskId =>
      Ok("I have created a new task: " + taskId)
    }
  }

  def createProject(name: String)= Action.async { implicit rs =>
    val project = Project(0, name)
    dbConfig.db.run(projectDAO.insert(project))
      .map(id => Ok(s"project $id created") )
  }

  def listProjects = Action.async { implicit rs =>
    dbConfig.db.run(projectDAO.all)
      .map(projects => Ok(s"Projects: ${projects.map(_.name).mkString(", ")}"))
  }

  def project(id: Long) = Action.async { implicit rs =>

    val query = for {
      Some(project) <-  projectDAO.findById(id)
      tasks <- taskDAO.findByProjectId(id)
    }yield (project, tasks)

    dbConfig.db.run(query)
      .map{case (project, tasks) => Ok(s"Project ${project.name} contains tasks: ${tasks.map(_.color).mkString(", ")}")}
  }
    
}
