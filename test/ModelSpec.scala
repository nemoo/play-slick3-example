package test

import play.api.test._
import play.api.test.Helpers._
import play.api.db.slick.DatabaseConfigProvider
import play.api.Play.current

import play.api.{Play, Application}
import slick.driver.JdbcProfile

import scala.concurrent.Await
import scala.concurrent.duration.Duration

import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._


@RunWith(classOf[JUnitRunner])
class ModelSpec extends Specification {

  import models._

  lazy val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
  val appWithMemoryDatabase = FakeApplication(additionalConfiguration = inMemoryDatabase("test"))


  def projectDao(implicit app: Application) = {
    val app2CompaniesDAO = Application.instanceCache[ProjectDAO]
    app2CompaniesDAO(app)
  }

  "A project" should {

    "be inserted" in {
      running(appWithMemoryDatabase) {

        val project = Project(1, "A")
        Await.result(dbConfig.db.run(projectDao.insert(project)), Duration.Inf)

        val result = Await.result(dbConfig.db.run(projectDao.all), Duration.Inf)

        result must be_==(Seq(Project(1, "A")))
      }
    }
    
  }

}
