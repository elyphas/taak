package circe_codec

//import io.circe.Decoder.Result

import java.util.Date
//import io.circe._
//import io.circe.generic.auto._
//import io.circe.syntax._
//import org.http4s.EntityDecoder
//import io.circe.generic.auto._
import io.circe.syntax._
//import org.http4s.circe.jsonOf
import io.circe._
import io.circe.generic.semiauto._
import shared.mytypes.{Inputs, Recipe}

//import java.time.LocalDate

object Circe_codec {

    implicit val dateEncoder: Encoder[Date] = Encoder.instance(a => a.getTime.asJson)
    implicit val dateDecoder: Decoder[Date] = Decoder.instance(a => a.as[Long].map(new Date(_)))

    implicit val recipeEncoder: Encoder[Recipe] = deriveEncoder[Recipe]

    implicit val recipeDecoder: Decoder[Recipe] = new Decoder[Recipe] {
            override def apply(c: HCursor): Decoder.Result[Recipe] =
                  for {
                          user <- c.get[String]("user")
                          entidad <- c.get[String]("entidad")
                          id_institution <- c.get[String]("id_institution")
                          institution_description <- c.get[String]("institution_description")
                          fiscal_period <- c.get[Int]("fiscal_period")
                          folio <- c.get[String]("folio")
                          date <- c.get[Date]("date")
                          date2 <- c.get[String]("date2")
                          year <- c.get[Int]("year")
                          month <- c.get[Int]("month")
                          day <- c.get[Int]("day")
                          type_document <- c.get[String]("type_document")
                          servicio <- c.get[Option[String]]("servicio")
                          id_patient <- c.get[Option[String]]("id_patient")
                          patient_name <- c.get[Option[String]]("patient_name")
                          file_id <- c.get[Option[String]]("file_id")
                          physician_name <- c.get[Option[String]]("physician_name")
                          diagnostic <- c.get[Option[String]]("diagnostic")
                          recomendation <- c.get[Option[String]]("recomendation")
                          observation <- c.get[Option[String]]("observation")
                  } yield Recipe( user, entidad, id_institution, institution_description, fiscal_period, folio, date, date2, year, month, day,
                                  type_document, servicio, id_patient, patient_name, file_id, physician_name,
                                  diagnostic, recomendation, observation )


          }

  implicit val inputsEncoder: Encoder[Inputs] = deriveEncoder[Inputs]

  /**user: String,
  entidad: String,
  id_institution: String,
  description_institution: String,
  fiscal_period: Int,
  folio: String,
  type_document: String,
  programa_presupuestal: String,
  date: Date,
  observation: Option[String]*/

  implicit val inputsDecoder: Decoder[Inputs] = new Decoder[Inputs] {
    override def apply(c: HCursor): Decoder.Result[Inputs] =
      for {
        user <- c.get[String]("user")
        entidad <- c.get[String]("entidad")
        id_institution <- c.get[String]("id_institution")
        description_institution <- c.get[String]("description_institution")
        fiscal_period <- c.get[Int]("fiscal_period")
        folio <- c.get[String]("folio")
        type_document <- c.get[String]("type_document")
        programa_presupuestal <- c.get[String]("programa_presupuestal")
        date <- c.get[Date]("date")
        observation <- c.get[Option[String]]("observation")
      } yield Inputs(user, entidad, id_institution, description_institution, fiscal_period, folio, type_document, programa_presupuestal, date, observation)
  }


}
