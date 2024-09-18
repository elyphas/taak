package modules.inputs

import cats.implicits._
import colibri.Observer
import gridCatalogs.GridCatalogsFromInputText
import outwatch._
import outwatch.dsl.{span => sp, _}
import outwatch_components.inputs._
import outwatch_components.repositories.Repository
import outwatch_components.{Catalog, FrmMainItem, GridCatalogsFromInputText}
import org.scalajs.dom.{Blob, URL, document}
import org.scalajs.dom.html
import repositories.Repository.{ftsRepoInstitutions, repoInput}
import shared.mytypes.{Inputs, Institution, TypeDocument, User}
import instance.Instance.instanceInput
import gridCatalogs.GridCatalogsFromInputText.txtCatalogInstitution
import outwatch_components.types.QueryCatalog

class FrmDocument( implicit
                        repoGetAllUsers: Repository[Unit, List[User]],
                        repoGetAllTypeDocs: Repository[Unit, List[TypeDocument]],
                        ftsInstitutionRepo: Repository[QueryCatalog, List[Institution]]
              ) extends FrmMainItem[Inputs] {

  private val gridCatalogInstitution: GridCatalogsFromInputText[Institution, Catalog] = GridCatalogsFromInputText[Institution, Catalog]

  val elementFocusDefault = "txtFolio"

  protected val titleForm: String = "Entradas"

  private val txtEjercicio = cmpInput[A]("Ejercicio", hdlMainItem,
                                      mappingFn = (valF, s) => s.copy ( fiscal_period = strToInt ( valF ) ),
                                      getField = s => s.fiscal_period.toString,
                                      VMod(width := "25%", textAlign := "center"),
                                      None)

  private val txtFecha: VNode = cmpInput[A]("Fecha", hdlMainItem,
                                      mappingFn = ( valF, s ) => s.copy ( date = strToDate ( valF ) ),
                                      getField = s => dateFormated(s.date),
                                      VMod ( width := "40%" ),
                                      inputType = "date"
                                  )

  private val cboTipoDocto = cmpCombo2[A]("Tipo de Documento", hdlMainItem,
                                          hdlValues = repoGetAllTypeDocs.onSuccess.map(_.map(_.type_document)),
                                          mappingFn = (valF, s) => s.copy(type_document = valF),
                                          getField = s => s.type_document,
                                          None,
                                          itemSelected = "ENTRADA")


  private val onFoundInstitution: ((Catalog, A)) => A = { case (cat, item) => item.copy(id_institution = cat.id, description_institution = cat.descripcion) }

  private val txtIdArea: VNode = cmpInput[A]("Id Area", hdlMainItem,
                                          mappingFn = (valF, s) => s.copy(id_institution = valF),
                                          getField = s => s.id_institution,
                                          props = VMod(width := "30%"),
                                          events = Some(VMod(
                                            connectEventsGridCatalog(gridCatalogInstitution)(onFoundInstitution),
                                            makeSearch(ftsRepoInstitutions, "tblinstitutions", "id_institution")
                                          )),
                                          showCero = true)

  private val txtAreaDescription: VNode = cmpInput[A]("Area Descripción", hdlMainItem,
                                          mappingFn = (valF, s) => s.copy(description_institution = valF),
                                          getField = s => s.description_institution,
                                          props = VMod(width := "50%"),
                                          events = None,
                                          showCero = true,
                                          inputType = "textArea")

  private val txtFolio = cmpInput[A]("Folio", hdlMainItem,
                                      mappingFn = (valF, s) => s.copy(folio = valF),
                                      getField = s => s.folio,
                                      props = VMod(width := "20%"),
                                      events = Some(VMod(onChangingId, txtFocusOnDomMount)),
                                      showCero = true)

  private val cboUsuario = cmpCombo2[A]("Usuario", hdlMainItem,
                                      hdlValues = repoGetAllUsers.onSuccess.map(_.map(_.user)),
                                      mappingFn = (valF, s) => s.copy(user = valF),
                                      getField = s => s.user,
                                      None,
                                      itemSelected = "JCESAR")

  //private val frmLstItems = new FrmLstItems

  private def connectOtherComponents: VNode = div (
    loadCatalogs(repoGetAllUsers, "users"),
    loadCatalogs(repoGetAllTypeDocs, "types_documents"),
    VMod.managedElement { elem =>
      ftsInstitutionRepo.onSuccess
        .map{_.map( i => Catalog(id = i.id_institution, descripcion = i.description))}
        .unsafeSubscribe(gridCatalogInstitution.hdlCatalog)
    }
  )

  override val getId: Inputs => String = a => a.folio

  def render: VNode = div(idAttr := "pageForm",
          div(cls := "inner-shadow", sp(titleForm), processSideEffect),
          connectInsideForm, /**This come from FrmMainItem  */
          connectOtherComponents,
          makeGroupInputs(List(txtEjercicio, txtFecha, cboTipoDocto), w = 55),
          makeGroupInputs(List(txtIdArea, txtAreaDescription), w = 40),
          gridCatalogInstitution.render,
          //txtCatalogInstitution.render,
          makeGroupInputs(List(cboUsuario, txtFolio), w = 50),
          menuButtons(repoMainItem),
          //frmLstItems.render
      )
}