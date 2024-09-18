package types_scarango

import com.outr.arango.{Document, DocumentModel, DocumentRef, Field, Id, Index}
import fabric.rw._ //{ReaderWriter, ccRW}

import java.text.SimpleDateFormat
import java.util.{Calendar, Date}

import shared.mytypes.Requisition

/**case class Requisition (
        user: String,
        entidad: String,
        id_area: String,
        fiscal_period: Int,
        folio: String,
        date: Long,
        observation: Option[String]
)*/

object Requisition {

    val calendar = Calendar.getInstance()
    val today = calendar.getTime().getTime.toLong

    def apply (
            user: String = "",
            entidad: String = "1441",
            id_area: String = "FARMACIA",
            fiscal_period: Int = calendar.get(Calendar.YEAR),
            folio: String = "",
            type_document: String = "",
            date: Long = today,
            observation: Option[String] = None
      ): Requisition = new Requisition (
                                user = user,
                                entidad = entidad,
                                id_area = id_area,
                                fiscal_period = fiscal_period,
                                folio = folio,
                                type_document = type_document,
                                date = date,
                                observation = observation
                          )
}

case class RequisitionScarango (
                user: String,
                entidad: String,
                id_area: String,
                fiscal_period: Int,
                folio: String,
                date: Long,
                observation: Option[String],
                _id: Id[RequisitionScarango] = RequisitionScarango.id()
) extends Document[RequisitionScarango]

object RequisitionScarango extends DocumentModel[RequisitionScarango] {

  override implicit val rw: RW[RequisitionScarango] = RW.gen

  override val collectionName: String = "tbl_requisition"

  val fiscal_period: Field[Int] = field("fiscal_period")
  val folio: Field[String] = field("folio")

  override def indexes: List[Index] = Nil

  def ref: DocumentRef[RequisitionScarango, RequisitionScarango.type] = DocumentRef(this, Some("doc"))

  //override val serialization: Serialization[RequisitionScarango] = Serialization.auto[RequisitionScarango]

  def createId(item: Requisition): String = {
    val dateFormat = (new SimpleDateFormat("dd-MM-yyyy")).format(new Date(item.date))
    item.user + "-" + item.entidad + "-" + item.id_area + "-" + dateFormat + "-" + "-" + item.fiscal_period + "-" + item.folio
  }

  def toNative(item: RequisitionScarango): Requisition =
    Requisition(
      user = item.user,
      entidad = item.entidad,
      id_area = item.id_area,
      fiscal_period = item.fiscal_period,
      folio = item.folio,
      date = item.date,
      observation = item.observation
    )

  def toScarango(item: Requisition): RequisitionScarango =
    RequisitionScarango(
      user = item.user,
      entidad = item.entidad,
      id_area = item.id_area,
      fiscal_period = item.fiscal_period,
      folio = item.folio,
      date = item.date,
      observation = item.observation,
      _id = RequisitionScarango.id(createId(item))
    )
}

case class BatchAndExpiration (
                  batch: String,
                  expiration: Long
)

object BatchAndExpiration {
    implicit val rw: RW[BatchAndExpiration] = RW.gen
}

/******   Item types    ****/

case class ItemRequisition (
        user: String,
        entity: String,
        area: String,
        fiscal_period: Int,
        folio: String,
        date: Long,
        to: String,
        from: String,
        key_item: String,
        description_item: String,
        measure_unit: String,
        presentation: Option[Int],
        pack_unit_measure: Option[String],
        //batchAndExpiration: Option[BatchAndExpiration],
        amount: Int
)

object ItemRequisition {
  def apply(
          user: String = "",
          entity: String = "",
          area: String = "",
          fiscal_period: Int = 2020,
          folio: String = "",
          date: Long = 0,
          to: String = "",
          from: String = "",
          key_item: String = "",
          description_item: String = "",
          measure_unit: String = "",
          presentation: Option[Int] = None,
          pack_unit_measure: Option[String] = None,
          //batchAndExpiration: Option[BatchAndExpiration] = None,
          amount: Int = 0
       ): ItemRequisition = new ItemRequisition (
                user = user,
                entity = entity,
                area = area,
                fiscal_period,
                folio,
                date = date,
                to = to,
                from = from,
                key_item,
                description_item,
                measure_unit,
                presentation,
                pack_unit_measure,
                //batchAndExpiration,
                amount
      )

    /**def toStock(item: ItemRequisition): Stock = Stock(
                          entidad = item.entity,
                          area = item.area,
                          key_item = item.key_item,
                          description_item = item.description_item,
                          measure_unit = item.measure_unit,
                          presentation = item.presentation,
                          pack_unit_measure = item.pack_unit_measure,
                          stock = item.amount
                )

    def moveToStock(item: ItemRequisition): Stock = Stock(
                      entidad = item.entity,
                      area = item.area,
                      key_item = item.key_item,
                      description_item = item.description_item,
                      measure_unit = item.measure_unit,
                      presentation = item.presentation,
                      pack_unit_measure = item.pack_unit_measure,
                      stock = item.amount
                )

  def toStockFixedArea(item: ItemRequisition, area: String): Stock = Stock(
          entidad = item.entity,
          area = area,
          key_item = item.key_item,
          description_item = item.description_item,
          measure_unit = item.measure_unit,
          presentation = item.presentation,
          pack_unit_measure = item.pack_unit_measure,
          stock = item.amount
  )*/

    /**def toPackBalance(item: ItemRequisition): PackBalance = PackBalance (
                    //user = item.user,
                    entidad = Some(item.entity),
                    area = Some(item.area),
                    fiscal_period = item.fiscal_period,
                    folio = item.folio,
                    timestamp = (new Date()).getTime().toLong,
                    cve_articulo = item.key_item,
                    descripcion = item.description_item,
                    unidad = item.measure_unit,
                    presentacion = item.presentation,
                    unid_med_pres = item.pack_unit_measure,
                    //partida: Option[String] = None,
                    amount_by_pack = item.presentation.getOrElse(1),
                    balance = 0
            )*/

}

case class ItemRequisitionScarango (
              user: String,
              entity: String,
              area: String,
              fiscal_period: Int,
              folio: String,
              date: Long,
              to: String,
              from: String,
              key_item: String,
              description_item: String,
              measure_unit: String,
              presentation: Option[Int] = None,
              pack_unit_measure: Option[String],
              ///batchAndExpiration: Option[BatchAndExpiration],
              amount: Int,
              _id: Id[ItemRequisitionScarango] = ItemRequisitionScarango.id()
) extends Document[ItemRequisitionScarango]

object ItemRequisitionScarango extends DocumentModel[ItemRequisitionScarango] {

    override implicit val rw: RW[ItemRequisitionScarango] = RW.gen

    override val collectionName: String = "tblrequisition_details"

    val fiscal_period: Field[Int] = field("fiscal_period")

    override def indexes: List[Index] = Nil

    def ref: DocumentRef[ItemRequisitionScarango, ItemRequisitionScarango.type] = DocumentRef(this, Some("doc"))

  def createId(item: ItemRequisition) = {
    //val dateFormat = (new SimpleDateFormat("dd-MM-yyyy")).format(new Date(item.date))
    /*item.user + "-" +*/
    item.entity + "-" +
      item.key_item
    //item.area +
    //"-" + dateFormat + "-" + item.type_document + "-" + item.fiscal_period + "-" + item.folio
  }

    //override val serialization: Serialization[ItemRequisitionScarango] = Serialization.auto[ItemRequisitionScarango]
  def toScarango(item: ItemRequisition): ItemRequisitionScarango =
      ItemRequisitionScarango(
        user = item.user,
        entity = item.entity,
        area = item.area,
        fiscal_period = item.fiscal_period,
        folio = item.folio,
        date = item.date,
        to = item.to,
        from = item.from,
        key_item = item.key_item,
        description_item = item.description_item,
        measure_unit = item.measure_unit,
        presentation = item.presentation,
        pack_unit_measure = item.pack_unit_measure,
        //batchAndExpiration = item.batchAndExpiration,
        amount = item.amount,
        _id = ItemRequisitionScarango.id(createId(item))
      )

  def toNative(item: ItemRequisitionScarango): ItemRequisition =
    ItemRequisition(
      user = item.user,
      entity = item.entity,
      area = item.area,
      fiscal_period = item.fiscal_period,
      folio = item.folio,
      date = item.date,
      to = item.to,
      from = item.from,
      key_item = item.key_item,
      description_item = item.description_item,
      measure_unit = item.measure_unit,
      presentation = item.presentation,
      pack_unit_measure = item.pack_unit_measure,
      //batchAndExpiration = item.batchAndExpiration,
      amount = item.amount
    )
}