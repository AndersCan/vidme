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
        Future(BadRequest(Json.obj("status" -> "error", "msg" -> JsError.toFlatJson(errors))))
      },
      user => {
        println(s"Find this shit: $user")
        m.find(user).map { users =>
          println(s"Found this shit: $users")
          if (users.nonEmpty) Ok(Json.obj("status" -> "ok", "msg" -> "user found")).withSession("user" -> user.name)
          else Ok(Json.obj("status" -> "notfound", "msg" -> "Sorry, that user does not exist"))
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
