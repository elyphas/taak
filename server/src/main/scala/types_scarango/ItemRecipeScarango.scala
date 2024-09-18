package types_scarango

import java.util.Date
import java.text.SimpleDateFormat
import com.outr.arango.{Document, DocumentModel, Id, Index}
import com.outr.arango.Field
import com.outr.arango.DocumentRef
import fabric.rw._
import shared.mytypes.ItemRecipe

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

case class ItemRecipeScarango (
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
      _id: Id[ItemRecipeScarango] = ItemRecipeScarango.id()
) extends Document[ItemRecipeScarango]

object ItemRecipeScarango extends DocumentModel[ItemRecipeScarango] {

    override implicit val rw: RW[ItemRecipeScarango] = RW.gen

    val type_document: Field[String] = field("type_document")
    val fiscal_period: Field[Int] = field("fiscal_period")
    val folio: Field[String] = field("folio")
    val date: Field[String] = field("date")
    val timestamp: Field[Long] = field("timestamp")
    val user: Field[String] = field("user")

    override def indexes: List[Index] = Nil

    override val collectionName: String = "tblrecipe_details"

    def ref: DocumentRef[ItemRecipeScarango, ItemRecipeScarango.type] = DocumentRef(this, Some("i"))

    //override val serialization: Serialization[ItemRecipeScarango] = Serialization.auto[ItemRecipeScarango]

  def toNative(item: ItemRecipeScarango): ItemRecipe = ItemRecipe(
    user = item.user,
    entidad = item.entidad,
    area = item.area,
    fiscal_period = item.fiscal_period,
    folio = item.folio,
    type_document = item.type_document,
    date = item.date,
    timestamp = item.timestamp,
    key_item = item.key_item,
    description_item = item.description_item,
    measure_unit = item.measure_unit,
    presentation = item.presentation,
    pack_unit_measure = item.pack_unit_measure,
    prescription = item.prescription,
    supplied = item.supplied
  )

  def createId(item: ItemRecipe): String = {
    //val dateFormat = (new SimpleDateFormat("dd-MM-yyyy")).format(new Date(item.date))
    item.user + "-" + item.entidad + "-" + item.area + "-" + item.fiscal_period + "-" + item.folio + "-" + item.key_item + "-" + item.timestamp
  }

  def toScarango(item: ItemRecipe): ItemRecipeScarango = {

    val dateFormat = (new SimpleDateFormat("dd/MM/yyyy")).format(new Date(item.timestamp))

    ItemRecipeScarango (
      user = item.user,
      entidad = item.entidad,
      area = item.area,
      fiscal_period = item.fiscal_period,
      folio = item.folio,
      type_document = item.type_document,
      date = dateFormat,
      timestamp = item.timestamp,
      key_item = item.key_item,
      description_item = item.description_item,
      measure_unit = item.measure_unit,
      presentation = item.presentation,
      pack_unit_measure = item.pack_unit_measure,
      prescription = item.prescription,
      supplied = item.supplied,
      _id = ItemRecipeScarango.id(createId(item))
    )

  }

}