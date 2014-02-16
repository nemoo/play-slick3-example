package models

import play.api.db.slick.Config.driver.simple._

case class Item(id: Int, color: String)

class ItemsTable(tag: Tag) extends Table[Item](tag, "ITEM") {
  def * = (id, color) <> (Item.tupled, Item.unapply)
  def ? = (id.?, color.?).shaped.<>({ r => import r._; _1.map(_ => Item.tupled((_1.get, _2.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

  val id: Column[Int] = column[Int]("ID", O.AutoInc, O.PrimaryKey)
  val color: Column[String] = column[String]("COLOR")

}

object Items extends DAO {

  def findByColor(color: String)(implicit s: Session) =
    Items.filter(_.color === color).firstOption

  def insert(item: Item)(implicit s: Session) {
    Items.insert(item)
  }    
  
  def count(implicit s: Session): Int =
    Items.length.run  
} 


