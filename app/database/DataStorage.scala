package database

import models.UserAccount
import reactivemongo.bson.BSONObjectID

import scala.concurrent.Future

/**
 * Created by anders on 09/04/15.
 */
trait DataStorage {
  /**
   * Finds a user in the database
   * @param user user to find
   * @return found user
   */
  def find(user: UserAccount): Future[List[UserAccount]]

  /**
   * checks if the given user exists.
   * @param user user with values to match
   * @return the found user
   */
  def exists(user: UserAccount): Future[Option[UserAccount]]

  /**
   * Saves the user details to the database
   * @param user
   * @return
   */
  def save(user: UserAccount): Future[Any]

  /**
   * Deletes the user details from the database
   * @param user
   * @return
   */
  def delete(user: UserAccount): Future[Boolean]

  /**
   * Deletes the user details from the database
   * @param userId BSONObjectID of user to delete
   * @return Future[true] if successful
   */
  def delete(userId: String): Future[Boolean]
}
