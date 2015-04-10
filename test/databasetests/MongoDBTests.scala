package databasetests

/**
 * Created by anders on 09/04/15.
 */

import database.MongoDBImpl
import models.UserAccount
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.language.postfixOps

class MongoDBTests extends FlatSpec with Matchers with ScalaFutures {
  val m: MongoDBImpl = new MongoDBImpl("TEST", "TEST")
  val un: String = "USERNAME"
  val pw: String = "PASSWORD"
  val defaultUser = UserAccount(un, pw)

  "The MongoDBImpl " must "be able to save a User" in {
    m.save(defaultUser)
  }

  it must "find the user that has been saved" in {
    val found = m.find(defaultUser)

    whenReady(found)(list => list.foreach {
      ua => {
        assert(ua.isInstanceOf[UserAccount])
      }
    })
  }

  it must "saved user has correct values" in {
    val found = m.find(defaultUser)

    val futList: Future[List[Boolean]] = found.map(users =>
      users.map(user => {
        (user.name.toString == "\"USERNAME\"") && (user.pw.toString == "\"PASSWORD\"")
      })
    )
    whenReady(futList)(list => list.foreach(b => {
      assert(b)
    })
    )
  }

  it must "delete user returns true" in {
    val tobeDeleted = UserAccount("deleteName", "deletePassword")
    whenReady(m.save(tobeDeleted))(res => {
      whenReady(m.delete(tobeDeleted))(res => {
        assert(res.ok)
      })
    })
  }
  it must "not find deleted user" in {
    val found = m.find(UserAccount("deleteName", "deletePassword"))
    whenReady(found)(list => assert(list.isEmpty))
  }

  it must "update a user with new values" in {
    val update = UserAccount("newname", "newpassword")
    val futUpdate = m.update(defaultUser, update)

    whenReady(futUpdate)(lastError => {
      assert(lastError.ok)
      val find = m.find(update)
      whenReady(find)(users => {
        assert(users.nonEmpty)
        users.foreach(u => {
          assert(u.name == "\"newname\"" && u.pw == "\"newpassword\"")
        })
      })
    })
  }
}
