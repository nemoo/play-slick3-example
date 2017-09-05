package utils

import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.api.services.IdentityService
import models.{ Administrator, NormalUser, User}
import play.api.Logger

import scala.concurrent.Future

@javax.inject.Singleton
class UserService @javax.inject.Inject() (a:String) extends IdentityService[User]{

  val logger = Logger(this.getClass())
  
  override def retrieve(loginInfo: LoginInfo): Future[Option[User]] ={
    val user = loginInfo.providerKey match {
      case x  if x == "gatesb" => User(x, permission = Administrator)
      case x => User(x, permission = NormalUser)
    }

    logger.debug(s"retrieving user $user")
    Future.successful(Option(user))
  }

}
