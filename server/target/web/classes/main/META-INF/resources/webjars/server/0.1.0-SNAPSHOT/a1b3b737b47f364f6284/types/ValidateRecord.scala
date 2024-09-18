package types

import cats.data.{Validated, ValidatedNel}
import cats.implicits.{catsSyntaxOptionId, none}
import encoder_json.types.{JsonDate, JsonInt, JsonNull, JsonString, JsonValue}

//import java.time.{LocalDate, ZoneId}

import java.util.Date

trait ValidateRecord {

  type V[A] = ValidatedNel[String, A]

  protected def validateString(value: JsonValue, msgOnError: String): V[String] = {
    val result = value match {
      case JsonString(value) => value
      case _ => ""
    }
    Validated.condNel(result.nonEmpty, result, msgOnError)
  }

  protected def validateString(value: String, msgOnError: String): V[String] =
          Validated.condNel(value.nonEmpty, value, msgOnError)

  protected def validateOptionString(value: JsonValue, msgOnError: String): V[Option[String]] = {
    val result = value match {
      case JsonString(value) if (value.nonEmpty) => Some(value)
      case _ => None
    }
    Validated.condNel(result.nonEmpty, result, msgOnError)
  }

  protected def validateInt (value: JsonValue, msgOnError: String): V[Int] = {
    val result = value match {
      case JsonInt(value) => value
      case _ => 0
    }
    Validated.condNel(result > 0, result, msgOnError)
  }

  protected def processPresentation(value: JsonValue): Option[Int] = {
    value match {
      case JsonInt(value) => value.some
      case _ => none
    }
  }

  protected def processPackUnitMeasure(value: JsonValue, presentation: Option[Int]): Option[String] = {
    presentation match {
          case valuePres if (valuePres.getOrElse(0) > 0) =>
            value match {
              case JsonString(value) => value.some
              case _ => none
            }
          case _ => none
    }
  }

  /**protected def localDatetoLong(localDate: LocalDate): Long = {
    val zoneId: ZoneId =   ZoneId.of("CST", ZoneId.SHORT_IDS)
    localDate.atStartOfDay(zoneId).toInstant.toEpochMilli
  }*/

  protected def validateOptionIntPositive (value: JsonValue, msgOnError: String): V[Option[Int]] = {
    val result = value match {
      case JsonInt(value) if (value > 0) => Some(value)
      case _ => None
    }
    Validated.condNel(result.isDefined, result, msgOnError)
  }

  /**protected def validateLocalDate(value: JsonValue, msgOnError: String): V[LocalDate] = {
    val result = value match {
      case JsonLocalDate(value) => value
      case JsonNull => LocalDate.now()
    }
    Validated.condNel(true, result, msgOnError)
  }*/

  protected def validateDate(value: JsonValue, msgOnError: String): V[Date] = {
    val result = value match {
      case JsonDate(value) => value
      case JsonNull => new Date()
    }
    Validated.condNel(true, result, msgOnError) //Validated.condNel(meas.nonEmpty, measurement_unit, "Unidad de Medida, invalida")
  }

  /**private def validateEntidad(entidad: JsonValue): V[String] = {
    val entid = entidad match {
      case JsonString(value) => value
      case _ => ""
    }
    Validated.condNel(entid.nonEmpty, entid, "Entidad invalida")
  }*/

}
