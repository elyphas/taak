package shared

import cats.data.Validated._
import cats.data.{Validated, ValidatedNel}
//import cats.data.Validated.{Invalid, Valid}
//import cats.data._
import cats.implicits._
//import encoder_json.types._

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
                            descripcion  = descripcion,
                            unidad = unidad,
                            presentacion = presentacion,
                            unid_med_pres = unid_med_pres,
                            partida = partida
                    )

  type V[A] = ValidatedNel[String, A]

  def validateDescripcion(txt: Option[String]): V[Option[String]] = {

    def condition(txt: Option[String]) = {
      if (txt === None || txt.getOrElse("") === "") false
      else true
    }

    Validated.condNel(condition(txt), txt, "El campo descripción 'No debe debe estar vacío'")
  }

  def validateNoneEmptyString(txt: String, msgOnError: String): V[String] = {
    Validated.condNel(txt.nonEmpty, txt, msgOnError)
  }

  def validate(item: Articulo) = (
        Validated.condNel(item.cve_articulo.nonEmpty, item.cve_articulo, "¡Campo 'Clave' Invalido!" ),
        validateDescripcion(item.descripcion),
        Validated.condNel(item.unidad.nonEmpty, item.unidad, "¡Campo 'Unidad' Invalido"),
        Validated.condNel(item.partida.nonEmpty, item.partida, "¡Campo Partida' Invalido !" )
    ).mapN { case (
          clave_valid,
          descripcion_valid,
          unidad_valid,
          partida_valid
    ) =>
        new Articulo(
              cve_articulo = clave_valid,
              descripcion = descripcion_valid,
              unidad = unidad_valid,
              presentacion = item.presentacion,
              unid_med_pres =	item.unid_med_pres,
              partida = partida_valid
        )
  }

}
