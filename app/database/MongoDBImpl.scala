package database

import models.User
import play.api.libs.json.{JsArray, JsObject, Json}
import play.modules.reactivemongo.MongoController
import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.api.Cursor

import scala.concurrent.Future

/**
 * Created by anders on 09/04/15.
 */
class MongoDBImpl extends DataStorage with MongoController {

  /*
 * Get a JSONCollection (a Collection implementation that is designed to work
 * with JsObject, Reads and Writes.)
 * Note that the `collection` is not a `val`, but a `def`. We do _not_ store
 * the collection reference to avoid potential problems in development with
 * Play hot-reloading.
 */
  def collection: JSONCollection = db.collection[JSONCollection]("persons")

  /**
   * Finds a user in the database
   * @param user user to find
   * @return found user
   */
  override def find(user: User): Future[Option[User]] = {
    val cursor: Cursor[JsObject] = collection.
      find(Json.obj("name" -> user.name, "pw" -> user.pw))
      .cursor[JsObject]

    // gather all the JsObjects in a list
    val futurePersonsList: Future[List[JsObject]] = cursor.collect[List](1)

    // transform the list into a JsArray
    val futurePersonsJsonArray: Future[JsArray] = futurePersonsList.map { persons =>
      Json.arr(persons)
    }

    // everything's ok! Let's reply with the array
    futurePersonsJsonArray.map { persons =>
      Some(
        new User(
          persons.\("name").toString(),
          persons.\("pw").toString()
        ))
    }
  }

  /**
   * Saves the user details to the database
   * @param user
   * @return
   */
  override def save(user: User): Future[Either[String, Unit]] = {
    val json = Json.obj(
      "name" -> user.name,
      "pw" -> user.pw
    )
    collection.insert(json).map(lastError =>
      Left(s"Mongo LastError: $lastError"))
  }

  /**
   * checks if the given user exists.
   * @param user user with values to match
   * @return the found user
   */
  override def exists(user: User): Future[Option[User]] = ???
}
