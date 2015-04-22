package database

import models.UserAccount

import scala.concurrent.Future

/**
 * UserStorage is used to save UserAccounts to a database.
 *
 * Returned Boolean values represent if the command was successfully run on the database, not if they changed anything.
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
  def save(user: UserAccount): Future[Boolean]

  /**
   * Updates the given user with new details to the database
   * @param oldUser old user
   * @param updatedUser updated users
   * @return
   */
  def update(oldUser: UserAccount, updatedUser: UserAccount): Future[Boolean]

  /**
   * Deletes the user details from the database
   * @param user to delete
   * @param count how many to delete
   * @return Boolean if command was successful, not if delete was performed
   */
  def delete(user: UserAccount, count: Int = 1): Future[Boolean]
}
