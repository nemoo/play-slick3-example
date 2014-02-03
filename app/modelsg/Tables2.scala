package modelsg
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables2 extends {
  val profile = scala.slick.driver.H2Driver
} with Tables2

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables2 {
  val profile: scala.slick.driver.JdbcProfile
  import profile.simple._
  
  import scala.slick.model.ForeignKeyAction
  import scala.slick.jdbc.{GetResult => GR}
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  
  /** Entity class storing rows of table Activities
   *  @param id Database column ID AutoInc, PrimaryKey
   *  @param name Database column NAME 
   *    */
  case class Activity(id: Int, atype: String)

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
  /** Collection-like TableQuery object for table Activities */
  lazy val Activities = new TableQuery(tag => new Activities(tag))
  
}