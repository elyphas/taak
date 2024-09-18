package spatutorial.shared.typeclass

import encoder_json.types.JsonValue

trait ActionsOnMainDocument[A] {
      def isValid(record:  Option[Map[String, JsonValue]]): Boolean
      def getValidItem(record: Option[Map[String, JsonValue]]): Either[String, A]
}