package controllers

import javax.inject.Inject

import com.mohiva.play.silhouette.api.Silhouette
import com.mohiva.play.silhouette.api.util.Credentials
import models.{ProjectRepo, TaskRepo}
import play.api.data.Form
import play.api.db.slick.DatabaseConfigProvider
import play.api.mvc.{Action, BaseController, Controller, ControllerComponents}
import slick.driver.JdbcProfile
import play.api.data.Forms._
import play.api.data._
import utils.AuthEnv


class Auth @Inject() (
  val controllerComponents: ControllerComponents,
  silhouette: Silhouette[AuthEnv])
  extends BaseController {

  def signin = Action { implicit request =>
    Ok(views.html.signin())
  }

  val signInForm = Form(mapping(
    "login" -> nonEmptyText,
    "password" -> nonEmptyText
  )(Credentials.apply)(Credentials.unapply))

  def authenticate = Action { implicit request =>
    Redirect(controllers.routes.Application.listProjects())
  }

  def signout = Action { implicit request =>
    Redirect(controllers.routes.Auth.signin())
  }
}
