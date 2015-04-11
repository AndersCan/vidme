package controllers

import database.MongoDBImpl
import models.UserAccount
import play.api.libs.json.JsError
import play.api.mvc.{BodyParsers, Action, Controller}
import play.api.libs.json._
import play.api.libs.functional.syntax._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

/**
 * Created by anders on 11/04/15.
 */
object SecurityController extends Controller {

  val m = new MongoDBImpl("TEST", "TEST")

  implicit val userAccountRead: Reads[UserAccount] = (
    (JsPath \ "name").read[String] and
      (JsPath \ "pw").read[String]
    )(UserAccount.apply _)

  def login = Action.async(BodyParsers.parse.json) { request =>
    val loginResult = request.body.validate[UserAccount]
    loginResult.fold(
      errors => {
        Future(BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toFlatJson(errors))))
      },
      user => {
        m.find(user).map { users =>
          if (users.nonEmpty) Ok("Found user")
          else Ok("No User Found")
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
