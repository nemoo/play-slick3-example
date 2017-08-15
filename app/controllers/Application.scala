package controllers

import javax.inject.{Inject, Singleton}

import models.{ProjectRepo, TaskRepo, TestData, User}
import play.api.mvc._
import com.github.takezoe.slick.blocking.BlockingH2Driver.blockingApi._
import com.mohiva.play.silhouette
import com.mohiva.play.silhouette.api.Silhouette
import com.mohiva.play.silhouette.api.actions._
import play.Environment
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import utils.{AuthEnv, WeatherService}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Application @Inject()(
                             projectRepo: ProjectRepo,
                             taskRepo: TaskRepo,
                             testData: TestData,
                             weather: WeatherService,
                             silhouette: Silhouette[AuthEnv],
                             val controllerComponents: ControllerComponents,
                             env: Environment
                           )(protected val dbConfigProvider: DatabaseConfigProvider,
                             val ex: ExecutionContext )
                           extends BaseController {

  val db = dbConfigProvider.get[JdbcProfile].db

  //generate some test data
  db.withSession { implicit session =>
    if (projectRepo.all.length + taskRepo.all.length == 0 && env.isDev) {
      testData.createTestData
    }
  }


  def test = silhouette.UnsecuredAction { implicit request: Request[AnyContent] =>
    Ok("test")
  }

  def addTaskToProject(color: String, projectId: Long) = silhouette.SecuredAction { implicit request: SecuredRequest[AuthEnv, AnyContent] =>
    db.withSession { implicit session =>
      projectRepo.addTask(color, projectId)
      Redirect(routes.Application.projects(projectId))
    }
  }

  def modifyTask(taskId: Long, color: Option[String]) = silhouette.SecuredAction { implicit request: SecuredRequest[AuthEnv, AnyContent] =>
      db.withSession { implicit session =>

        val updatedRows = taskRepo.partialUpdate(taskId, color, None, None)

        Ok(s"Rows affected : $updatedRows")
      }
  }

  def createProject(name: String)= silhouette.SecuredAction { implicit request: SecuredRequest[AuthEnv, AnyContent] =>
    db.withSession { implicit session =>
      val id = projectRepo.create(name)
      Ok(s"project $id created")
    }
  }

  def listProjects = silhouette.SecuredAction { implicit request: SecuredRequest[AuthEnv, AnyContent] =>
    db.withSession { implicit session =>
      implicit val user = request.identity

      val temperature = weather.forecast("NYC")
      val projects = projectRepo.all
       Ok(views.html.projects(projects, temperature))
    }
  }

  def projects(id: Long) = silhouette.SecuredAction { implicit request: SecuredRequest[AuthEnv, AnyContent] =>
    db.withSession { implicit session =>
      implicit val user = request.identity

      val project =  projectRepo.findById(id).get
      val tasks = taskRepo.findByProjectId(id)
      Ok(views.html.project(project, tasks))
    }
  }

  def delete(id: Long) = silhouette.SecuredAction { implicit request: SecuredRequest[AuthEnv, AnyContent] =>
    db.withTransaction { implicit session =>
      projectRepo.delete(id)
      Redirect(routes.Application.listProjects())
    }
  }

}
