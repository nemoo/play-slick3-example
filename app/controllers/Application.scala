package controllers

import javax.inject.Inject
import models.{TaskRepo, ProjectRepo}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.Action
import play.api.mvc.Controller

class Application @Inject()( projectRepo: ProjectRepo, taskRepo: TaskRepo)
                           extends Controller {

  def addTaskToProject(color: String, projectId: Long) = Action.async { implicit rs =>
    projectRepo.addTask(color, projectId)
      .map{ _ =>  Redirect(routes.Application.projects(projectId)) }
  }

  def createProject(name: String)= Action.async { implicit rs =>
    projectRepo.create(name)
      .map(id => Ok(s"project $id created") )
  }

  def listProjects = Action.async { implicit rs =>
    projectRepo.all
      .map(projects => Ok(views.html.projects(projects.toList)))
  }

  def projects(id: Long) = Action.async { implicit rs =>
    for {
      Some(project) <-  projectRepo.findById(id)
      tasks <- taskRepo.findByProjectId(id)
    }yield (Ok(views.html.project(project, tasks)))
  }
    
}
