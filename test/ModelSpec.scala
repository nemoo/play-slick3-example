package test

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._
import play.api.db.slick.DB
import play.api.Play.current

class ModelSpec extends Specification {
  
  import models._

  // -- Date helpers
  
  def dateIs(date: java.util.Date, str: String) = new java.text.SimpleDateFormat("yyyy-MM-dd").format(date) == str
  
  // --
  
  "An Item" should {
    
    "be retrieved by color" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          val Some(task) = Tasks.findByColor("blue")
          Tasks.count must be_==(5)
          task.color must equalTo("blue")            
        }        
      }
    }
    
    "be inserted" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        
        DB.withSession{ implicit s =>
          Tasks.insert(Task(99,"black", 1))
          
          val Some(item) = Tasks.findByColor("black")      
          item.color must equalTo("black")  
          Tasks.count must be_==(6)
        }
      }
    }
    
    
    "be unchangeable" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        
        DB.withSession{ implicit s =>
          Tasks.count must be_==(5)
          Tasks.findByColor("cyan") must beNone      
        }
      }
    }   
    
    "be selectable distinct" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        
        DB.withSession{ implicit s =>
          val results = Tasks.distinctTest
          results.map(x => println(x.name))
          results must have size(1)      
        }
      }
    }       
    
  }
  
}