package controllers

import javax.inject.Inject

import com.mohiva.play.silhouette.api.Silhouette
import com.mohiva.play.silhouette.api.actions.SecuredRequest
import com.mohiva.play.silhouette.api.util.Credentials
import play.api.data.Form
import play.api.mvc._
import play.api.data.Forms._
import utils.AuthEnv

import scala.concurrent.ExecutionContext


class Auth @Inject() (
  val controllerComponents: ControllerComponents,
  silhouette: Silhouette[AuthEnv])(
  val ex: ExecutionContext)
extends BaseController {

  def signin = silhouette.UnsecuredAction { implicit request: Request[AnyContent] =>
    Ok(views.html.signin())
  }

  val signInForm = Form(mapping(
    "login" -> nonEmptyText,
    "password" -> nonEmptyText
  )(Credentials.apply)(Credentials.unapply))

  def authenticate = silhouette.UnsecuredAction { implicit request: Request[AnyContent] =>
//    Redirect(controllers.routes.Application.listProjects())
    Ok("authenticate")
  }

  def signout = silhouette.SecuredAction { implicit request: SecuredRequest[AuthEnv, AnyContent] =>
    Redirect(controllers.routes.Auth.signin())
  }
}
