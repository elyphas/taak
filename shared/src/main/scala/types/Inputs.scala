package shared
package mytypes

import cats.data.Validated._
import cats.data.{Validated, ValidatedNel}
import cats.data.Validated.{Invalid, Valid}
//import cats.data._
import cats.implicits._
import encoder_json.types._
import types.ValidateRecord

//import java.time.{LocalDate, LocalTime, ZoneId}
import java.util.Date

case class Inputs (
    user: String,
    entidad: String,
    id_institution: String,
    description_institution: String,
    fiscal_period: Int,
    folio: String,
    type_document: String,
    programa_presupuestal: String,
    date: Date,
    observation: Option[String]
)

object Inputs {

  //private val today: LocalDate = LocalDate.now(ZoneId.of("CST", ZoneId.SHORT_IDS))

  import java.util.Date
  val today: Date = new java.util.Date()

  def apply(
             user: String = "JCESAR",
             entidad: String = "1441",
             id_institution: String = "ALMACEN-CENTRAL",
             description_institution: String = "",
             fiscal_period: Int = today.getYear,
             folio: String = "",
             type_document: String = "ENTRADA",
             programa_presupuestal: String = "",
             //date: Long = today.getTime.toLong,
             date: Date = today,
             observation: Option[String] = None
           ): Inputs = new Inputs(
                                user = user,
                                entidad = entidad,
                                id_institution = id_institution,
                                description_institution = description_institution,
                                fiscal_period = fiscal_period,
                                folio = folio,
                                type_document = type_document,
                                programa_presupuestal = programa_presupuestal,
                                date = date,
                                observation = observation
                        )

  def validate(item: Inputs): ValidatedNel[String, Inputs] = (
    Validated.condNel(item.user.nonEmpty, item.user, "Usuario invalid@"),
    Validated.condNel(item.entidad.nonEmpty, item.entidad, "Entidad invalid@"),
    Validated.condNel(item.id_institution.nonEmpty, item.id_institution, "Area invalid@"),
    Validated.condNel(item.description_institution.nonEmpty, item.id_institution, "Area invalid@"),
    Validated.condNel(item.fiscal_period >= 2021, item.fiscal_period, "Año invalido debe ser mayor o igual que 2021"),
    Validated.condNel(item.folio.nonEmpty, item.folio, "Tipo de documento invalid@"),
    Validated.condNel(item.type_document.nonEmpty, item.type_document, "Tipo de documento invalid@"),
    Validated.condNel(item.programa_presupuestal.nonEmpty, item.programa_presupuestal, "Programa presupuestal invalid@"),
  ).mapN { case (
            user_valid,
            entidad_valid,
            id_institution_valid,
            description_institution_valid,
            fiscal_period_valid,
            folio_valid,
            type_document_valid,
            programa_presupuestal_valid,
    ) => Inputs(
                  user = user_valid,
                  entidad = entidad_valid,
                  id_institution = id_institution_valid,
                  description_institution = description_institution_valid,
                  fiscal_period = fiscal_period_valid,
                  folio = folio_valid,
                  type_document = type_document_valid,
                  programa_presupuestal = programa_presupuestal_valid,
                  date = item.date,
                  observation = item.observation
          )
  }
}

case class ItemInput(
        user: String,
        entidad: String,
        id_institution: String,
        fiscal_period: Int,
        folio: String,
        type_document: String,
        programa_presupuestal: String,
        key_item: String,
        description_item: String,
        measurement_unit: String,
        presentation: Option[Int],
        package_unit_measurement: Option[String],
        batchAndExpiration: Option[BatchAndExpiration],
        amount: Int
)


/*** this is going to use because the batch and expiration fields in the grid is going to be plane but in the
 * case class is a field with a case class: 'BatchAndExpiration'
 * */
case class ItemInput_AuxGrid(
                      user: String,
                      entidad: String,
                      id_institution: String,
                      fiscal_period: Int,
                      folio: String,
                      type_document: String,
                      programa_presupuestal: String,
                      key_item: String,
                      description_item: String,
                      measurement_unit: String,
                      presentation: Option[Int],
                      package_unit_measurement: Option[String],
                      batchAndExpiration: Option[BatchAndExpiration_AuxGrid],
                      amount: Int
                    )

object ItemInput extends ValidateRecord {
  def apply(
             user: String = "JCESAR",
             entidad: String = "1441",
             id_institution: String = "ALMACEN-CENTRAL",
             fiscal_period: Int = 2021,
             folio: String = "",
             type_document: String = "",
             programa_presupuestal: String = "",
             key_item: String = "",
             description_item: String = "",
             measurement_unit: String = "",
             presentation: Option[Int] = None,
             package_unit_measurement: Option[String] = None,
             batchAndExpiration: Option[BatchAndExpiration] = None,
             amount: Int = 0,
     ): ItemInput = new ItemInput(
        user = user,
        entidad = entidad,
        id_institution = id_institution,
        fiscal_period = fiscal_period,
        folio = folio,
        type_document = type_document,
        programa_presupuestal = programa_presupuestal,
        key_item = key_item,
        description_item = description_item,
        measurement_unit = measurement_unit,
        presentation = presentation,
        package_unit_measurement = package_unit_measurement,
        batchAndExpiration = batchAndExpiration,
        amount = amount,
    )

  def validateFiscalPeriod( fiscal_period: JsonValue ): V[Int] = {
    val fp = fiscal_period match {
      case JsonInt(value) => value
      case JsonNull => 0
    }
    Option(fp).toValidNel("Ejercicio Fiscal no valido!")
  }

  def validateTypeDocto(typeDocto: JsonValue): V[String] = {
    val typeDoct = typeDocto match {
      case JsonString(value) => value
      case JsonNull => ""
    }
    Validated.condNel(typeDoct.nonEmpty, typeDoct, "Tipo de documento invalido")
  }

  def validateProgramaPresup(programaPresup: JsonValue): V[String] = {
    val programa = programaPresup match {
      case JsonString(value) => value
      case JsonNull => ""
    }
    Validated.condNel(programa.nonEmpty, programa, "Programa presupuestal invalido")
    //Option(fol).toValidNel("Folio no valido!")  //folio.toIntOption.toValidNel("Folio no valido!")
  }

  def validateFolio( folio: JsonValue): V[String] = {
    val fol = folio match {
      case JsonString(value) => value
      case JsonNull => ""
    }
    Validated.condNel(fol.nonEmpty, fol, "Folio invalido")
    //Option(fol).toValidNel("Folio no valido!")  //folio.toIntOption.toValidNel("Folio no valido!")
  }

  def validateKey_Item(key_item: JsonValue): V[String] = {
    val k = key_item match {
      case JsonString(value) => value
      case JsonNull => ""
    }
    Validated.condNel(k.nonEmpty, k, "Clave invalida")
  }

  def validateDescription_Item(description_item: JsonValue): V[String] = {
    val descrip = description_item match {
      case JsonString(value) => value
      case JsonNull => ""
    }
    Validated.condNel(descrip.nonEmpty, descrip, "Descripción invalida")
  }

  def validateMeasurementUnit(measurement_unit: JsonValue ): V[String] = {
    val meas = measurement_unit match {
      case JsonString(value) => value
      case JsonNull => ""
    }
    Validated.condNel(meas.nonEmpty, meas, "Unidad de Medida, invalida")
  }

  def validatePresentation ( presentation: JsonValue ): V[Int] = {
    val pres = presentation match {
      case JsonInt(value) => value
      case JsonNull => 0
    }
    Option(pres).toValidNel("Presentación, no valida!")
    //pres.toIntOption.toValidNel("Presentación, no valida!")
  }

  def validateString(string: JsonValue): V[String] = {
    val str = string match {
      case JsonString(value) => value
      case JsonNull => ""
    }
    Valid(str)
  }

  def validateLong(long: JsonValue): V[Long] = {
    val l = long match {
      case JsonLong(value) => value
      case JsonNull => 0
    }
    Valid(l)
  }

  /*def validateExpiration(str: JsonValue): V[Long] = {
        val long = str match {
            case JsonString(value) =>
                  val dateFormat = new SimpleDateFormat("dd/mm/yyyy")
                  dateFormat.parse(value).getTime
            case JsonNull => 0
        }
        Valid(long)
  }*/

  def validateAmount(amount: JsonValue): V[Int] = {
    val q = amount match {
      case JsonInt(value) => value
      case JsonNull => 0
    }
    Option(q).toValidNel("Cantidad invalida")
  }

  def validate(item: Map[String, JsonValue]) = (
              validateString(item.getOrElse("user", JsonNull)),
              validateString(item.getOrElse("entidad", JsonNull)),
              validateString(item.getOrElse("id_institution", JsonNull)),
              validateFiscalPeriod(item.getOrElse("fiscal_period", JsonNull)),
              validateString(item.getOrElse("folio", JsonNull)),
              validateString(item.getOrElse("type_document", JsonNull)),
              validateString(item.getOrElse("programa_presupuestal", JsonNull)),
              validateString(item.getOrElse("key_item", JsonNull)),
              validateString(item.getOrElse("description_item", JsonNull)),
              validateString(item.getOrElse("measurement_unit", JsonNull)),
              validatePresentation(item.getOrElse("presentation", JsonNull)),
              //validateString(item.getOrElse("package_unit_measurement", JsonNull)),
              validateString(item.getOrElse("batch", JsonNull)),
              validateDate(item.getOrElse("expiration", JsonNull), "Fecha de expiración no valida !"),
              validateAmount(item.getOrElse("amount", JsonNull))
    ).mapN { case (
        user_valid,
        entidad_valid,
        id_area_valid,
        fiscal_period_valid,
        folio_valid,
        typeDocto_valid,
        programaPresup_valid,
        key_item_valid,
        description_item_valid,
        measurement_unit_valid,
        presentation_valid,
        //package_unit_measurement_valid,
        batch_valid,
        expiration_valid,
        amount_valid
    ) => {

        val batchAndExpiration = if (batch_valid.nonEmpty)
                                Some(BatchAndExpiration(batch_valid, expiration_valid.getTime))
                            else
                                None

    val presentation: Option[Int] = processPresentation(item.getOrElse("presentation", JsonNull))

    val packUnitMeasure: Option[String] = processPackUnitMeasure ( item.getOrElse ( "package_unit_measurement", JsonNull ), presentation )

        ItemInput (
              user = user_valid,
              entidad = entidad_valid,
              id_institution = id_area_valid,
              fiscal_period = fiscal_period_valid,
              folio = folio_valid,
              type_document = typeDocto_valid,
              programa_presupuestal = programaPresup_valid,
              key_item = key_item_valid,
              description_item = description_item_valid,
              measurement_unit = measurement_unit_valid,
              presentation = if(presentation_valid == 0) None else Some(presentation_valid),
              package_unit_measurement = packUnitMeasure, //if(package_unit_measurement_valid == "") None else Some(package_unit_measurement_valid),
              batchAndExpiration = batchAndExpiration,
              amount = amount_valid
        )
    }
  }

  def isValid(record:  Option[Map[String, JsonValue]]): Boolean = validate(record.getOrElse(Map.empty[String, JsonValue])).isValid

  def getValidItem(record: Option[Map[String, JsonValue]]): Either[String, ItemInput] /**: Either[String, ItemRecipe]*/ = {

    validate(record.getOrElse(Map.empty[String, JsonValue]))
    match {
      case Valid(a) => Right(a)
      case Invalid(e) => Left(e.mkString_(""))
    }
  }
}

