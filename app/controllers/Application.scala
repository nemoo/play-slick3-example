package controllers

import javax.inject.Inject

import models.{ProjectRepo, TaskRepo}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.{Action, Controller}
import com.github.takezoe.slick.blocking.BlockingH2Driver.blockingApi._

class Application @Inject()( projectRepo: ProjectRepo, taskRepo: TaskRepo)
                           extends Controller {

  import projectRepo.db

  def addTaskToProject(color: String, projectId: Long) = Action { implicit request =>
    db.withSession { implicit session =>
      projectRepo.addTask(color, projectId)
      Redirect(routes.Application.projects(projectId))
    }
  }

  def modifyTask(taskId: Long, color: Option[String]) = Action { implicit request =>
      db.withSession { implicit session =>

        val updatedRows = taskRepo.partialUpdate(taskId, color, None, None)

        Ok(s"Rows affected : $updatedRows")
      }
  }

  def createProject(name: String)= Action { implicit request =>
    db.withSession { implicit session =>
      val id = projectRepo.create(name)
      Ok(s"project $id created")
    }
  }

  def listProjects = Action { implicit request =>
    db.withSession { implicit session =>
      val projects = projectRepo.all
       Ok(views.html.projects(projects))
    }
  }

  def projects(id: Long) = Action { implicit request =>
    db.withSession { implicit session =>
      val project =  projectRepo.findById(id).get
      val tasks = taskRepo.findByProjectId(id)
      Ok(views.html.project(project, tasks))
    }
  }

  def delete(name: String) = Action { implicit request =>
    db.withSession { implicit session =>
      projectRepo.delete(name)
      Ok(s"project $name deleted")
    }
  }

}
