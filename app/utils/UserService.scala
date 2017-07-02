package utils

import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.api.services.IdentityService
import models.User

import scala.concurrent.Future

@javax.inject.Singleton
class UserService @javax.inject.Inject() (a:String) extends IdentityService[User]{
  override def retrieve(loginInfo: LoginInfo): Future[Option[User]] = Future.successful(Option(User("john","1234")))
}
