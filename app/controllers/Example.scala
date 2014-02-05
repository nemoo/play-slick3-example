package controllers

import play.api.mvc.Controller
import org.joda.time.DateTime
import play.api.libs.json._

import play.api.db.slick._

import play.api.db.slick.DBAction
import modelsg.current.activityRepo
import modelsg.current.itemRepo
import modelsg.current.activityRepo.profile.simple._

object Test extends Controller {

  def test1 = DBAction { implicit rs =>
    
	val data = activityRepo.findByName("xzy")
//	var data = activityRepo.findByName("xy").firstOption
	
    Ok(data.toString)     
  }  
  
  def test2 = DBAction { implicit rs =>
    
	val data = activityRepo.findByName("ysd")
//	var data = activityRepo.findByName("xy").firstOption
	
    Ok(data.toString)
  }    

  def test3 = DBAction { implicit rs =>
    
	val activity = activityRepo.findByName("xzy")
	val item = itemRepo.findByColor("blue").firstOption

	
    Ok(activity.toString + item.toString)     
  }    
}
