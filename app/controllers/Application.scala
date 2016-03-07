package controllers

import javax.inject.Inject

import models.{ProjectRepo, TaskRepo}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.{Action, Controller}

class Application @Inject()( projectRepo: ProjectRepo, taskRepo: TaskRepo)
                           extends Controller {

  def addTaskToProject(color: String, projectId: Long) = Action.async { implicit rs =>
    projectRepo.addTask(color, projectId)
      .map{ _ =>  Redirect(routes.Application.projects(projectId)) }
  }

  def modifyTask(taskId: Long, color: Option[String]) = Action.async { implicit rs =>
    taskRepo.partialUpdate(taskId, color, None, None).map(i =>
    Ok(s"Rows affected : $i"))
  }
  def createProject(name: String)= Action.async { implicit rs =>
    projectRepo.create(name)
      .map(id => Ok(s"project $id created") )
  }

  def listProjects = Action.async { implicit rs =>
    projectRepo.all
      .map(projects => Ok(views.html.projects(projects)))
  }

  def projects(id: Long) = Action.async { implicit rs =>
    for {
      Some(project) <-  projectRepo.findById(id)
      tasks <- taskRepo.findByProjectId(id)
    } yield Ok(views.html.project(project, tasks))
  }

  def delete(name: String) = Action.async { implicit rs =>
    projectRepo.delete(name).map(num => Ok(s"$num projects deleted"))
  }

}
