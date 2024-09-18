package types_scarango

import com.outr.arango.{Document, DocumentModel, DocumentRef, Field, Id, Index}
import fabric.rw._
import shared.mytypes.User

case class UserScarango (
              user: String,
              _id: Id[UserScarango ] = UserScarango.id()
  ) extends Document[UserScarango]


object UserScarango extends DocumentModel[UserScarango] {

    //override implicit val rw: ReaderWriter[UserScarango] = ccRW
    override implicit val rw: RW[UserScarango] = RW.gen

    override def indexes: List[Index] = Nil
    override val collectionName: String = "tbl_users"
    //override val serialization: Serialization[UserScarango] = Serialization.auto[UserScarango]

  def toScarango(item: User): UserScarango = UserScarango (
    user = item.user,
    _id = UserScarango.id(createId(item))
  )

  def toNative(item: UserScarango): User = User(
    user = item.user
  )

  def createId(item: User) = item.user

}