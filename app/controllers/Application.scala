package controllers

import javax.inject.Inject

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.Action
import play.api.mvc.Controller

import models.{TaskDAO, ProjectDAO}

class Application @Inject()(projectDAO: ProjectDAO, taskDAO: TaskDAO) extends Controller {



  /*
  def addTaskToProject(taskName: String, projectId: Long) = Action { implicit rs =>
    DB.withSession{ implicit connection =>
    	val project = Projects.findById(projectId).get
    	
      val newTaskId = Tasks insert Task(0,taskName,project.id)
      
      val task = Tasks.findById(newTaskId).get
      
      val tasksOfProject = Projects.findTasks(projectId)
      
      Ok("I have created " + task + " The project has now these tasks: " + tasksOfProject.mkString(", "))     
    }
  }


  def test1 = Action { implicit rs =>
    DB.withSession{ implicit connection =>
    	val data = Tasks.findByColor("blue")
    	val data2 = Projects.findByName("xzy")
      Ok(data.toString)     
    }
  }
  */

  def test1 = Action.async { implicit rs =>
    projectDAO.all().zip(taskDAO.all()).map {case (cats, dogs) => Ok("OK") }
  }
    
}
