package database

import models.UserAccount

import scala.concurrent.Future

/**
 * Created by anders on 09/04/15.
 */
trait UserStorage {
  /**
   * Finds a user in the database
   * @param user user to find
   * @return found user
   */
  def find(user: UserAccount): Future[List[UserAccount]]

  /**
   * Saves the user details to the database
   * @param user
   * @return
   */
  def save(user: UserAccount): Future[Any]

  /**
   * Updates the given user with new details to the database
   * @param oldUser old user
   * @param updatedUser updated users
   * @return
   */
  def update(oldUser: UserAccount, updatedUser: UserAccount): Future[Any]

  /**
   * Deletes the user details from the database
   * @param user to delete
   * @param count how many to delete
   * @return Boolean if command was successful, not if delete was performed
   */
  def delete(user: UserAccount, count: Int): Future[Any]
}
