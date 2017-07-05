package utils

import com.mohiva.play.silhouette.api.actions.{SecuredErrorHandler, UnsecuredErrorHandler}

import play.api.mvc._
import play.api.mvc.Results._

import scala.concurrent.Future
import javax.inject.{Inject}



class ErrorHandler @Inject() extends  SecuredErrorHandler with UnsecuredErrorHandler {

  // 401 - Unauthorized
  override def onNotAuthenticated(implicit request: RequestHeader): Future[Result] = Future.successful {
    Redirect(controllers.routes.Auth.signin()).withSession(request.session + ("ENTRY_URI" -> request.uri))
  }

  // 403 - Forbidden
  override def onNotAuthorized(implicit request: RequestHeader): Future[Result] = Future.successful {
//    Forbidden(views.html.errors.accessDenied())
    Ok("You are not authorized to view this page")
  }

}