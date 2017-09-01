package models

import javax.inject.{Inject, Singleton}

import play.api.db.slick.DatabaseConfigProvider
import com.github.takezoe.slick.blocking.BlockingH2Driver.blockingApi._
import Implicits._
import play.api.cache.AsyncCacheApi
import slick.dbio.Effect
import slick.sql.FixedSqlAction



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

@Singleton
class TaskRepo @Inject()(cache: AsyncCacheApi)
                        (protected val dbConfigProvider: DatabaseConfigProvider) extends DAO{

  def findById(id: Long)(implicit session: Session): Task =
    Tasks.filter(_.id === id)
      .first

  def findByColor(color: String)(implicit session: Session): Option[Task] =
    Tasks.filter(_.color === color)
      .firstOption

  def findByProjectId(projectId: Long)(implicit session: Session): List[Task] =
    Tasks.filter(_.project === projectId)
      .list

  def findByReadyStatus(implicit session: Session): List[Task] =
    Tasks.filter(_.status === TaskStatus.ready)
      .list


  def partialUpdate(id: Long, color: Option[String], status: Option[TaskStatus.Value], project: Option[Long])(implicit session: Session): Int = {

    val task = findById(id)
    Tasks.filter(_.id === id)
      .update(task.patch(color, status, project))
  }

  def all()(implicit session: Session): Seq[Task] =
    Tasks.list

  def insert(task: Task)(implicit session: Session): Long =
    (Tasks returning Tasks.map(_.id))
      .insert(task)

  def insertOrUpdate(task: Task)(implicit session: Session): Unit =
    (Tasks returning Tasks.map(_.id))
      .insertOrUpdate(task)

  def _deleteAllInProject(projectId: Long)(implicit session: Session): Int =
    Tasks.filter(_.project === projectId)
      .delete

}

private class TasksTable(tag: Tag) extends Table[Task](tag, "TASK") {

  def id = column[Long]("ID", O.AutoInc, O.PrimaryKey)
  def color = column[String]("COLOR")
  def status = column[TaskStatus.Value]("STATUS")
  def project = column[Long]("PROJECT")

  def * = (id, color, status, project) <> (Task.tupled, Task.unapply)
  def ? = (id.?, color.?, status.?, project.?).shaped.<>({ r => import r._; _1.map(_ => Task.tupled((_1.get, _2.get, _3.get, _4.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))
}

