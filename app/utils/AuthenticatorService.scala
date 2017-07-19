package utils

import javax.inject.Inject
import play.api.Logger
import scala.concurrent.Future


trait AuthenticatorService {
  def authenticate(user: String, password: String): Future[Unit]
}

@javax.inject.Singleton
class AuthenticatorServiceImpl @Inject() extends AuthenticatorService{

  val logger = Logger(this.getClass())

  override def authenticate(user: String, password: String): Future[Unit] ={

    logger.info(s"authenticated $user")
    Future.successful(Unit)
  }

}