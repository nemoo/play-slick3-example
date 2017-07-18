package utils

import javax.inject.Inject

import models.{Account, Administrator}

import scala.concurrent.Future


trait AuthenticatorService {
  def authenticate(user: String, password: String): Future[Account]
}

class AuthenticatorServiceImpl @Inject() extends AuthenticatorService{
  override def authenticate(user: String, password: String): Future[Account] ={
    val account = Account(user, permission = Administrator)
    println(s"authenticated $account")
    Future.successful(account)
  }

}