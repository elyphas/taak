package shared

//import cats.data.Validated._
//import cats.data.{Validated, ValidatedNel}
//import cats.data.Validated.{Invalid, Valid}
//import cats.data._
//import cats.implicits._
//import encoder_json.types._

case class QueryCatalog(table: String, field: String, search: String)

sealed trait Father {
  	val id: String
  	val descripcion: Option[String]
}

final case class ArticuloCatalog(
        id: String,
        descripcion: String,
        unidad: String,
        presentacion: Int,
        unid_med_pres: String,
        stock: Double
)

case class Inventory (
    area: String,
    place: Option[String],
    key_item: String,
    description_item: Option[String],
    measurement_unit: Option[String],
    presentation: Option[Int],
    package_unit_measurement: Option[String],
    stock: Option[Int],
    minimum: Option[Int],
    maximum: Option[Int]
)

object Inventory {

  def apply (
        pArea: String,
        pPlace: Option[String] = None,
        pKey_item: String,
        pDescription_item: Option[String] = None,
        pMeasurement_unit: Option[String] = None,
        pPresentation: Option[Int] = None,
        pPackage_unit_measurement: Option[String] = None,
        pStock: Option[Int] = None,
        pMinimum: Option[Int] = None,
        pMaximum: Option[Int] = None
    ) = new Inventory (
          area = pArea, 
          place = pPlace,
          key_item = pKey_item,
          description_item = pDescription_item,
          measurement_unit = pMeasurement_unit,
          presentation = pPresentation,
          package_unit_measurement = pPackage_unit_measurement,
          stock = pStock,
          minimum = pMinimum,
          maximum = pMaximum
    )

}

case class Category (
            id: String = "",
			descripcion: Option[String] = None,
			observaciones: Option[String] = None,
			activo: Option[Boolean] = None
	) extends Father

/*case class TypeDocument(
	 id: String,
	 descripcion: Option[String],
	 observation: Option[String]
) extends Father

object TypeDocument {
    def apply = new TypeDocument(id = "", descripcion = None, observation = None)
}*/

//case class Ranking(ranking: Double)

case class ItemStore (
      entidad: Option[String],
      id: String,
      descripcion: Option[String],
      unidad: String,
      presentacion: Option[Int],
      unid_med_pres: Option[String],
      partida: Option[String],
      cabms: Option[String],
      cb: Option[Boolean],
      iva: Option[Double],
      baja: Option[Boolean],
      retension_isr: Option[Double],
      partida_descripcion: Option[String],
      stock: Int = 0
) extends Father

object ItemStore {
    def apply(
               entidad: Option[String] = None,
               id: String = "",
               descripcion: Option[String] = None,
               unidad: String = "",
               presentacion: Option[Int] = None,
               unid_med_pres: Option[String] = None,
               partida: Option[String] = None,
               cabms: Option[String] = None,
               cb: Option[Boolean] = None,
               iva: Option[Double] = None,
               baja: Option[Boolean] = Some(false),
               retension_isr: Option[Double] = None,
               partida_descripcion: Option[String] = None,
               stock: Int = 0
         ): ItemStore = new ItemStore (
                          entidad = entidad,
                          id = id,
                          descripcion = descripcion,
                          unidad = unidad,
                          presentacion = presentacion,
                          unid_med_pres = unid_med_pres,
                          partida = partida,
                          cabms = cabms,
                          cb = cb,
                          iva = iva,
                          baja = baja,
                          retension_isr = retension_isr,
                          partida_descripcion = partida_descripcion,
                          stock = stock
                    )
}


case class ResultsFTS ( id: String, descripcion: Option[String] = None, ranking: Double )

case class LstItems[A](items: List[A]= List.empty[A]) {
	def updated ( newItem: A, idFunction: A => Boolean) = {
		items.span(i => !idFunction(i)) match {
			case (prefix, _ +: postfix) => LstItems(prefix ++: (newItem +: postfix))
			case _ => LstItems(items :+ newItem)
		}
	}
	def updatedByIdx(idx: Int, newItem: A) = LstItems(items.updated(idx, newItem))
	def remove(item: A) = LstItems(items.filterNot(_ == item))
}

final case class Partida (
                           cve_partida: String = "",
                           descripcion: Option[String] = None,
                           observaciones: Option[String] = None,
                           presupuesto: Option[Double] = None,
                           activo: Option[Boolean] = None
                         )