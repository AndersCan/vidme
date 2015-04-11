package controllers

import database.implementations.MongoDBUserImpl
import models.UserAccount
import play.api.libs.json.JsError
import play.api.mvc.{BodyParsers, Action, Controller}
import play.api.libs.json._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Created by anders on 11/04/15.
 */
object SecurityController extends Controller {

  val m = new MongoDBUserImpl("TEST", "TEST")


  /**
   * Attempts to login with the given values. If successful adds a session to the user.
   * @return
   */

  import UserAccount.userAccountRead

  def login = Action.async(BodyParsers.parse.json) { request =>
    val loginResult = request.body.validate[UserAccount]
    loginResult.fold(
      errors => {
        Future(BadRequest(Json.obj("status" -> "BAD JSON", "message" -> JsError.toFlatJson(errors))))
      },
      user => {
        m.find(user).map { users =>
          if (users.nonEmpty) Redirect(routes.HomeController.index()).withSession("user" -> user.name)
          else Ok(Json.obj("status" -> "invalid", "msg" -> "user not found"))
        }
      }
    )
  }


  //  def loginMap = Action.async(parse.json) { request =>
  //    val loginResult = request.body.validate[UserAccount]
  //    loginResult.map(user => {
  //      case UserAccount => {
  //        m.find(user) map { users =>
  //          if (users.nonEmpty) {
  //            Ok("Found user")
  //          } else {
  //            Ok("No user found")
  //          }
  //        }
  //      }
  //      case _ => Ok("Wrong JSON")
  //    })
  //  }
}
