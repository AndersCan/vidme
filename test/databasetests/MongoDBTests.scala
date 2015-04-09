package databasetests

/**
 * Created by anders on 09/04/15.
 */

import database.MongoDBImpl
import models.UserAccount
import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.postfixOps

class MongoDBTests extends FlatSpec with Matchers {
  val m: MongoDBImpl = new MongoDBImpl("TEST", "TEST")

  "The MongoDBImpl " must "be able to save a User" in {
    m.save(new UserAccount("name", "password"))
  }
  it must "find the user that has been saved" in {
    val found = m.find(new UserAccount("name", "password"))
    found.map(usrs =>
      assert(usrs.head.name == "name" && usrs.head.pw == "pw")
    )
  }
  //  it must "saved user has correct values" in {
  //    val found = m.find(new UserAccount("name", "password")) map {
  //      case Some(UserAccount(un, pw, id)) => UserAccount(un, pw, id)
  //      case _ => None
  //    }
  //    found.onComplete({
  //      case Success(UserAccount(un, pw, id)) => {
  //        //Do something with my list
  //        assert(true)
  //      }
  //      case Failure(_) => {
  //        assert(false)
  //        //Do something with my error
  //      }
  //    })
  //
  //    val found2 = m.find(new UserAccount("name", "password"))
  //    val usr = Await.ready(found2, 10 seconds)
  //
  //    val result = usr.map(oua => {
  //      val x = oua.get
  //      println("This is the type: " + x)
  //    }) match {
  //      case _ => true
  //      case _ => false
  //    }
  //    assert(result)
  //  }
}
