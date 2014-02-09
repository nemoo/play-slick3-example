package modelsg

import play.api.db.slick.Profile

/** Entity class storing rows of table Activities
 *  @param id Database column ID AutoInc, PrimaryKey
 *  @param name Database column NAME 
 *    */
case class Activity(id: Int, name: String)
case class Item(id: Int, color: String)

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables { this: Profile =>
  import profile.simple._
  
  import scala.slick.model.ForeignKeyAction
  import scala.slick.jdbc.{GetResult => GR}
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  
 /** Table description of table ACTIVITY. Objects of this class serve as prototypes for rows in queries. */
  class Activities(tag: Tag) extends Table[Activity](tag, "ACTIVITY") {
    def * = (id, name) <> (Activity.tupled, Activity.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (id.?, name.?).shaped.<>({r=>import r._; _1.map(_=> Activity.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column ID AutoInc, PrimaryKey */
    val id: Column[Int] = column[Int]("ID", O.AutoInc, O.PrimaryKey)
    /** Database column ATYPE  */
    val name: Column[String] = column[String]("NAME")
    
   }
  
  class Items(tag: Tag) extends Table[Item](tag, "ACTIVITY") {
    def * = (id, color) <> (Item.tupled, Item.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (id.?, color.?).shaped.<>({r=>import r._; _1.map(_=> Item.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column ID AutoInc, PrimaryKey */
    val id: Column[Int] = column[Int]("ID", O.AutoInc, O.PrimaryKey)
    /** Database column COLOR  */
    val color: Column[String] = column[String]("COLOR")
    
   }  
  
}
