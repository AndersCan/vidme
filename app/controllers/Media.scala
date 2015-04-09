package controllers

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.{Action, Controller}

/**
 * Created by anders on 09/04/15.
 */
object Media extends Controller {

  def getVideo(id: String) = Action.async {
    val futureString = scala.concurrent.Future {
      "Asynch"
    }
    futureString.map(i => Ok("Got result: " + i))
  }

}
