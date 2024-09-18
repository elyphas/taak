package shared

object EventsDB {
  sealed trait EventsDBResult {
      val msg: String
  }

  case class InsertedDB(msg: String = "Se guardo un nuevo registro") extends EventsDBResult
  case class UpdatedDB(msg: String = "Se actualizo un registro") extends EventsDBResult
  case class SavedDB(msg: String = "Se guardo") extends EventsDBResult
  case class DeletedDB(msg: String = "Se borro") extends EventsDBResult
  case class FoundDB(msg: String = "Se encontro") extends EventsDBResult
  case class NotFoundDB(msg: String = "No se encontro") extends EventsDBResult
  case class ErrorDB(msg: String = "Error") extends EventsDBResult

  sealed trait EventsDBWillDo
  case object InsertDB extends EventsDBWillDo
  case object UpdateDB extends EventsDBWillDo
  case object DeleteDB extends EventsDBWillDo
  case class DeleteFieldDB(field: String) extends EventsDBWillDo
  case object FindDB extends EventsDBWillDo
  case object GetAllDB extends EventsDBWillDo

  /**sealed trait EventsDBWillDox {
      val path: String
  }

  case class InsertDBx(path: String) extends EventsDBWillDox
  case class DeleteDBx(path: String) extends EventsDBWillDox
  case class FindDBx(path: String) extends EventsDBWillDox
  case class UpdateDBx(path: String) extends EventsDBWillDox*/

  sealed trait EventsDBWillDox {
    val path: String
  }

  case class InsertDBx(path: String, id: String) extends EventsDBWillDox
  case class DeleteDBx(path: String, id: String) extends EventsDBWillDox
  case class FindDBx(path: String, id: String) extends EventsDBWillDox
  case class UpdateDBx(path: String, oldId: String) extends EventsDBWillDox
  case class DeleteFieldDBx(path: String, field: String) extends EventsDBWillDox

}

object EventAccount{
    sealed trait EventAccountMovement
    object EventCreditMovement extends EventAccountMovement //Start a increase
    object EventDebitMovement extends EventAccountMovement  //Cancel a decrease
}
