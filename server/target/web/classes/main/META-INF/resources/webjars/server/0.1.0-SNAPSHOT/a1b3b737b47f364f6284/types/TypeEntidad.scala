package shared

import cats.data.Validated._
import cats.data.{Validated, ValidatedNel}
//import cats.data.Validated.{Invalid, Valid}
//import cats.data._
import cats.implicits._
//import encoder_json.types._

case class Entidad (
      descripcion: String,
      observacion: Option[String]
)

object Entidad {
  def apply(
             descripcion: String,
             observacion: Option[String] = None
       ): Entidad = new Entidad (
                            descripcion = descripcion,
                            observacion = observacion
                    )

  type V[A] = ValidatedNel[String, A]

  def validateNoneEmptyString(txt: String, msgOnError: String): V[String] = {
        Validated.condNel(txt.nonEmpty, txt, msgOnError)
  }


  def validate(item: Entidad) = (
          validateNoneEmptyString(item.descripcion,"¡El campo *descripción* esta vacío!"),
          validateNoneEmptyString(item.observacion.getOrElse(""),"¡El campo *observación* esta vacío!")
    ).mapN { case (
          descripcion_valid,
          observacion_valid
    ) =>
        new Entidad (
                descripcion = descripcion_valid,
                observacion = Some(observacion_valid)
          )
  }

}
