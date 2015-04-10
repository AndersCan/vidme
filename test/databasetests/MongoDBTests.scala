package databasetests

/**
 * Created by anders on 09/04/15.
 */

import database.MongoDBImpl
import models.UserAccount
import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.{Future, Await}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.postfixOps
import scala.concurrent.duration._

class MongoDBTests extends FlatSpec with Matchers {
  val m: MongoDBImpl = new MongoDBImpl("TEST", "TEST")

  "The MongoDBImpl " must "be able to save a User" in {
    m.save(new UserAccount("OKAYNAME", "PASSWORD"))
  }
  it must "find the user that has been saved" in {
    val found = m.find(new UserAccount("OKAYNAME", "PASSWORD"))

    val flist: Future[List[Boolean]] = found.map(usrs =>
      usrs.map(user => {
        println(s"USER: $user")
        user.name == "OKAYNAME" && user.pw == "PASSWORD"
      })
    )
    flist.onComplete(list => list.foreach(b => {
      assert(!b.isEmpty)
    }
    ))

    Await.ready(flist, 10 seconds)
    val list = flist.value.get.get
    list foreach (b => println(b))

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
