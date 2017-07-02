package controllers

import javax.inject.Inject

import models.{ProjectRepo, TaskRepo}
import play.api.db.slick.DatabaseConfigProvider
import play.api.mvc.{Action, Controller}
import slick.driver.JdbcProfile

class Auth extends Controller {

  def signin = Action { implicit request =>
    Ok(views.html.signin())
  }

  def authenticate = Action { implicit request =>
    Redirect(controllers.routes.Application.listProjects())
  }

  def signout = Action { implicit request =>
    Redirect(controllers.routes.Auth.signin())
  }
}
