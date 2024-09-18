package shared

//import cats.data.Validated._
import cats.data.{Validated, ValidatedNel}
import types.ValidateRecord
//import cats.data.Validated.{Invalid, Valid}
//import cats.data._
import cats.implicits._
//import encoder_json.types._

case class ArticuloStock (
      id_institution: String,
      description_institution: String,
      cve_articulo: String,
      descripcion: String,
      unidad: String,
      presentacion: Option[Int],
      unid_med_pres: Option[String],
      partida: Option[String],
      max: Option[Double],
      min: Option[Double],
      stock: Option[Double]
)

object ArticuloStock extends ValidateRecord {
  def apply(
             id_institution: String = "1441",
             description_institution: String = "",
             cve_articulo: String = "",
             descripcion: String = "",
             unidad: String = "",
             presentacion: Option[Int] = None,
             unid_med_pres: Option[String] = None,
             partida: Option[String] = None,
             max: Option[Double] = None,
             min: Option[Double] = None,
             stock: Option[Double] = None
       ): ArticuloStock = new ArticuloStock (
                            id_institution = id_institution,
                            description_institution = description_institution,
                            cve_articulo = cve_articulo,
                            descripcion = descripcion,
                            unidad = unidad,
                            presentacion = presentacion,
                            unid_med_pres = unid_med_pres,
                            partida = partida,
                            max = max,
                            min = min,
                            stock = stock
                    )

  //type V[A] = ValidatedNel[String, A]

  def validateDescripcion(txt: Option[String]): V[Option[String]] = {

    def condition(txt: Option[String]) = {
      if (txt === None || txt.getOrElse("") === "") false
      else true
    }

    Validated.condNel(condition(txt), txt, "El campo descripción 'No debe debe estar vacío'")
  }

  def validateIVA(iva: Option[Double], partida: String): V[Option[Double]] = {

    def condition(iva: Option[Double], partida: String) = {
      iva match {
        case None => false
        case Some(x) if x == 0 && partida == "25301" => true
        case Some(x) if x == 16 => true
        case _ => false
      }
    }
    val result = condition(iva, partida)
    val msg = if (!result && partida == "25301") "0%" else "16%"

    Validated.condNel(result, iva, s"El articulo no tiene iva de acuerdo \n a la partida debe ser $msg")
  }

  def validateStock(stock: Option[Double]): V[Option[Double]] = {
    def condition(v: Option[Double]): Boolean =
      v match {
        case None => false
        case Some(_) => true
      }

    Validated.condNel(condition(stock), stock, "¡Stock no debe ser vacío!")
  }

  def validateNoneEmptyString(txt: String, msgOnError: String): V[String] = {
          Validated.condNel(txt.nonEmpty, txt, msgOnError)
  }

  def validate(item: ArticuloStock): V[ArticuloStock] = (
            validateString(item.id_institution,"¡El campo *id_institution* esta vacío!"),
            validateString(item.description_institution,"¡El campo *Área* esta vacío!"),
            validateString(item.cve_articulo, "¡Campo 'Clave' Invalido!" ),
            validateString(item.descripcion,"¡El campo *Descripción* esta vacío!"),
            validateString(item.unidad,"¡El campo *Unidad* esta vacío!"),
            validateStock(item.stock)
    ).mapN { case (
          id_institution_valid,
          description_institution_valid,
          clave_valid,
          descripcion_valid,
          unidad_valid,
          stock_valid
    ) =>
          new ArticuloStock(
            id_institution = id_institution_valid,
            description_institution = description_institution_valid,
            cve_articulo = clave_valid,
            descripcion = descripcion_valid,
            unidad = unidad_valid,
            presentacion = item.presentacion,
            unid_med_pres =	item.unid_med_pres,
            partida = item.partida,
            max = item.max,
            min = item.min,
            stock = stock_valid
      )
  }

}
