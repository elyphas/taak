package shared
package mytypes

import cats.data.Validated._
import cats.data.{Validated, ValidatedNel}
import cats.data.Validated.Valid

import java.util.Calendar
//import cats.data._
import cats.implicits._
import encoder_json.types._

case class Requisition (
            user: String,
            entidad: String,
            id_area: String,
            fiscal_period: Int,
            folio: String,
            type_document: String,
            date: Long,
            observation: Option[String]
)

object Requisition {

  def apply(
        user: String = "JCESAR",
        entidad: String = "1441",
        id_area: String = "ALMACEN",
        fiscal_period: Int,
        folio: String = "",
        type_document: String = "ALMACEN CENTRAL",
        date: Long,    //= today.getTime.toLong,
        observation: Option[String] = None
 ): Requisition = new Requisition(
        user = user,
        entidad = entidad,
        id_area = id_area,
        fiscal_period = fiscal_period,
        folio = folio,
        type_document = type_document,
        date = date,
        observation = observation
  )

  private type V[A] = ValidatedNel[String, A]

  /**private def validateUser(user: JsonValue): V[String] = {
    val us = user match {
      case JsonString(value) => value
      case _ => ""    //case JsonNull => ""
    }
    Validated.condNel(us.nonEmpty, us, "Usuario invalid@")
  }

  private def validateEntidad(entidad: JsonValue): V[String] = {
        val entid = entidad match {
              case JsonString(value) => value
              case _ => ""    //case JsonNull => ""
        }
        Validated.condNel(entid.nonEmpty, entid, "Entidad invalid@")
  }

  private def validateArea(area: JsonValue): V[String] = {
          val are = area match {
                case JsonString(value) => value
                case _ => ""    //case JsonNull => ""
          }
          Validated.condNel(are.nonEmpty, are, "Area invalid@")
  }*/

  private def validateFiscalPeriod( fiscal_period: JsonValue ): V[Int] = {
        val fp = fiscal_period match {
              case JsonInt(value) => value
              case _ => 0    //case JsonNull => 0
        }
        Option(fp).toValidNel("Ejercicio Fiscal invalid@!")
  }

  private def validateFolio( folio: JsonValue): V[String] = {
        val fol = folio match {
              case JsonString(value) => value
              case _ => ""    //case JsonNull => ""
        }
        Validated.condNel(fol.nonEmpty, fol, "Folio invalid@")
  }

  /**private def validateTypeDocument(type_document: JsonValue): V[String] = {
    val typeDocto = type_document match {
      case JsonString(value) => value
      case _ => ""    //case JsonNull => ""
    }
    Validated.condNel(typeDocto.nonEmpty, typeDocto, "Tipo de documento invalid@")
  }*/

  private def validateDate(date: JsonValue): V[Long] = {
        val dat = date match {
              case JsonLong(value) => value
              case _ => 0    //case JsonNull => 0
        }
        Validated.condNel(dat > 0, dat, "Fecha invalid@")
  }

  private def validateString(str: JsonValue): V[String] = {
    val txt = str match {
      case JsonString(value) => value
      case _ => ""    //case JsonNull => ""
    }
    Validated.condNel(true, txt, "Texto invalid@")
  }

  def validateRequisition(item: Map[String, JsonValue]): V[Requisition] = {
      (
          validateString(item.get("user").getOrElse(JsonNull)),
          validateString(item.get("entidad").getOrElse(JsonNull)),
          validateString(item.get("id_area").getOrElse(JsonNull)),
          validateFiscalPeriod(item.get("fiscal_period").getOrElse(JsonNull)),
          validateFolio(item.get("folio").getOrElse(JsonNull)),
          validateDate(item.get("date").getOrElse(JsonNull)),
          validateString(item.get("observacion").getOrElse(JsonNull))
      ).mapN { case (
                      user_valid,
                      entidad_valid,
                      id_area_valid,
                      fiscal_period_valid,
                      folio_valid,
                      date_valid,
                      observacion_valid
            ) => Requisition (
                      user = user_valid,
                      entidad = entidad_valid,
                      id_area = id_area_valid,
                      fiscal_period = fiscal_period_valid,
                      folio = folio_valid,
                      date = date_valid,
                      observation = Some(observacion_valid)
                )
      }
  }
}

case class ItemRequisition (
      user: String,
      entidad: String,
      id_area: String,
      fiscal_period: Int,
      folio: String,
      date: Long,
      key_item: String,
      description_item: String,
      measurement_unit: String,
      presentation: Option[Int] = None,
      package_unit_measurement: Option[String] = None,
      batchAndExpiration: Option[BatchAndExpiration],
      quantity: Int
)

object ItemRequisition {

  def apply(
          user: String = "",
          entidad: String = "1441",
          id_area: String = "",
          fiscal_period: Int = 2020,
          folio: String = "",
          date: Long = 0,
          key_item: String = "",
          description_item: String = "",
          measurement_unit: String = "",
          presentation: Option[Int] = None,
          package_unit_measurement: Option[String] = None,
          batchAndExpiration: Option[BatchAndExpiration] = None,
          /*batch: String,
          expiration: Long,*/
          quantity: Int = 0
      ): ItemRequisition = new ItemRequisition (
              user = user,
              entidad = entidad,
              id_area = id_area,
              fiscal_period,
              folio,
              date = date,
              key_item,
              description_item,
              measurement_unit,
              presentation,
              package_unit_measurement,
              batchAndExpiration,
              quantity
        )

  private type V[A] = ValidatedNel[String, A]

  private def validateFiscalPeriod( fiscal_period: JsonValue ): V[Int] = {
          val fp = fiscal_period match {
                  case JsonInt(value) => value
                  case _ => 0      //case JsonNull => 0
          }
          Option(fp).toValidNel("Ejercicio Fiscal no valido!")
  }

  private def validateEntidad(entidad: JsonValue): V[String] = {
    val entid = entidad match {
        case JsonString(value) => value
        case _ => ""    //case JsonNull => ""
    }
    Validated.condNel(entid.nonEmpty, entid, "Folio invalido")
  }

  private def validateArea(area: JsonValue): V[String] = {
    val are = area match {
        case JsonString(value) => value
        case _ => ""    //case JsonNull => ""
    }
    Validated.condNel(are.nonEmpty, are, "Folio invalido")
  }

  private def validateFolio( folio: JsonValue): V[String] = {
          val fol = folio match {
                  case JsonString(value) => value
                  case _ => ""    //case JsonNull => ""
          }
          Validated.condNel(fol.nonEmpty, fol, "Folio invalido")
  }

  private def validateDate(date: JsonValue): V[Long] = {
    val d = date match {
      case JsonLong(value) => value
      case _ => 0  //case JsonNull => 0
    }
    Valid(d)
  }

  private def validateKey_Item(key_item: JsonValue): V[String] = {
        val k = key_item match {
              case JsonString(value) => value
              case _ => ""      //case JsonNull => ""
        }
        Validated.condNel(k.nonEmpty, k, "Clave invalida")
  }

  private def validateDescription_Item(description_item: JsonValue): V[String] = {
          val descrip = description_item match {
                case JsonString(value) => value
                case _ => ""    //case JsonNull => ""
          }
          Validated.condNel(descrip.nonEmpty, descrip, "Descripción invalida")
  }

  private def validateMeasurementUnit(measurement_unit: JsonValue ): V[String] = {
          val meas = measurement_unit match {
                case JsonString(value) => value
                case _ => ""    //case JsonNull => ""
          }
          Validated.condNel(meas.nonEmpty, meas, "Unidad de Medida, invalida")
  }

  private def validatePresentation ( presentation: JsonValue ): V[Int] = {
        val pres = presentation match {
              case JsonInt(value) => value
              case _ => 0    //case JsonNull => 0
        }
        Option(pres).toValidNel("Presentación, no valida!")
        //pres.toIntOption.toValidNel("Presentación, no valida!")
  }

  private def validatePackageUnitMeasurement(package_unit_measurement: JsonValue): V[String] = {
        val packUnit = package_unit_measurement match {
              case JsonString(value) => value
              case _ => ""    //case JsonNull => ""
        }
        Valid(packUnit)
  }

  private def validateBatch(batch: JsonValue): V[String] = {
    val b = batch match {
      case JsonString(value) => value
      case _ => ""    //case JsonNull => ""
    }
    Valid(b)
  }

  private def validateExpiration(expiration: JsonValue): V[Long] = {
    val e = expiration match {
        case JsonLong(value) => value
        case _ => 0    //case JsonNull => 0
    }
    Valid(e)
  }

  private def validateQuantity( quantity: JsonValue): V[Int] = {
        val q = quantity match {
              case JsonInt(value) => value
              case _ => 0 //case JsonNull => 0
        }
        Option(q).toValidNel("Cantidad invalida")
  }

  def validate(item: Map[String, JsonValue]): V[ItemRequisition] =
        (
              validateEntidad(item.get("entidad").getOrElse(JsonNull)),
              validateArea(item.get("id_area").getOrElse(JsonNull)),
              validateFiscalPeriod(item.get("fiscal_period").getOrElse(JsonNull)),
              validateFolio(item.get("folio").getOrElse(JsonNull)),
              validateDate(item.get("date").getOrElse(JsonNull)),
              validateKey_Item(item.get("key_item").getOrElse(JsonNull)),
              validateDescription_Item(item.get("description_item").getOrElse(JsonNull)),
              validateMeasurementUnit(item.get("measurement_unit").getOrElse(JsonNull)),
              validatePresentation(item.get("presentation").getOrElse(JsonNull)),
              validatePackageUnitMeasurement(item.get("package_unit_measurement").getOrElse(JsonNull)),
              validateBatch(item.get("batch").getOrElse(JsonNull)),
              validateExpiration(item.get("expiration").getOrElse(JsonNull)),
              validateQuantity(item.get("quantity").getOrElse(JsonNull))
        ).mapN { case (
              entidad_valid,
              id_area_valid,
              fiscal_period_valid,
              folio_valid,
              date_valid,
              key_item_valid,
              description_item_valid,
              measurement_unit_valid,
              presentation_valid,
              package_unit_measurement_valid,
              batch_valid,
              expiration_valid,
              quantity_valid
        ) => {

                val batchAndExpi = if (batch_valid.length > 0 || expiration_valid > 0)
                                        Some(BatchAndExpiration(batch_valid, expiration_valid))
                                    else
                                        None

                ItemRequisition (
                    entidad = entidad_valid,
                    id_area = id_area_valid,
                    fiscal_period = fiscal_period_valid,
                    folio = folio_valid,
                    date = date_valid,
                    key_item = key_item_valid,
                    description_item = description_item_valid,
                    measurement_unit = measurement_unit_valid,
                    presentation = if (presentation_valid == 0) None else Some(presentation_valid),
                    package_unit_measurement = if(package_unit_measurement_valid == "") None else Some(package_unit_measurement_valid),
                    batchAndExpiration = batchAndExpi,
                    /*batch = batch_valid,
                    expiration = expiration_valid,*/
                    quantity = quantity_valid
                )
            }
        }

}