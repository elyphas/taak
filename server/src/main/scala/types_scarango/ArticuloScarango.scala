package types_scarango

import java.util.Date
import com.outr.arango.{Document, DocumentModel, Id, Index}
import com.outr.arango.query._
import com.outr.arango.Field
import fabric.rw._
import com.outr.arango.DocumentRef

import java.text.SimpleDateFormat
import shared.mytypes
import shared.mytypes.Articulo

case class ArticuloScarango (
        cve_articulo: String = "",
        descripcion: Option[String] = None,
        unidad: String = "",
        presentacion: Option[Int] = None,
        unid_med_pres: Option[String] = None,
        partida: Option[String] = None,
        _id: Id[ArticuloScarango] = ArticuloScarango.id()
) extends Document[ArticuloScarango]

object ArticuloScarango extends DocumentModel[ArticuloScarango] {

    override implicit val rw: RW[ArticuloScarango] = RW.gen

    val cve_articulo: Field[String] = field("cve_articulo")

    val stock: Field[Int] = field("stock")

    override def indexes: List[Index] = Nil

    //override val collectionName: String = "tblartic"
    override val collectionName: String = "tblartic"

    def ref: DocumentRef[ArticuloScarango, ArticuloScarango.type] = DocumentRef(this, Some("a"))

    //override val serialization: Serialization[ArticuloScarango] = Serialization.auto[ArticuloScarango]

  def toScarango(item: Articulo): ArticuloScarango = ArticuloScarango(
    cve_articulo = item.cve_articulo,
    descripcion = item.descripcion,
    unidad = item.unidad,
    presentacion = item.presentacion,
    unid_med_pres = item.unid_med_pres,
    partida = item.partida,
    _id = ArticuloScarango.id(item.cve_articulo)
  )

  def toNative(item: ArticuloScarango): Articulo = Articulo (
    cve_articulo = item.cve_articulo,
    descripcion = item.descripcion,
    unidad = item.unidad,
    presentacion = item.presentacion,
    unid_med_pres = item.unid_med_pres,
    partida = item.partida
  )
}