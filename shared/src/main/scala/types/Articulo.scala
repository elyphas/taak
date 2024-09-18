package shared
package mytypes

case class Articulo (
      cve_articulo: String,
      descripcion: Option[String],
      unidad: String,
      presentacion: Option[Int],
      unid_med_pres: Option[String],
      partida: Option[String]
)

object Articulo {

  def apply(
             cve_articulo: String = "",
             descripcion: Option[String] = None,
             unidad: String = "",
             presentacion: Option[Int] = None,
             unid_med_pres: Option[String] = None,
             partida: Option[String] = None
           ): Articulo = new Articulo (
    cve_articulo = cve_articulo,
    descripcion = descripcion,
    unidad = unidad,
    presentacion = presentacion,
    unid_med_pres = unid_med_pres,
    partida = partida
  )

}