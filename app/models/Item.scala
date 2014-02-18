package models

import play.api.db.slick.Config.driver.simple._

case class Item(id: Long, color: String, activity: Long)

class ItemsTable(tag: Tag) extends Table[Item](tag, "ITEM") {
  def * = (id, color, activity) <> (Item.tupled, Item.unapply)
  def ? = (id.?, color.?, activity.?).shaped.<>({ r => import r._; _1.map(_ => Item.tupled((_1.get, _2.get, _3.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

  val id: Column[Long] = column[Long]("ID", O.AutoInc, O.PrimaryKey)
  val color: Column[String] = column[String]("COLOR")
  val activity: Column[Long] = column[Long]("ACTIVITY")

}

object Items extends DAO {

  def findByColor(color: String)(implicit s: Session) =
    Items.filter(_.color === color).firstOption

  def insert(item: Item)(implicit s: Session) {
    Items.insert(item)
  }    
  
  def count(implicit s: Session): Int =
    Items.length.run  
    
  def distinctTest(implicit s: Session): List[Activity] = (for {
      activity <- Activities 
      item <- Items if item.activity === activity.id
      if item.color like "blue"
    } yield (activity)).groupBy(x=>x).map(_._1).list//http://stackoverflow.com/questions/18256768/select-distinct-in-scala-slick      

} 


