package shared
package mytypes

case class Institution (
      id_institution: String,
      description: String,
)

object Institution {

  def apply(
             id_institution: String = "",
             description: String = "",
    ) = new Institution(
                      id_institution = id_institution,
                      description = description,
              )

}