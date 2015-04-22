package database.implementations

import javax.inject.Singleton

import database.UserStorage
import models.UserAccount
import play.api.libs.json.{JsValue, Json}
import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.api.{Cursor, MongoDriver}
import reactivemongo.core.commands.LastError

import scala.concurrent.Future


// TODO - Create explisit Execution context

import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by anders on 09/04/15.
 */
@Singleton
class MongoDBUserImpl() extends UserStorage {
  val dbName = "TEST"
  val colName = "TEST"

  val driver = new MongoDriver()
  val connection = driver.connection(List("localhost:27017"))

  def db = connection.db(dbName)

  /*
 * Get a JSONCollection (a Collection implementation that is designed to work
 * with JsObject, Reads and Writes.)
 * Note that the `collection` is not a `val`, but a `def`. We do _not_ store
 * the collection reference to avoid potential problems in development with
 * Play hot-reloading.
 */
  def collection: JSONCollection = db.collection[JSONCollection](colName)

  /**
   * Finds a user in the database
   * @param user user to find
   * @return found user
   */
  override def find(user: UserAccount): Future[List[UserAccount]] = {
    val cursor: Cursor[JsValue] = collection.
      find(Json.obj("name" -> user.name, "pw" -> user.pw))
      .cursor[JsValue]

    // gather all the JsObjects in a list
    val futurePersonsList: Future[List[JsValue]] = cursor.collect[List]()

    val futureUserAccount: Future[List[UserAccount]] = futurePersonsList.map(usrlist => {
      usrlist map (usr => {
        new UserAccount(
          (usr \ "name").toString(),
          (usr \ "pw").toString()
        )
      })
    })
    futureUserAccount
  }

  /**
   * Saves the user details to the database
   * @param user
   * @return lastError
   */
  override def save(user: UserAccount): Future[Boolean] = {
    val json = Json.obj(
      "name" -> user.name,
      "pw" -> user.pw
    )

    val futureUpdate = collection.insert(json)
    getBoolean(futureUpdate)
  }

  /**
   * Updates the given user with new details to the database
   * @param oldUser old user
   * @param updatedUser updated users
   * @return lastError
   */
  override def update(oldUser: UserAccount, updatedUser: UserAccount): Future[Boolean] = {
    val selector = UserAccount.getJson(oldUser)

    val modifier = Json.obj(
      "$set" -> UserAccount.getJson(updatedUser)
    )
    // get a future update
    val futureUpdate = collection.update(selector, modifier)
    getBoolean(futureUpdate)
  }

  /**
   * Deletes the user details from the database
   * @param user UserAccount of user to delete
   * @param count Optional How many users to delete. Default is 1
   * @return Future[true] if command was executed without errors.
   */
  override def delete(user: UserAccount, count: Int): Future[Boolean] = {
    val futureRemove = collection.remove(Json.obj("name" -> user.name, "pw" -> user.pw))
    getBoolean(futureRemove)
  }

  def getBoolean(le: Future[LastError]): Future[Boolean] = {
    le.map {
      case lastError if lastError.ok => true
      case _ => false
    }
  }
}
