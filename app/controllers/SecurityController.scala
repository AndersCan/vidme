package controllers

import javax.inject.Inject

import database.UserStorage
import models.UserAccount
import play.api.libs.json.JsError
import play.api.mvc.{BodyParsers, Action, Controller}
import play.api.libs.json._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Created by anders on 11/04/15.
 */
class SecurityController @Inject()(userStorage: UserStorage) extends Controller {

  //  val m = new MongoDBUserImpl()

  /**
   * Attempts to login with the given values. If successful adds a session to the user.
   * @return
   */
  // Implicit import to read UserAccount from JSON

  import UserAccount.userAccountRead

  def login = Action.async(BodyParsers.parse.json) { request =>
    val loginResult = request.body.validate[UserAccount]
    loginResult.fold(
      errors => {
        Future(BadRequest(Json.obj("status" -> "error", "msg" -> JsError.toFlatJson(errors))))
      },
      user => {
        userStorage.find(user).map { users =>
          // TODO WithSession is currently not used
          if (users.nonEmpty) Ok(Json.obj("status" -> "ok", "msg" -> "user found")).withSession("user" -> user.name)
          else Ok(Json.obj("status" -> "notfound", "msg" -> "Sorry, that user does not exist"))
        }
      }
    )
  }

  def register = Action.async(BodyParsers.parse.json) { implicit request =>
    val loginResult = request.body.validate[UserAccount]
    loginResult.fold(
      errors => {
        Future(BadRequest(Json.obj("status" -> "error", "msg" -> JsError.toFlatJson(errors))))
      },
      user => {
        userStorage.find(user).map { users =>
          // TODO WithSession is currently not used
          if (users.isEmpty) {
            userStorage.save(user)
            Ok(Json.obj("status" -> "ok", "msg" -> "user saved")).withSession("user" -> user.name)
          }
          else Ok(Json.obj("status" -> "error", "msg" -> "Sorry, that user already exist"))
        }
      }
    )
  }
}
