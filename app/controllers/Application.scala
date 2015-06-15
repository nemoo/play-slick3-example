package controllers

import play.api.mvc._
import play.api.db.slick._
import play.api.Play.current
import models._

object Application extends Controller {


  def addTaskToProject(taskName: String, projectId: Long) = Action { implicit rs =>
//    DB.withSession{ implicit connection =>
//    	val project = Projects.findById(projectId).get
//
//      val newTaskId = Tasks insert Task(0,taskName,project.id)
//
//      val task = Tasks.findById(newTaskId).get
//
//      val tasksOfProject = Projects.findTasks(projectId)
//
//      Ok("I have created " + task + " The project has now these tasks: " + tasksOfProject.mkString(", "))
//    }
    Ok
  }  

  def test1 = Action { implicit rs =>
//    DB.withSession{ implicit connection =>
//    	val data = Tasks.findByColor("blue")
//    	val data2 = Projects.findByName("xzy")
//      Ok(data.toString)
//    }
    Ok
  }  
    
}
