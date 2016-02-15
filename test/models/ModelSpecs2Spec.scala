package models

import org.specs2.specification.AfterEach
import play.api.db.DBApi
import play.api.db.evolutions.Evolutions
import play.api.test._
import testhelpers.{EvolutionHelper, Injector}

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration


class ModelSpecs2Spec extends PlaySpecification with AfterEach {

  val projectRepo = Injector.inject[ProjectRepo]

  override def after = EvolutionHelper.clean()

  "An item " should {

    "be inserted during the first test case" in new WithApplication(FakeApplication()) {
        val action = projectRepo.create("A")
          .flatMap(_ => projectRepo.all)

        val result = Await.result(action, Duration.Inf)

        result must be_==(List(Project(1, "A")))
    }

    "and not exist in the second test case" in new WithApplication(FakeApplication()) {
        val action = projectRepo.all

        val result = Await.result(action, Duration.Inf)

        result must be_==(List.empty)
    }


  }

}
