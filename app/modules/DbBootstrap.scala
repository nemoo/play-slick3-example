package modules

import models.{ProjectRepo, TaskRepo, TestData}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import play.api.Logger
import com.github.takezoe.slick.blocking.BlockingH2Driver.blockingApi._
import play.api.Environment
import play.api.Mode.Dev

import javax.inject.{Inject, Singleton}
import play.api.db.evolutions.ApplicationEvolutions
import scala.concurrent.Future
import scala.concurrent.ExecutionContext

@Singleton
class DbBootstrap @Inject() (
    testData: TestData,
    env: Environment,
    dbConfigProvider: DatabaseConfigProvider,
    applicationEvolutions: ApplicationEvolutions
)(implicit ec: ExecutionContext) {

  val db = dbConfigProvider.get[JdbcProfile].db
  val logger = Logger(getClass())

  if (env.mode == Dev) { // <- we only want it to be executed in DevMode
    if (applicationEvolutions.upToDate) {
        //generate some test data
        db.withSession { implicit session =>
            logger.info("creating test data...")
            testData.createTestData
        }
        logger.info("finished DB bootstrap")
    } else {
      logger.warn("Evolutions is not up to date, DB bootstrap won't run yet!")
    }
  }
}
