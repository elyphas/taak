package modules.pharmacy

import encoder_json.HelpersLabelledGeneric.JsonEncoder
import outwatch_components.{FrmListItems, Grid2}
import shared.{ArticuloCatalog, ArticuloStock}
import shared.mytypes.{ItemRecipe, Recipe}
import encoder_json.types._
import outwatch_components.mytypes.EventsDB._

import scala.language.postfixOps
import gridCatalogs.GridCatalogsFromGrid.gridCatalogArticuloStock
import instance.Instance.instanceRecipe
import repositories.Repository.ftsRepoArticuloStock
import repositories.Repository.{repoGetLstItemsRecipe, repoItemRecipe}

object CreateGrid2 {
  import gridCatalogs.BehaviorOnGrid.behaviorOnGridItemRecipe
  implicit val grid2: Grid2[ItemRecipe] = new Grid2[ItemRecipe]() {}
}

import CreateGrid2.grid2

class FrmLstItems extends FrmListItems[Recipe, ItemRecipe, ArticuloStock, ArticuloCatalog] {

  protected val defaultValuesGrid: Recipe => Map[String, JsonValue] = mainDocument => Map(
    "user" -> JsonString(mainDocument.user),
    "entidad" -> JsonString(mainDocument.entidad),
    "id_institution" -> JsonString(mainDocument.id_institution),
    "fiscal_period" -> JsonInt(mainDocument.fiscal_period),
    "folio" -> JsonString(mainDocument.folio),
    "type_document" -> JsonString(mainDocument.type_document),
    "date" -> JsonString(mainDocument.date2),
    "timestamp" -> JsonLong(0L))

  protected val fieldsToUpdateGrid: ArticuloCatalog => Map[String, JsonValue] = (elem: ArticuloCatalog) => Map(
    "key_item" -> JsonString(elem.id),
    "description_item" -> JsonString(elem.descripcion),
    "measurement_unit" -> JsonString(elem.unidad),
    "presentation" -> JsonInt(elem.presentacion),
    "package_unit_measurement" -> JsonString(elem.unid_med_pres))

  protected val fieldSaveOnEnter: String = "supplied"

  protected def mapOnEnterAndSave: ((ItemRecipe, EventsDBWillDox), Recipe) => (ItemRecipe, EventsDBWillDox) =
                        { case ((i, event), mainDocument) => (i, event) }

  protected def getIdToFindInGrid: ArticuloCatalog => String = cat => cat.id

  protected val fromCatalogToShowOnGrid: ArticuloStock => ArticuloCatalog =
                      (i: ArticuloStock) => ArticuloCatalog(id = i.cve_articulo,
                                                            descripcion = i.descripcion,
                                                            unidad = i.unidad,
                                                            presentacion = i.presentacion.getOrElse(0),
                                                            unid_med_pres = "",
                                                            stock = 0)
}