package utils

import javax.inject.Inject

import models.{Account, Administrator}

import scala.concurrent.Future


trait AuthenticatorService {
  def authenticate(user: String, password: String): Future[Account]
}

class AuthenticatorServiceImpl @Inject() extends AuthenticatorService{
  override def authenticate(user: String, password: String): Future[Account] = Future.successful(Account("gatesb", permission = Administrator))
}