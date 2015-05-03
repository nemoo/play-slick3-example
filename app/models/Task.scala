package models

import play.api.db.slick.Config.driver.simple._

case class Task(id: Long, color: String, activity: Long)

class TasksTable(tag: Tag) extends Table[Task](tag, "TASK") {
  def * = (id, color, project) <> (Task.tupled, Task.unapply)
  def ? = (id.?, color.?, project.?).shaped.<>({ r => import r._; _1.map(_ => Task.tupled((_1.get, _2.get, _3.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

  val id: Column[Long] = column[Long]("ID", O.AutoInc, O.PrimaryKey)
  val color: Column[String] = column[String]("COLOR")
  val project: Column[Long] = column[Long]("PROJECT")
}

object Tasks extends DAO {

  def findByColor(color: String)(implicit s: Session): Option[Task] =
    Tasks.filter(_.color === color).firstOption

  def findById(id: Long)(implicit s: Session): Option[Task] =
      Tasks.filter(_.id === id).firstOption
  
  def insert(project: Task)(implicit session: Session): Long = {
    val id = (Tasks returning Tasks.map(_.id)) += project
    id
  }    
  
  def count(implicit s: Session): Int =
    Tasks.length.run  
    
  def distinctTest(implicit s: Session): List[Project] = (for {
      project <- Projects 
      task <- Tasks if task.project === project.id
      if task.color like "blue"
    } yield (project)).groupBy(x=>x).map(_._1).list//http://stackoverflow.com/questions/18256768/select-distinct-in-scala-slick      

} 


