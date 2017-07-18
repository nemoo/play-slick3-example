package models

import com.mohiva.play.silhouette.api.Identity

case class User (
  login: String
) extends Identity

