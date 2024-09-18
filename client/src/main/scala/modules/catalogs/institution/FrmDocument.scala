package modules.catalogs.institution

import gridCatalogs.GridCatalogsFromInputText
import outwatch._
import outwatch.dsl.{span => sp, _}
import outwatch_components.inputs._
import outwatch_components.{Catalog, FrmMainItem, GridCatalogsFromInputText}
import shared.mytypes.Institution
import instance.Instance.instanceInstitution
import repositories.Repository.{ftsRepoInstitutions, repoInstitution}
import gridCatalogs.GridCatalogsFromInputText.txtCatalogInstitution
import outwatch_components.mytypes.EventsDB.FindDBx
import outwatch_components.repositories.Repository
import outwatch_components.types.QueryCatalog

class FrmDocument(implicit
                  ftsInstitutionRepo: Repository[QueryCatalog, List[Institution]]
                 ) extends FrmMainItem[Institution] {

  private val gridCatalogInstitution: GridCatalogsFromInputText[Institution, Catalog] = GridCatalogsFromInputText[Institution, Catalog]

  val elementFocusDefault = "txtId"

  protected val titleForm: String = "Instituciones"

  /**private def makeSearch[B](repo: Repository[QueryCatalog, B], tbl: String, fld: String): VMod =
      onInput.value.map { x =>
              (QueryCatalog ( table = tbl, field = fld, search = x ), FindDBx("",""))
      } --> repo.event*/    //ftsRepoInstitutions.event

  private val onFoundInstitution: ((Catalog, Institution)) => Institution = { case (cat, item) => item.copy(id_institution = cat.id, description = cat.descripcion) }

  private val txtId = cmpInput[A]("Id", hdlMainItem,
    mappingFn = (valF, s) => s.copy ( id_institution = valF ),
    getField = s => s.id_institution,
    VMod(width := "35%", textAlign := "center"),
    events = Some(VMod(
        txtFocusOnDomMount,
        onChangingId,
        connectEventsGridCatalog(gridCatalogInstitution)(onFoundInstitution),
        makeSearch(ftsRepoInstitutions, "tblinstitutions", "id_institution")
    ))
  )

  private val txtDescription = cmpInput[A]("Descripcion", hdlMainItem,
    mappingFn = (valF, s) => s.copy ( description = valF ),
    getField = s => s.description,
    VMod(width := "100%"),
    Some(VMod(
      connectEventsGridCatalog(gridCatalogInstitution)(onFoundInstitution
        /**{ case (cat: Catalog, item: Institution) =>
          item.copy(id_institution = cat.id, description = cat.descripcion )
        }*/
      ),
      makeSearch(ftsRepoInstitutions, "tblinstitutions", "description")
      /**onInput.value.map { x =>
        (QueryCatalog ( table = "tblinstitutions", field = "description", search = x ), FindDBx("",""))
      } --> ftsRepoInstitutions.event*/
    )
    ),
  )

  private def connectOtherComponents: VNode = div( idAttr := "connectcomponents",
                    VMod.managedElement { elem =>
                      ftsInstitutionRepo.onFail.unsafeSubscribe(hdlProcessSideEffect)
                    },
                    VMod.managedElement { elem =>
                      ftsInstitutionRepo.onSuccess
                        .map{_.map( i => Catalog(id = i.id_institution, descripcion = i.description))}
                        .unsafeSubscribe(gridCatalogInstitution.hdlCatalog)
                    }
  )

  override val getId: Institution => String = a => a.id_institution

  def render: VNode = div(idAttr := "pageForm",
          div(cls := "inner-shadow", sp (titleForm), processSideEffect),
          connectInsideForm, /**This come from FrmMainItem  */
          connectOtherComponents,
          makeGroupInputs(List(txtId, txtDescription), 40),
          gridCatalogInstitution.render,
          div(menuButtons(repoMainItem))
        )
}