package test

import play.api.test.Helpers._
import play.api.test._
import services.ProjectService
import testhelpers.Injector

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

import org.scalatest._
import play.api.test._
import play.api.test.Helpers._
import org.scalatestplus.play._


class ModelSpec2 extends PlaySpec with OneAppPerTest {

  import models._

  val projectService = Injector.inject[ProjectService]

  "An item " should {

    "be inserted during the first test case" in {
      running(FakeApplication()) {

        val action = projectService.create("A")
          .flatMap(_ => projectService.all)

        val result = Await.result(action, Duration.Inf)

        result mustBe List(Project(1, "A"))
      }
    }

    "and not exist in the second test case" in {
      running(FakeApplication()) {

        val action = projectService.all

        val result = Await.result(action, Duration.Inf)

        result mustBe None
      }
    }


  }

}
