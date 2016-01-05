package models

import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

case class Task(id: Long, color: String, status: TaskStatus.Value, project: Long)

object TaskStatus extends Enumeration {
  val ready = Value("ready")
  val set = Value("set")
  val go = Value("go")
}

class TaskRepo @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig.driver.api._
  private val Tasks = TableQuery[TasksTable]


  def findById(id: Long): DBIO[Task] =
    Tasks.filter(_.id === id).result.head

  def findByColor(color: String): DBIO[Option[Task]] =
    Tasks.filter(_.color === color).result.headOption

  def findByProjectId(projectId: Long): DBIO[List[Task]] =
    Tasks.filter(_.project === projectId).to[List].result

  def findByReadyStatus: DBIO[List[Task]] =
    Tasks.filter(_.status === TaskStatus.ready).to[List].result

  def all(): DBIO[Seq[Task]] =
    Tasks.result

  def insert(Task: Task): DBIO[Long] =
    Tasks returning Tasks.map(_.id) += Task


  private class TasksTable(tag: Tag) extends Table[Task](tag, "TASK") {

    def id = column[Long]("ID", O.AutoInc, O.PrimaryKey)
    def color = column[String]("COLOR")
    def status = column[TaskStatus.Value]("STATUS")
    def project = column[Long]("PROJECT")

    def * = (id, color, status, project) <> (Task.tupled, Task.unapply _)
    def ? = (id.?, color.?, status.?, project.?).shaped.<>({ r => import r._; _1.map(_ => Task.tupled((_1.get, _2.get, _3.get, _4.get))) }, (_: Any) => throw new Exception("Inserting into ? Taskion not supported."))
  }

  implicit val taskStatusColumnType = MappedColumnType.base[TaskStatus.Value, String](
    _.toString, string => TaskStatus.withName(string))

}
