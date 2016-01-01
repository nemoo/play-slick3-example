package test

import play.api.test._
import play.api.test.Helpers._
import services.ProjectService
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import org.specs2.mutable._
import scala.concurrent.ExecutionContext.Implicits.global
import testhelpers.Injector


class ModelSpec extends Specification {

  import models._

  val projectService = Injector.inject[ProjectService]

  "A project" should {

    "be inserted" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {

        val action = projectService.create("A")
          .flatMap(_ => projectService.all)

        val result = Await.result(action, Duration.Inf)

        result must be_==(List(Project(1, "A")))
      }
    }

    "be inserted2" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {

        val action = projectService.create("A")
          .flatMap(_ => projectService.all)

        val result = Await.result(action, Duration.Inf)

        result must be_==(List(Project(1, "A")))
      }
    }


  }

}
