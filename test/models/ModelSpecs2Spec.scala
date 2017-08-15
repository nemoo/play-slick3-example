package models

import com.github.takezoe.slick.blocking.BlockingH2Driver.blockingApi._
import org.specs2.specification.AfterEach
import play.api.db.slick.DatabaseConfigProvider
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test._
import slick.jdbc.JdbcProfile
import testhelpers.{EvolutionHelper, Injector}
import utils.WeatherService


class ModelSpecs2Spec extends PlaySpecification with AfterEach {

  val projectRepo = Injector.inject[ProjectRepo]
  val weather = Injector.inject[WeatherService]
  val db = Injector.inject[DatabaseConfigProvider].get[JdbcProfile].db

  override def after = EvolutionHelper.clean()

  "An item " should {

    def app = GuiceApplicationBuilder()
      .build()

    "be inserted during the first test case" in new WithApplication(app) {
      db.withSession { implicit session =>

        val action = projectRepo.create("A")
        val result = projectRepo.all
        result must be_==(List(Project(1, "A")))
      }
    }

    "and not exist in the second test case" in new WithApplication(app) {
      db.withSession { implicit session =>
        val result = projectRepo.all

        result must be_==(List.empty)
      }
    }

    "get some mocked weather information" in new WithApplication(app) {
        val result = weather.forecast("NYC")

        result must be_==(80)
    }
  }
}
