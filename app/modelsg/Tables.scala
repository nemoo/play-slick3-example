package modelsg

import play.api.db.slick.Profile

case class Activity(id: Int, name: String)
case class Item(id: Int, color: String)

trait Tables { this: Profile =>
  import profile.simple._
  
  import scala.slick.model.ForeignKeyAction
  import scala.slick.jdbc.{GetResult => GR}
  
  class Activities(tag: Tag) extends Table[Activity](tag, "ACTIVITY") {
    def * = (id, name) <> (Activity.tupled, Activity.unapply)
    def ? = (id.?, name.?).shaped.<>({r=>import r._; _1.map(_=> Activity.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    val id: Column[Int] = column[Int]("ID", O.AutoInc, O.PrimaryKey)
    val name: Column[String] = column[String]("NAME")
    
   }
  
  class Items(tag: Tag) extends Table[Item](tag, "ACTIVITY") {
    def * = (id, color) <> (Item.tupled, Item.unapply)
    def ? = (id.?, color.?).shaped.<>({r=>import r._; _1.map(_=> Item.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    val id: Column[Int] = column[Int]("ID", O.AutoInc, O.PrimaryKey)
    val color: Column[String] = column[String]("COLOR")
    
   }  
  
}
