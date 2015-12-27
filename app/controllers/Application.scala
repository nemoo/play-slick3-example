package controllers

import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.Action
import play.api.mvc.Controller
import models.{Project, TaskDAO, ProjectDAO, Task}
import slick.driver.JdbcProfile
import services.ProjectService


class Application @Inject()(projectDAO: ProjectDAO, taskDAO: TaskDAO, projectService: ProjectService)
                           (protected val dbConfigProvider: DatabaseConfigProvider)
                           extends Controller {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig.driver.api._


  def addTaskToProject(color: String, projectId: Long) = Action.async { implicit rs =>
    projectService.addTaskToProject(color, projectId)
      .map{ _ =>  Redirect(routes.Application.projects(projectId)) }
  }

  def createProject(name: String)= Action.async { implicit rs =>
     projectService.createProject(name)
       .map(id => Ok(s"project $id created") )
  }

  def listProjects = Action.async { implicit rs =>
      projectService.all
        .map(projects => Ok(views.html.projects(projects.toList)))
  }

  def projects(id: Long) = Action.async { implicit rs =>
    projectService.projectDetails(id)
      .map{case (project, tasks) =>
        Ok(views.html.project(project, tasks))}
  }
    
}
