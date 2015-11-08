package controllers

import javax.inject.Inject
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.Action
import play.api.mvc.Controller
import models.{Project, TaskDAO, ProjectDAO, Task}


class Application @Inject()(projectDAO: ProjectDAO, taskDAO: TaskDAO) extends Controller {


  def addTaskToProject(taskName: String, projectId: Long) = Action.async { implicit rs =>
    val result = for {
      project <- projectDAO.findById(projectId)
      taskId <- taskDAO.insert(Task(0, taskName, project.id))
    }yield taskId


    result.map{ taskId =>
      Ok("I have created a new task: " + taskId)
    }
  }

  def createProject(name: String)= Action.async { implicit rs =>
    val project = Project(0, name)
    projectDAO.insert(project).map(id => Ok(s"project $id created") )
  }

  def listProjects = Action.async { implicit rs =>
    projectDAO.all.map{projects =>
        Ok(s"Projects: ${projects.mkString}")
    }
  }
    
}
