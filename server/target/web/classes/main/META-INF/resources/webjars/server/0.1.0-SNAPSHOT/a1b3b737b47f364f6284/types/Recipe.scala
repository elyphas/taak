package shared
package mytypes

import cats.data.Validated.{Invalid, Valid}
import cats.implicits._
import encoder_json.types.{JsonNull, JsonValue}
import types.ValidateRecord

import java.util.{Calendar, Date}

case class Recipe (
      user: String,
      entidad: String,
      id_institution: String,
      institution_description: String,
      fiscal_period: Int,
      folio: String,
      date: Date,   /**   Hubo muchos problemas al querer usar java.time.LocalDate por eso usamos java.util.Date*/
      date2: String,
      year: Int,
      month: Int,
      day: Int,
      type_document: String,
      servicio: Option[String],
      id_patient: Option[String],
      patient_name: Option[String],
      file_id: Option[String],
      physician_name: Option[String],
      diagnostic: Option[String],
      recomendation: Option[String],
      observation: Option[String]
)

object Recipe {

  def apply(
             user: String = "",
             entidad: String = "1441",
             id_institution: String = "FARMACIA",
             institution_description: String = "FARMACIA",
             fiscal_period: Int = 0,
             folio: String = "",
             date: Date = new Date(),
             date2: String = "",
             year: Int = 2024,
             month: Int = 9,
             day: Int = 18,
             type_document: String = "CONSULTA",
             servicio: Option[String] = None,
             id_patient: Option[String] = None,
             patient_name: Option[String] = None,
             file_id: Option[String] = None,
             physician_name: Option[String] = None,
             diagnostic: Option[String] = None,
             recomendation: Option[String] = None,
             observation: Option[String] = None
    ) = new Recipe(
                      user = user,
                      entidad = entidad,
                      id_institution = id_institution,
                      institution_description = institution_description,
                      fiscal_period = fiscal_period,
                      folio = folio,
                      date2 = date2,
                      date = date,
                      year = year,
                      month = month,
                      day = day,
                      type_document = type_document,
                      servicio = servicio,
                      id_patient = id_patient,
                      patient_name = patient_name,
                      file_id = file_id,
                      physician_name = physician_name,
                      diagnostic = diagnostic,
                      recomendation = recomendation,
                      observation = observation
              )

}

case class ItemRecipe(
      user: String,
      entidad: String,
      area: String,
      fiscal_period: Int,
      folio: String,
      type_document: Option[String],
      date: String,
      timestamp: Long,
      key_item: String,
      description_item: String,
      measure_unit: String,
      presentation: Option[Int],
      pack_unit_measure: Option[String],
      prescription: Int,
      supplied: Int,
)

object ItemRecipe extends ValidateRecord {

  val today = new java.util.Date()
  val currentYear = today.getYear() + 1900

  def apply(
            user: String = "",
            entidad: String = "1441",
            area: String = "FARMACIA",
            fiscal_period: Int = currentYear,
            folio: String = "",
            type_document: Option[String] = None,
            date: String = "",
            timestamp: Long = 0L,
            key_item: String = "",
            description_item: String = "",
            measure_unit: String = "",
            presentation: Option[Int] = None,
            pack_unit_measure: Option[String] = None,
            prescription: Int = 0,
            supplied: Int = 0,
    ): ItemRecipe = new ItemRecipe(
          user = user,
          entidad = entidad,
          area = area,
          fiscal_period = fiscal_period,
          folio = folio,
          type_document = type_document,
          date = date,
          timestamp = timestamp,
          key_item = key_item,
          description_item = description_item,
          measure_unit = measure_unit,
          presentation = presentation,
          pack_unit_measure = pack_unit_measure,
          prescription = prescription,
          supplied = supplied,
    )

  private def validate(item: Map[String, JsonValue]): V[ItemRecipe] = (
        validateString(item.getOrElse("user", JsonNull), "User invalido"),
        validateString(item.getOrElse("id_institution", JsonNull), "Id de Institución no valido !"),
        validateString(item.getOrElse("description_institution", JsonNull), "Area invalida!"),
        validateInt(item.getOrElse("fiscal_period", JsonNull), "Periodo fiscal invalido!"),
        validateString(item.getOrElse("folio", JsonNull), "Folio invalido!"),
        validateString(item.getOrElse("type_document", JsonNull), "Tipo de Documento Invalido!"),
        validateDate(item.getOrElse("date", JsonNull), "Fecha invalida!"),
        //validateInt(item.getOrElse("timestamp", JsonNull), "El timestamp es invalido!"),
        validateString(item.getOrElse("key_item", JsonNull), "Clave del articulo invalida!"),
        validateString(item.getOrElse("description_item", JsonNull), "Descripción del articulo invalida!"),
        validateString(item.getOrElse("measurement_unit", JsonNull), "Unidad de medida invalida!"),
        //validateOptionIntPositive(item.get("presentation").getOrElse(JsonNull), "Presentación no valida!"),
        validateInt(item.getOrElse("presentation", JsonNull), "Unidad de medida del contenido no valida!"),
        //validateInt(item.get("prescription").getOrElse(JsonNull), "Cantidad de prescripción no valida !"),
        validateInt(item.getOrElse("supplied", JsonNull), "Cantidad suministrada no valida!")
  ).mapN { case (
          user_valid,
          entidad_valid,
          area_valid,
          fiscal_period_valid,
          folio_valid,
          type_document_valid,
          date_valid,
          //timestamp_valid,
          key_item_valid,
          description_item_valid,
          measurement_unit_valid,
          //presentation_valid,
          //package_unit_measurement_valid,
          prescription_valid,
          supplied_valid
    ) =>

          /**val presentation: Option[Int] = item.getOrElse("presentation", JsonNull) match {
            case JsonInt(value) => value.some
            case _ => none
          }*/

          /**val packUnitMeasure: Option[String] = presentation match {
            case value if (value.getOrElse(0) > 0) =>
              item.getOrElse("package_unit_measurement", JsonNull) match {
                case JsonString(value) => value.some
                case _ => none
              }
            case _ => none
          }*/

          val presentation: Option[Int] = processPresentation(item.getOrElse("presentation", JsonNull))

          val packUnitMeasure: Option[String] = processPackUnitMeasure(item.getOrElse("package_unit_measurement", JsonNull), presentation)

          //val pack = Some(fromJson(item.get("package_unit_measurement").getOrElse(JsonNull)))

          val typedocto = if(type_document_valid == "") None else Some(type_document_valid)

        //val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        //val dateFormated = date_valid.format(formatter)

        //val zoneId: ZoneId =   ZoneId.of("CST", ZoneId.SHORT_IDS)

        //val epoch: Long = date_valid.atStartOfDay(zoneId).toInstant.toEpochMilli

        val diaInt = date_valid.getDate

        val diaStr = if (diaInt < 10) "0" + diaInt.toString else diaInt.toString
        val mesInt = date_valid.getMonth
        val mesStr = if (mesInt < 9) "0" + (mesInt + 1).toString else (mesInt + 1).toString

        val dateFormated: String = (date_valid.getYear() + 1900) + "-" + mesStr + "-" + diaStr

        new ItemRecipe (
                    user = user_valid,
                    entidad = entidad_valid,
                    area = area_valid,
                    fiscal_period = fiscal_period_valid,
                    folio = folio_valid,
                    type_document = typedocto,
                    date = dateFormated,
                    timestamp = 0L,
                    key_item = key_item_valid,
                    description_item = description_item_valid,
                    measure_unit = measurement_unit_valid,
                    presentation = presentation,
                    pack_unit_measure = packUnitMeasure,
                    prescription = prescription_valid,
                    supplied = supplied_valid
              )
  }

  def isValid(record:  Option[Map[String, JsonValue]]): Boolean = validate(record.getOrElse(Map.empty[String, JsonValue])).isValid

  def getValidItem(record: Option[Map[String, JsonValue]]): Either[String, ItemRecipe] /**: Either[String, ItemRecipe]*/ = {

    val itemValid: V[ItemRecipe] = validate(record.getOrElse(Map.empty[String, JsonValue]))

      itemValid match {
                  case Valid(a) => Right(a)
                  //case Invalid(e) => Left(e.mkString_(""))
                  case Invalid(e) => Left(e.foldLeft("")( (acc, cur) => s" $acc + \n + $cur ") )
            }
  }

}