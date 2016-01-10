package test

import play.api.test._
import play.api.test.Helpers._
import models.ProjectRepo
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import org.specs2.mutable._
import scala.concurrent.ExecutionContext.Implicits.global
import testhelpers.Injector


class ModelSpec extends Specification {

  import models._

  val projectRepo = Injector.inject[ProjectRepo]

  "An item " should {

    "be inserted during the first test case" in {
      running(FakeApplication()) {

        val action = projectRepo.create("A")
          .flatMap(_ => projectRepo.all)

        val result = Await.result(action, Duration.Inf)

        result must be_==(List(Project(1, "A")))
      }
    }

    "and not exist in the second test case" in {
      running(FakeApplication()) {

        val action = projectRepo.all

        val result = Await.result(action, Duration.Inf)

        result must be_==(None)
      }
    }


  }

}
