package models

import scala.concurrent.Future
import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile

case class Task(id: Long, color: String, project: Long)

class TaskDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  //val dbConfig = dbConfigProvider.get[JdbcProfile]

  import driver.api._

  private val Tasks = TableQuery[TasksTable]

  def findById(id: Long): Future[Option[Task]] =
    db.run(Tasks.filter(_.id === id).result.headOption)

  def findByColor(color: String): Future[Option[Task]] =
    db.run(Tasks.filter(_.color === color).result.headOption)

  /*
  def count(implicit s: Session): Int =
    Tasks.length.run

  def distinctTest(implicit s: Session): List[Task] = (for {
    Task <- Tasks
    task <- Tasks if task.Task === Task.id
    if task.color like "blue"
  } yield (Task)).groupBy(x=>x).map(_._1).list//http://stackoverflow.com/questions/18256768/select-distinct-in-scala-slick
  */

  def all(): Future[Seq[Task]] = db.run(Tasks.result)

  def insert(Task: Task): Future[Unit] = db.run(Tasks += Task).map { _ => () }


  private class TasksTable(tag: Tag) extends Table[Task](tag, "TASK") {

    def id = column[Long]("ID", O.AutoInc, O.PrimaryKey)
    def color = column[String]("COLOR")
    def project = column[Long]("PROJECT")

    def * = (id, color, project) <> (Task.tupled, Task.unapply _)
    def ? = (id.?, color.?, project.?).shaped.<>({ r => import r._; _1.map(_ => Task.tupled((_1.get, _2.get, _3.get))) }, (_: Any) => throw new Exception("Inserting into ? Taskion not supported."))

  }
}