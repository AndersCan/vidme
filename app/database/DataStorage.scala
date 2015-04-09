package database

import scala.concurrent.Future

/**
 * Created by anders on 09/04/15.
 */
trait DataStorage {
  def find(name: String, pw: String): Future[Option[]]
}
