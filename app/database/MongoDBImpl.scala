package database

import models.UserAccount
import play.api.libs.json.{JsValue, Json}
import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.api.{Cursor, MongoDriver}
import reactivemongo.bson.{BSONDocument, BSONObjectID}
import reactivemongo.core.commands.LastError

import scala.concurrent.Future
import scala.util.{Success, Failure}


// TODO - Create explisit Execution context

import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by anders on 09/04/15.
 */
class MongoDBImpl(val dbName: String, val colName: String) extends DataStorage {
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
        println("FOUND: " + (usr \ "_id").toString())
        new UserAccount((usr \ "_id").toString(),
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
   * @return
   */
  override def save(user: UserAccount): Future[LastError] = {
    val json = Json.obj(
      "_id" -> BSONObjectID.generate.stringify,
      "name" -> user.name,
      "pw" -> user.pw
    )
    collection.insert(json).map(lastError => {
      lastError
    })
  }

  /**
   * checks if the given user exists.
   * @param user user with values to match
   * @return the found user
   */
  override def exists(user: UserAccount): Future[Option[UserAccount]] = ???

  /**
   * Deletes the user details from the database
   * @param user
   * @return
   */
  override def delete(user: UserAccount): Future[Boolean] = {
    delete(user._id)
  }

  /**
   * Deletes the user details from the database
   * @param userId BSONObjectID of user to delete
   * @return Future[true] if successful
   */
  override def delete(userId: String): Future[Boolean] = {
    println("delete user: " + Json.obj("_id" -> userId))
    val futureRemove = collection.remove(Json.obj("id" -> userId))
    futureRemove map (lastError => {

      println(lastError.ok)
      println(lastError.message)
      lastError.ok
    })
  }
}
