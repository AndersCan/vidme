package controllers

import play.api.mvc._

/**
 * HomeController is used for main page specific tasks
 */
object HomeController extends Controller {

  def index = Action { implicit result =>
    Ok(views.html.index("Your new application is ready."))
  }

}