package models

import org.specs2.specification.AfterEach
import play.api.db.DBApi
import play.api.db.evolutions.Evolutions
import play.api.test._
import testhelpers.{EvolutionHelper, Injector}
import com.github.takezoe.slick.blocking.BlockingH2Driver.blockingApi._


class ModelSpecs2Spec extends PlaySpecification with AfterEach {

  val projectRepo = Injector.inject[ProjectRepo]

  override def after = EvolutionHelper.clean()

  "An item " should {

    "be inserted during the first test case" in new WithApplication(FakeApplication()) {
      projectRepo.db.withSession { implicit session =>
        val action = projectRepo.create("A")
        val result = projectRepo.all
        result must be_==(List(Project(1, "A")))
      }
    }

    "and not exist in the second test case" in new WithApplication(FakeApplication()) {
      projectRepo.db.withSession { implicit session =>
        val result = projectRepo.all

        result must be_==(List.empty)
      }
    }


  }

}
