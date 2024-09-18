package modules.pharmacy

import cats.implicits._
import colibri.Observer
import outwatch._
import outwatch.dsl.{span => sp, _}
import outwatch_components.inputs._
import outwatch_components.repositories.Repository
import outwatch_components.FrmMainItem
import shared.mytypes.{Recipe, TypeDocument, User}
import instance.Instance.instanceRecipe
import org.scalajs.dom.{Blob, URL, console, document, html}
import repositories.Repository.{repoRecipe, repoSendFile}

class FrmDocument( implicit
                        repoGetAllUsers: Repository[Unit, List[User]],
                        repoGetAllTypeDocs: Repository[Unit, List[TypeDocument]],
              ) extends FrmMainItem[Recipe] {

  /**helpers.OutwatchTracing.error.unsafeForeach { throwable =>
    console.log(s"Exception while patching an Outwatch compontent: ${throwable.getMessage}")
  }*/

  val elementFocusDefault = "txtFolio"

  protected val titleForm: String = "Recetas"

  private val txtFecha: VNode = cmpInput[A]("Fecha", hdlMainItem,
                                        mappingFn = ( valF, s ) => s.copy(date = strToDate(valF)),
                                        getField = s => (dateFormated(s.date)),
                                        VMod ( width := "15%" ),
                                        inputType = "date"
                                    )

  private val txtEjercicio = cmpInput[A]("Ejercicio", hdlMainItem,
                                      mappingFn = (valF, s) => s.copy ( fiscal_period = strToInt ( valF ) ),
                                      getField = s => s.fiscal_period.toString,
                                      VMod(width := "35%", textAlign := "center"),
                                      None
                                      /**Some(VMod(onChangingId /**updateFolioOnChangeElementsId*/))*/
                                  )

  private val cboTypeRecipe = cmpCombo2[A]("Tipo Receta", hdlMainItem,
                                      hdlValues = repoGetAllTypeDocs.onSuccess.map(_.map(_.type_document)),
                                      mappingFn = (valF, s) => s.copy(type_document = valF),
                                      getField = s => s.type_document,
                                      VMod(width:="75%"),
                                      /**events = Some ( VMod ( updateFolioOnChangeElementsId ) ),*/
                                      itemSelected = "CONCENTRADO"
                                  )

  private val txtFolio = cmpInput[A]("Folio", hdlMainItem,
                                      mappingFn = (valF, s) => s.copy(folio = valF),
                                      getField = s => s.folio,
                                      props = VMod(width := "60%"),
                                      events = Some(VMod(onChangingId, txtFocusOnDomMount)),
                                      showCero = true
                                  )

  private val cboUsuario = cmpCombo2[A]("Usuario", hdlMainItem,
                                      hdlValues = repoGetAllUsers.onSuccess.map(_.map(_.user)),
                                      mappingFn = (valF, s) => s.copy(user = valF),
                                      getField = s => s.user,
                                      None, itemSelected = "JCESAR"
                                  )

  private val txtPhisycian = cmpInput[A]("Medico", hdlMainItem,
                                      mappingFn = (valF, s) => s.copy(physician_name = if(valF.isEmpty) None else Some(valF)),
                                      getField = s => s.physician_name.getOrElse(""),
                                      VMod(width := "70%"),
                                      None,
                                      showCero = true
                                  )

   private val frmLstItems = new FrmLstItems

  private def connectOtherComponents: VNode = div (
        EmitterBuilder.fromSource(hdlMainItem.debounceMillis(800).filter(_.folio.nonEmpty))--> frmLstItems.hdlIdItem,
        loadCatalogs(repoGetAllUsers, "users"),
        loadCatalogs(repoGetAllTypeDocs, "typedocuments"),
   )

  override val getId: Recipe => String = a => a.folio

  def render: VNode = div(idAttr := "pageForm",
          div(cls := "inner-shadow", sp (titleForm), processSideEffect),
          connectInsideForm, /**This come from FrmMainItem  */
          connectOtherComponents,
          makeGroupInputs(List(txtFecha), 50),
          makeGroupInputs(List(txtEjercicio, cboTypeRecipe, cboUsuario, txtFolio), 60),
          makeGroupInputs(List(txtPhisycian), 80),
           div(
            /**
            div(clear.both, display.block,
                cmpInput("Expediente", hdlFileId, VModifier(vdomTxt, width := "50px", textAlign := "justify"), None),
                cmpInput("Id Paciente", hdlIdPatient, VModifier(vdomTxt, width := "90px", textAlign := "justify"), None),
                cmpInput("Nombre paciente", hdlPatientName, VModifier(vdomTxt, width := "250px", textAlign := "justify"), None),
            ),
            div(clear.both, display.block,
                cmpInput("Nombre medico", hdlPhysicianName, VModifier(vdomTxt, width := "250px", textAlign := "justify"), None),
                cmpInput("Diagnostico", hdlDiagnostic, VModifier(vdomTxt, width := "250px", textAlign := "justify"), None),
            ),*/
            menuButtons(repoMainItem),
            frmLstItems.render
          )
        )
}