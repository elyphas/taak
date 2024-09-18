package types_scarango

import com.outr.arango.{Document, DocumentModel, Id, Index}
import com.outr.arango.Field
import com.outr.arango.DocumentRef
import fabric.rw._

import java.text.SimpleDateFormat
//import shared.mytypes.ItemOutputs

case class ItemOutputs (
                         fecha: Option[String],
                         cve_articulo: String,
                         descripcion: String,
                         output: Option[Int],
                         presentacion: Option[Int]
                       )

object ItemOutputs {

  implicit val rw: RW[ItemOutputs] = RW.gen

  def apply(
             fecha: Option[String] = None,
             cve_articulo: String = "",
             descripcion: String = "",
             output: Option[Int] = None,
             presentacion: Option[Int] = None
           ): ItemOutputs = new ItemOutputs(
    fecha = fecha,
    cve_articulo = cve_articulo,
    descripcion = descripcion,
    output = output,
    presentacion = presentacion
  )
}

case class ItemOutputsScarango (
       fecha: Option[String],
       cve_articulo: String,
       descripcion: String,
       output: Option[Int],
       presentacion: Option[Int],
      _id: Id[ItemOutputsScarango] = ItemOutputsScarango.id()
  ) extends Document[ItemOutputsScarango]

object ItemOutputsScarango extends DocumentModel[ItemOutputsScarango] {

    override implicit val rw: RW[ItemOutputsScarango] = RW.gen

    override def indexes: List[Index] = Nil
    override val collectionName: String = "tblrecipe_details"

    def ref: DocumentRef[ItemOutputsScarango, ItemOutputsScarango.type] = DocumentRef(this, Some("i"))

    //override val serialization: Serialization[ItemOutputsScarango] = Serialization.auto[ItemOutputsScarango]

}