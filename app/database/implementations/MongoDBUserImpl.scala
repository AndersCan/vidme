package database.implementations

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
class MongoDBUserImpl(val dbName: String, val colName: String) extends UserStorage {
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
  override def save(user: UserAccount): Future[LastError] = {
    val json = Json.obj(
      "name" -> user.name,
      "pw" -> user.pw
    )

    collection.insert(json).map(lastError => {
      lastError
    })
  }

  /**
   * Updates the given user with new details to the database
   * @param oldUser old user
   * @param updatedUser updated users
   * @return lastError
   */
  override def update(oldUser: UserAccount, updatedUser: UserAccount): Future[LastError] = {
    val selector = UserAccount.getJson(oldUser)

    val modifier = Json.obj(
      "$set" -> UserAccount.getJson(updatedUser)
    )
    println(selector)
    println(modifier)
    // get a future update
    val futureUpdate = collection.update(selector, modifier)
    futureUpdate
  }

  /**
   * Deletes the user details from the database
   * @param user UserAccount of user to delete
   * @return Future[true] if command was executed without errors.
   */
  override def delete(user: UserAccount, count: Int = 1): Future[LastError] = {
    val futureRemove = collection.remove(Json.obj("name" -> user.name, "pw" -> user.pw))
    futureRemove
  }
}
