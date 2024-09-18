package gridCatalogs

import colibri.Subject
import outwatch._
import outwatch.dsl.{span => sp, _}
import shared.mytypes.Institution

import scala.collection.immutable.ListMap
//import encoder_json.types.{JsonInt, JsonLong, JsonString, JsonValue}

import shared.{Articulo, ArticuloCatalog, ArticuloStock, ItemStore, Partida}

import outwatch_components.{Catalog, GridCatalogsFromGrid, GridCatalogsFromInputText}

import scala.language.postfixOps
import outwatch_components.TransformData

object GridCatalogsFromGrid {

  /**
   * @param focusCellOnUpdate
   * @param grdCatalog
   * @tparam A -- Catalog (table inside db) where is going to search for
   * @tparam B -- Case class that is going to show the results.   */

    def apply[A, B]()(implicit grdCatalog: GridCatalogsFromGrid[A, B]) : GridCatalogsFromGrid[A, B] = grdCatalog

    implicit val gridCatalogArticuloStock: GridCatalogsFromGrid[ArticuloStock, ArticuloCatalog]
                                                      = new GridCatalogsFromGrid[ArticuloStock, ArticuloCatalog] {

      override val focusCellOnUpdate: Int = 13

      val transformer: TransformData[ArticuloCatalog] = implicitly[TransformData[shared.ArticuloCatalog]]

      override def toCatalog: ArticuloStock => ArticuloCatalog = (i: shared.ArticuloStock) => shared.ArticuloCatalog(
                                            id = i.cve_articulo,
                                            descripcion = i.descripcion,
                                            unidad = i.unidad,
                                            presentacion = i.presentacion.getOrElse(0),
                                            unid_med_pres = i.unid_med_pres.getOrElse(""),
                                            stock = i.stock.getOrElse(0.0))

      def colFormat: ListMap[String, VMod] = ListMap(
                                                "id" -> VMod(width := "0.6%"),
                                                "descripcion" -> VMod(width := "60%"),
                                                "unidad" -> VMod(width := "8%"),
                                                "presentacion" -> VMod(width := "1%"),
                                                "unid_med_pres" -> VMod(width := "2%"),
                                                "stock" -> VMod(width := "3%"),
                                            )
      }
}

object GridCatalogsFromInputText {

  /**
   * @tparam A -- Catalog (table inside db) where is going to search for.
   * @tparam B -- Case class that is going to show the results.
   * @return  */
  def apply[A, B](implicit grdCatalog: GridCatalogsFromInputText[A, B]
                       ): GridCatalogsFromInputText[A, B] = grdCatalog

  implicit val gridCatalogArticulo: GridCatalogsFromInputText[Articulo, Articulo] =
                        new GridCatalogsFromInputText[Articulo, Articulo] {

          val transformer: TransformData[Articulo] = implicitly[TransformData[Articulo]]

          override val hdl: Subject[Articulo] = Subject.behavior[Articulo](Articulo())

          override def toCatalog: Articulo => Articulo = (i: Articulo) => i

          override def colFormat: ListMap[String, VMod] = ListMap(
                                      "id" -> VMod(width := "2%"),
                                      "descripcion" -> VMod(width := "50%"),
                                      "unidad" -> VMod(width := "2%"),
                                      "presentacion" -> VMod(width := "2%"),
                                      "unid_med_pres" -> VMod(width := "2%"),
                                      "partida" -> VMod(width := "2%"),
                                  )
  }

  implicit val gridCatalogPartida: GridCatalogsFromInputText[Partida, Catalog] =
                  new GridCatalogsFromInputText[Partida, Catalog] {

       override val hdl: Subject[Partida] = Subject.behavior[Partida](Partida())

       val transformer: TransformData[Catalog] = implicitly[TransformData[Catalog]]

       def toCatalog: Partida => Catalog = (i: Partida) => Catalog(i.cve_partida, i.descripcion.getOrElse(""))

  }

  implicit val gridCatalogInstitution: GridCatalogsFromInputText[Institution, Catalog] =
    new GridCatalogsFromInputText[Institution, Catalog] {

      override val hdl: Subject[Institution] = Subject.behavior[Institution](Institution())

      val transformer: TransformData[Catalog] = implicitly[TransformData[Catalog]]

      def toCatalog: Institution => Catalog = (i: Institution) => Catalog(i.id_institution, i.description)

    }

  implicit val txtCatalogInstitution: GridCatalogsFromInputText[Institution, Catalog] =
    new GridCatalogsFromInputText[Institution, Catalog] {

      val hdl: Subject[Institution] = Subject.behavior[Institution](Institution())

      val transformer: TransformData[Catalog] = implicitly[TransformData[Catalog]]

      def onError: String => Catalog = error => Catalog(id = "", descripcion = s"Hubo problemas al buscar en la base de datos $error")

      def toCatalog: Institution => Catalog = (i: Institution) => Catalog(i.id_institution, i.description)

      override def colFormat: ListMap[String, VMod] = ListMap(
        "id" -> VMod(width := "2%"),
        "descripcion" -> VMod(width:= "50%")
      )
    }


}