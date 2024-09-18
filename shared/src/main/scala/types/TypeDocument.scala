package shared
package mytypes

case class TypeDocument (
      type_document: String
)

object TypeDocument {

    def apply (
              type_document: String = "CONSULTA"
        ): TypeDocument = new TypeDocument (
                          type_document = type_document
                    )

}