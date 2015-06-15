package models

import slick.driver.MySQLDriver.api._

private[models] trait DAO {
  lazy val Projects = TableQuery[ProjectsTable]
  lazy val Tasks = TableQuery[TasksTable]
}
