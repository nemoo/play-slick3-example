package models

import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider

import slick.driver.JdbcProfile

import scala.concurrent.Future



case class Task(id: Long, color: String, status: TaskStatus.Value, project: Long) {

  def patch(color: Option[String], status: Option[TaskStatus.Value], project: Option[Long]): Task =
    this.copy(color = color.getOrElse(this.color),
              status = status.getOrElse(this.status),
              project = project.getOrElse(this.project))

}

object TaskStatus extends Enumeration {
  val ready = Value("ready")
  val set = Value("set")
  val go = Value("go")
}

class TaskRepo @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db
  import dbConfig.driver.api._
  private val Tasks = TableQuery[TasksTable]


  def findById(id: Long): Future[Task] =
    db.run(Tasks.filter(_.id === id).result.head)

  def findByColor(color: String): DBIO[Option[Task]] =
    Tasks.filter(_.color === color).result.headOption

  def findByProjectId(projectId: Long): Future[List[Task]] =
    db.run(Tasks.filter(_.project === projectId).to[List].result)

  def findByReadyStatus: DBIO[List[Task]] =
    Tasks.filter(_.status === TaskStatus.ready).to[List].result


  def partialUpdate(id: Long, color: Option[String], status: Option[TaskStatus.Value], project: Option[Long]): Future[Int] = {
    import scala.concurrent.ExecutionContext.Implicits.global

    val query = Tasks.filter(_.id === id)

    val update = query.result.head.flatMap {task =>
      query.update(task.patch(color, status, project))
    }

    db.run(update)
  }

  def all(): DBIO[Seq[Task]] =
    Tasks.result

  def insert(Task: Task): DBIO[Long] =
    Tasks returning Tasks.map(_.id) += Task

  def _deleteAllInProject(projectId: Long): DBIO[Int] =
    Tasks.filter(_.project === projectId).delete

  private class TasksTable(tag: Tag) extends Table[Task](tag, "TASK") {

    def id = column[Long]("ID", O.AutoInc, O.PrimaryKey)
    def color = column[String]("COLOR")
    def status = column[TaskStatus.Value]("STATUS")
    def project = column[Long]("PROJECT")

    def * = (id, color, status, project) <> (Task.tupled, Task.unapply)
    def ? = (id.?, color.?, status.?, project.?).shaped.<>({ r => import r._; _1.map(_ => Task.tupled((_1.get, _2.get, _3.get, _4.get))) }, (_: Any) => throw new Exception("Inserting into ? Taskion not supported."))
  }

  implicit val taskStatusColumnType = MappedColumnType.base[TaskStatus.Value, String](
    _.toString, string => TaskStatus.withName(string))

}
