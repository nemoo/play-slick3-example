package controllers

import play.api.mvc.Controller
import org.joda.time.DateTime
import play.api.libs.json._

import play.api.db.slick._

import play.api.db.slick.DBAction
import modelsg.current.activityRepo
import modelsg.current.activityRepo.profile.simple._

object Test extends Controller {
  
  def test1 = DBAction { implicit rs =>
    
	val data = activityRepo.findByName("xy").firstOption
//	var data = activityRepo.findByName("xy").firstOption
	
    Ok(data.toString)     
  }  
  
  def test2 = DBAction { implicit rs =>
    
	val data = activityRepo.findByName("xyxd").firstOption
//	var data = activityRepo.findByName("xy").firstOption
	
    Ok(data.toString)
  }    

}