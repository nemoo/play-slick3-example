package utils

import javax.inject.Inject

import models.{Account, Administrator, NormalUser}
import play.api.Logger

import scala.concurrent.Future


trait AuthenticatorService {
  def authenticate(user: String, password: String): Future[Account]
}

@javax.inject.Singleton
class AuthenticatorServiceImpl @Inject() extends AuthenticatorService{

  val logger = Logger(this.getClass())

  override def authenticate(user: String, password: String): Future[Account] ={
    val account = user match {
      case "gatesb" => Account(user, permission = Administrator)
      case _ => Account(user, permission = NormalUser)
    }

    logger.info(s"authenticated $account")
    Future.successful(account)
  }

}