package models

import slick.lifted.TableQuery

private[models] trait DAO {

  lazy val Projects = TableQuery[ProjectsTable]
  lazy val Tasks = TableQuery[TasksTable]

}