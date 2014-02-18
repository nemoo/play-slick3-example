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
          val Some(item) = Items.findByColor("blue")
          Items.count must be_==(5)
          item.color must equalTo("blue")            
        }        
      }
    }
    
    "be inserted" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        
        DB.withSession{ implicit s =>
          Items.insert(Item(99,"black", 1))
          
          val Some(item) = Items.findByColor("black")      
          item.color must equalTo("black")  
          Items.count must be_==(6)
        }
      }
    }
    
    
    "be unchangeable" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        
        DB.withSession{ implicit s =>
          Items.count must be_==(5)
          Items.findByColor("cyan") must beNone      
        }
      }
    }   
    
    "be selectable distinct" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        
        DB.withSession{ implicit s =>
          val results = Items.distinctTest
          results.map(x => println(x.name))
          results must have size(2)      
        }
      }
    }       
    
  }
  
}