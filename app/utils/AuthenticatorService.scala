package utils

import javax.inject.Inject

import models.{Account, Administrator}
import play.api.Logger

import scala.concurrent.Future


trait AuthenticatorService {
  def authenticate(user: String, password: String): Future[Account]
}

@javax.inject.Singleton
class AuthenticatorServiceImpl @Inject() extends AuthenticatorService{

  val logger = Logger(this.getClass())

  override def authenticate(user: String, password: String): Future[Account] ={
    val account = Account(user, permission = Administrator)
    logger.info(s"authenticated $account")
    Future.successful(account)
  }

}