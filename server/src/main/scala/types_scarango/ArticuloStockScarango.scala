package types_scarango

import java.util.Date
import com.outr.arango.{Document, DocumentModel, Id, Index}
import com.outr.arango.Field

import com.outr.arango.DocumentRef
import fabric.rw._ //{ReaderWriter, ccRW}

import shared.ArticuloStock

case class ArticuloStockScarango (
        id_institution: String = "",
        description_institution: String = "",
        cve_articulo: String = "",
        descripcion: String = "",
        unidad: String = "",
        presentacion: Option[Int] = None,
        unid_med_pres: Option[String] = None,
        partida: Option[String] = None,
        max: Option[Double] = None,
        min: Option[Double] = None,
        stock: Option[Double] = None,
        _id: Id[ArticuloStockScarango] = ArticuloStockScarango.id()
) extends Document[ArticuloStockScarango]

object ArticuloStockScarango extends DocumentModel[ArticuloStockScarango] {

    override implicit val rw: RW[ArticuloStockScarango] = RW.gen

    val entidad: Field[String] = field("entidad")
    val area: Field[String] = field("area")

    val cve_articulo: Field[String] = field("cve_articulo")

    val stock: Field[Int] = field("stock")

    override def indexes: List[Index] = Nil

    override val collectionName: String = "tbl_stocks"

    def ref: DocumentRef[ArticuloStockScarango, ArticuloStockScarango.type] = DocumentRef(this, Some("a"))

    //override val serialization: Serialization[ArticuloStockScarango] = Serialization.auto[ArticuloStockScarango]
    def toScarango(item: ArticuloStock): ArticuloStockScarango = ArticuloStockScarango(
      id_institution = item.id_institution,
      description_institution = item.description_institution,
      cve_articulo = item.cve_articulo,
      descripcion = item.descripcion,
      unidad = item.unidad,
      presentacion = item.presentacion,
      unid_med_pres = item.unid_med_pres,
      partida = item.partida,
      max = item.max,
      min = item.min,
      stock = item.stock,
      _id = ArticuloStockScarango.id(item.cve_articulo)
    )

  def toNative(item: ArticuloStockScarango): ArticuloStock = ArticuloStock (
    id_institution = item.id_institution,
    description_institution = item.description_institution,
    cve_articulo = item.cve_articulo,
    descripcion = item.descripcion,
    unidad = item.unidad,
    presentacion = item.presentacion,
    unid_med_pres = item.unid_med_pres,
    partida = item.partida,
    max = item.max,
    min = item.min,
    stock = item.stock
  )
}