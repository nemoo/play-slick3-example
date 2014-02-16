package controllers

//import play.api.mvc.Controller
//import play.api.mvc.Action
//import org.joda.time.DateTime
//import play.api.libs.json._
//
//import play.api.db.slick._
//
//import play.api.db.slick.DBAction
//import modelsg.Activities._
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.db.slick._
import play.api.Play.current

import views._
import models._

object Test extends Controller {

  def test1 = DBAction { implicit rs =>
    
	val data = Items.findByColor("xzy")
	val data2 = Activities.findByName("xzy")
//	var data = activityRepo.findByName("xy").firstOption
	
    Ok(data.toString)     
  }  
  

  
//  def test2 = DBAction { implicit rs =>
//    
//	val data = Activites.findByName("ysd")
////	var data = activityRepo.findByName("xy").firstOption
//	
//    Ok(data.toString)
//  }    
//
//  def test3 = DBAction { implicit rs =>
//    
//	val activity = Activites.findByName("xzy")
//	val item = itemRepo.findByColor("blue").firstOption
//
//	
//    Ok(activity.toString + item.toString)     
//  }    
//  
//  def addItem = DBAction { implicit rs =>
//    
//	Activites insert Activity(999, "newActivity")
//	
//	println(Activites.all)
//
//    Ok("")     
//  }     
}
