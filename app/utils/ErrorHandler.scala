package utils

import com.mohiva.play.silhouette.api.actions.{SecuredErrorHandler, UnsecuredErrorHandler}
import play.api._
import play.api.mvc._
import play.api.mvc.Results._
import play.api.routing.Router

import scala.concurrent.Future
import javax.inject.{Inject, Provider, Singleton}



class ErrorHandler extends  SecuredErrorHandler with UnsecuredErrorHandler  {

  // 401 - Unauthorized
  override def onNotAuthenticated(implicit request: RequestHeader): Future[Result] = Future.successful {
//    Redirect(controllers.routes.Auth.signIn).withSession(request.session + ("ENTRY_URI" -> request.uri))
    Ok("2")
  }

  // 403 - Forbidden
  override def onNotAuthorized(implicit request: RequestHeader): Future[Result] = Future.successful {
//    Forbidden(views.html.errors.accessDenied())
    Ok("1")
  }

}