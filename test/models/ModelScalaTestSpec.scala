package models

import org.scalatest.BeforeAndAfterEach
import org.scalatestplus.play._
import play.api.test.Helpers._
import play.api.test._
import testhelpers.{EvolutionHelper, Injector}
import com.github.takezoe.slick.blocking.BlockingH2Driver.blockingApi._
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.db.slick.DatabaseConfigProvider
import play.api.inject.guice.GuiceApplicationBuilder
import slick.jdbc.JdbcProfile


class ModelScalaTestSpec extends PlaySpec with GuiceOneAppPerTest with BeforeAndAfterEach {

  val projectRepo = Injector.inject[ProjectRepo]
  val db = Injector.inject[DatabaseConfigProvider].get[JdbcProfile].db

  override def afterEach() = EvolutionHelper.clean()

  "An item " should {

    "be inserted during the first test case" in {
      db.withSession { implicit session =>

        val action = projectRepo.create("A")
        val result = projectRepo.all

        result mustBe List(Project(1, "A"))
      }
    }

    "and not exist in the second test case" in {
      db.withSession { implicit session =>
        val result = projectRepo.all

        result mustBe List.empty
      }
    }


  }

}
