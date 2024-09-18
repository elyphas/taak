package spatutorial.shared.typeclass

import shared.mytypes.{ItemRecipe, Recipe}


trait Show[A] {
  def show(a: A): String
}

object Show {

  def show[A](a: A)(implicit sh: Show[A]) = sh.show(a)

  implicit class ShowOps[A](val a: A) extends AnyVal {
    def show(implicit sh: Show[A]) = sh.show(a)
  }

  implicit val itemRecipeShow: Show[ItemRecipe] = new Show[ItemRecipe] {
    def show(c: ItemRecipe): String =
      s"""
         |user = ${c.user}
         |entidad = ${c.entidad}
         |area = ${c.area}
         |fiscal_period = ${c.fiscal_period}
         |folio = ${c.folio}
         |type_document = ${c.type_document}
         |timestamp = ${c.timestamp}
         |key_item = ${c.key_item}
         |description_item = ${c.description_item}
         |measure_unit = ${c.measure_unit}
         |presentation = ${c.presentation}
         |pack_unit_measure = ${c.pack_unit_measure}
         |prescription = ${c.prescription}
         |supplied = ${c.supplied}
         |""".stripMargin
  }

  implicit val recipeShow: Show[Recipe] = new Show[Recipe] {
    def show(c: Recipe): String =
      s"""
         |      user: ${c.user}
         |      entidad: ${c.entidad}
         |      id_area: ${c.id_institution}
         |      area: ${c.institution_description},
         |      fiscal_period: ${c.fiscal_period}
         |      folio: ${c.folio}
         |      date: ${c.date}
         |      type_document: ${c.type_document}
         |      servicio: ${c.servicio.getOrElse("")}
         |      id_patient: ${c.id_patient.getOrElse("")}
         |      patient_name: ${c.patient_name.getOrElse("")}
         |      file_id: ${c.file_id.getOrElse("")}
         |      physician_name: ${c.physician_name}
         |      diagnostic: ${c.diagnostic.getOrElse("")}
         |      recomendation: ${c.recomendation.getOrElse("")}
         |      observation: ${c.observation.getOrElse("")}
      """.stripMargin
  }

}


