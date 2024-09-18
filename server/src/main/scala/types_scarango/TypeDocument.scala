package types_scarango

import com.outr.arango.{Document, DocumentModel, DocumentRef, Field, Id, Index}
import fabric.rw._
import shared.mytypes.TypeDocument

case class TypeDocumentScarango (
                  type_document: String,
                  _id: Id[TypeDocumentScarango ] = TypeDocumentScarango.id()
  ) extends Document[ TypeDocumentScarango ]


object TypeDocumentScarango extends DocumentModel[TypeDocumentScarango] {

    override implicit val rw: RW[TypeDocumentScarango] = RW.gen

    override def indexes: List[Index] = Nil
    override val collectionName: String = "tbl_type_documents"
    //override val serialization: Serialization[TypeDocumentScarango] = Serialization.auto[TypeDocumentScarango]
    def createId(item: TypeDocument): String = item.type_document

  def toScarango(item: TypeDocument): TypeDocumentScarango = TypeDocumentScarango (
    type_document = item.type_document
  )

  def toNative(item: TypeDocumentScarango): TypeDocument = TypeDocument(
    type_document = item.type_document
  )

}