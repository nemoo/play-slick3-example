package models

import play.api.db.slick.Config.driver.simple._

private[models] trait DAO {
  lazy val Activities = TableQuery[ActivitiesTable]
  lazy val Items = TableQuery[ItemsTable]
  
}
