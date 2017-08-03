package utils

import com.mohiva.play.silhouette.api.actions.{SecuredErrorHandler, UnsecuredErrorHandler}
import play.api.mvc._
import play.api.mvc.Results._

import scala.concurrent.Future
import javax.inject.Inject

import play.api.Logger



class SecurityErrorHandler @Inject() extends  SecuredErrorHandler with UnsecuredErrorHandler {

  val logger = Logger(this.getClass())
  
  // 401 - Unauthorized
  override def onNotAuthenticated(implicit request: RequestHeader): Future[Result] = Future.successful {
    logger.info("notAuthenticated")
    Redirect(controllers.routes.Auth.signin()).withSession(request.session + ("ENTRY_URI" -> request.uri))
  }

  // 403 - Forbidden
  override def onNotAuthorized(implicit request: RequestHeader): Future[Result] = Future.successful {
//    Forbidden(views.html.errors.accessDenied())
    logger.info("notAuthorized")
    Ok("You are not authorized to view this page")
  }

}