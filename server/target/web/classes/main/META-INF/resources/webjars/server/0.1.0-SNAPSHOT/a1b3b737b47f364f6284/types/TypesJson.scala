package encoder_json.types

//import java.time.LocalDate
import java.util.Date

sealed trait JsonValue
case class JsonObject(fields: List[ ( String, JsonValue ) ] ) extends JsonValue

case class JsonString(value: String) extends JsonValue

case class JsonDouble(value: Double) extends JsonValue
case class JsonInt(value: Int) extends JsonValue
case class JsonLong(value: Long) extends JsonValue

case class JsonBoolean(value: Boolean) extends JsonValue

//case class JsonLocalDate(value: LocalDate) extends JsonValue

case class JsonDate(value: Date) extends JsonValue

case class JsonArray(items: List[JsonValue]) extends JsonValue
case object JsonNull extends JsonValue

//case class Ver(name: String, age: Int)

/*case class LstItems[A](items: List[A]= List.empty[A]) {
  def updated ( newItem: A, idFunction: A => Boolean) = {
    items.span(i => !idFunction(i)) match {
      case (prefix, matched +: postfix) => LstItems(prefix ++: (newItem +: postfix))
      case _ => LstItems(items :+ newItem)
    }
  }
  def updatedByIdx(idx: Int, newItem: A) = LstItems(items.updated(idx, newItem))
  def remove(item: A) = LstItems(items.filterNot(_ == item))
}*/