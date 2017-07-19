package utils

import com.mohiva.play.silhouette.api.Authorization
import com.mohiva.play.silhouette.impl.authenticators.CookieAuthenticator
import models.{Administrator, User}
import play.api.mvc.Request

import scala.concurrent.Future

object AdminOnly extends Authorization[User, CookieAuthenticator] {
  
  def isAuthorized[B](user: User, authenticator: CookieAuthenticator)(
    implicit request: Request[B]) = {
    
    Future.successful(user.permission == Administrator)
  }
}