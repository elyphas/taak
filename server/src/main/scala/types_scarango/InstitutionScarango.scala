package types_scarango

import com.outr.arango.{Document, DocumentModel, Id, Index}
import com.outr.arango.Field
import com.outr.arango.DocumentRef
import fabric.rw._

import shared.mytypes.Institution

case class InstitutionScarango (
      id_institution: String,
      description: String,
      _id: Id[InstitutionScarango] = InstitutionScarango.id()
) extends Document[InstitutionScarango]

object InstitutionScarango extends DocumentModel[InstitutionScarango] {

    override implicit val rw: RW[InstitutionScarango] = RW.gen

    override def indexes: List[Index] = Nil
    override val collectionName: String = "tblinstitutions"

  def createId(item: Institution): String = item.id_institution

  def toScarango(item: Institution): InstitutionScarango =

              InstitutionScarango (
                      id_institution = item.id_institution,
                      description = item.description,
                      _id = InstitutionScarango.id(createId(item))
                )

  def toNative(item: InstitutionScarango): Institution = Institution (
        id_institution = item.id_institution,
        description = item.description,
  )
}