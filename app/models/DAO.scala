package models

import play.api.db.slick.Config.driver.simple._

private[models] trait DAO {
  lazy val Projects = TableQuery[ProjectsTable]
  lazy val Tasks = TableQuery[TasksTable]
}
