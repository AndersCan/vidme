package models

import reactivemongo.bson.BSONObjectID


/**
 * Created by anders on 09/04/15.
 */
case class UserAccount(name: String, pw: String)

object UserAccount {
  //  def apply(name: String, pw: String): UserAccount = {
  //    new UserAccount(name, pw)
  //  }

  //  implicit val reader = UserAccountReader
  //  implicit val writer = UserAccountWriter
  //
  //  object UserAccountReader extends BSONDocumentReader[UserAccount] {
  //    def read(doc: BSONDocument): UserAccount =
  //      UserAccount(
  //        doc.getAs[String]("name").get,
  //        doc.getAs[String]("pw").get,
  //        doc.getAs[BSONObjectID]("_id").get
  //      )
  //  }
  //
  //
  //  object UserAccountWriter extends BSONDocumentWriter[UserAccount] {
  //    def write(userAccount: UserAccount): BSONDocument =
  //      BSONDocument(
  //        "_id" -> userAccount.id,
  //        "name" -> userAccount.name,
  //        "pw" -> userAccount.pw
  //      )
  //  }

  //  implicit object UserWrites extends Writes[UserAccount] {
  //    override def writes(u: UserAccount): JsValue = Json.obj(
  //      "_id" -> JsString(u.id.toString()),
  //      "name" -> JsString(u.name),
  //      "pw" -> JsString(u.pw)
  //    )
  //  }
  //
  //  implicit object UserReads extends Reads[UserAccount] {
  //    override def reads(json: JsValue): JsResult[UserAccount] =
  //
  //  }

}
