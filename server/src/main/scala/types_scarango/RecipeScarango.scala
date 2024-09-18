package types_scarango

import java.util.Date
import java.text.SimpleDateFormat
import com.outr.arango.{Document, DocumentModel, Id, Index}
//import com.outr.arango.Field
//import com.outr.arango.DocumentRef
import fabric.rw._
import shared.mytypes.Recipe

//import java.time.{Instant, LocalDate, ZoneId}

/**
 * darkfrog26 --  Matt Hicks -- Autor of scarango and fabric.
 *
 *Your problem is that there's no built-in RW support for LocalDate.
 * You can create your own if you like. However, I recommend changing date to Long
 * and using System.currentTimeInMillis(). LocalDate is significantly heavier than Long.
 *

 Since Java 8 you can call java.time.Instant.toEpochMilli().

 For example the call

 final long currentTimeJava8 = Instant.now().toEpochMilli();

 gives you the same results as

 final long currentTimeJava1 = System.currentTimeMillis();
 */

case class RecipeScarango (
                            user: String,
                            entidad: String,
                            id_institution: String,
                            institution_description: String,
                            fiscal_period: Int,
                            folio: String,
                            date: Long,
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
                            observation: Option[String],
                            _id: Id[RecipeScarango] = RecipeScarango.id()
) extends Document[RecipeScarango]

object RecipeScarango extends DocumentModel[RecipeScarango] with DateAndLong {

  override implicit val rw: RW[RecipeScarango] = RW.gen

  override def indexes: List[Index] = Nil
  override val collectionName: String = "tblrecipe"
  //override val serialization: Serialization[RecipeScarango] = Serialization.auto[RecipeScarango]

  def createId(item: Recipe): String = {
        val dateFormat =  (new SimpleDateFormat("dd-MM-yyyy")).format(item.date)
        item.user + "-" + item.entidad + "-" + item.id_institution + "-" + dateFormat + "-" + item.type_document + "-" + item.fiscal_period + "-" + item.folio + "-" + item.user
  }

  def toScarango(item: Recipe): RecipeScarango = RecipeScarango (
                      user = item.user,
                      entidad = item.entidad,
                      id_institution = item.id_institution,
                      institution_description = item.institution_description,
                      fiscal_period = item.fiscal_period,
                      folio = item.folio,
                      date = item.date.getTime,
                      date2 = dateFormated(item.date),
                      year = item.year,
                      month = item.month,
                      day = item.day,
                      type_document = item.type_document,
                      servicio = item.servicio,
                      id_patient = item.id_patient,
                      patient_name = item.patient_name,
                      file_id = item.file_id,
                      physician_name = item.physician_name,
                      diagnostic = item.diagnostic,
                      recomendation = item.recomendation,
                      observation = item.observation,
                      _id = RecipeScarango.id(createId(item))
                )

  def toNative(item: RecipeScarango): Recipe =
              Recipe(
                  user = item.user,
                  entidad = item.entidad,
                  id_institution = item.id_institution,
                  institution_description = item.institution_description,
                  fiscal_period = item.fiscal_period,
                  folio = item.folio,
                  date = new Date(item.date),
                  date2 = item.date2,
                  year = item.year,
                  month = item.month,
                  day = item.day,
                  type_document = item.type_document,
                  servicio = item.servicio,
                  id_patient = item.id_patient,
                  patient_name = item.patient_name,
                  file_id = item.file_id,
                  physician_name = item.physician_name,
                  diagnostic = item.diagnostic,
                  recomendation = item.recomendation,
                  observation = item.observation,
              )

}