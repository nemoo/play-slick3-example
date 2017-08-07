package models

import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider
import com.github.takezoe.slick.blocking.BlockingH2Driver.blockingApi._


/**
  * Creates test data for use during development
  */
class TestData @Inject()(taskRepo: TaskRepo, projectRepo: ProjectRepo)(protected val dbConfigProvider: DatabaseConfigProvider) extends DAO {

  def createTestData(implicit session: Session) = {
    val p1Id = projectRepo.create("Alpha")
    projectRepo.addTask("blue", p1Id)
    projectRepo.addTask("red", p1Id)
    projectRepo.create("Beta")
  }
}
