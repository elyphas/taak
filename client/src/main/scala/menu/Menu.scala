package client

import modules.{inputs, pharmacy, work_with_files}

import scala.collection.immutable.ListMap
import colibri._
import modules.catalogs.institution
import modules.pharmacy.FrmDocument
import outwatch.{VMod, VNode}
import outwatch.dsl._
import repositories.Repository.ftsRepoInstitutions
import repositories.Repository.{repoGetAllTypeDocs, repoGetAllUsers, repoItemRecipe, repoRecipe}

import java.time.LocalDate

//import java.time.{LocalDate, ZoneId}
//import java.util.TimeZone

object Menu {

  println(LocalDate.now())

  //println("Antes de obtener el zone id")
  //private val getZoneId: ZoneId = ZoneId.of("CST", ZoneId.SHORT_IDS)
  //private val getZoneId: ZoneId = ZoneId.of("America/Mexico_City")

  //println("Despues de obtener el zone id")

  //val today: LocalDate = LocalDate.now(getZoneId)

  //println("despues de querer ver el localdate")

  //println(s"sLa fecha actual desde el menu es: $today")

  //println(LocalDate.now(ZoneId.of("CST", ZoneId.SHORT_IDS)))

  //println(TimeZone.getDefault.getID)

  //TimeZone.getAvailableIDs.foreach(println)
  //println("*" * 50)
  //println("Ver")
  //println(ZoneId.systemDefault())

  //println("Changos")

  //println(LocalDate.now())
  //println(LocalDate.now(ZoneId.of("Mexico/BajaSur") ))
  //println("Ahora no imprime nada")


  //println(ver.getID)
  //println("Changos lo puedo ver y despues da error")

  //println("orales" + "*" * 20)

  /**ZoneId.getAvailableZoneIds.forEach { v =>
    println("-" * 50)
    println(v)
    println("-" * 50)
  }*/

  //println("orales" + "*" * 20)

  //val vere = LocalDate.now(ZoneId.of("America/New_York"))

  //println(vere)

  //ZoneId.getAvailableZoneIds.forEach(println)

  //println("changos " + ZoneId.of("America/New_York"))

  sealed trait Page
  case object HomeMenu                  extends Page
  case object LogginMenu                extends Page
  case object DocumentPharmacyMenu      extends Page
  case object DocumentRequisitionMenu   extends Page
  case object DocumentInputsMenu        extends Page
  case object DocumentEntityMenu        extends Page
  case object CatalogMenu               extends Page
  case object ArticuloMenu              extends Page
  case object StocksMenu                extends Page
  case object UserMenu                  extends Page
  case object TypeDoctoMenu             extends Page
  case object WorkWithFilesMenu             extends Page

  private val startMenu = DocumentPharmacyMenu

  private val hashRouter = Subject.behavior[Page](startMenu)

  private def renderRouter: Observable[Observable[VNode]] = hashRouter map {

    case HomeMenu =>
      colibri.Observable(div(
        div(),
        h1("Bienvenido"),
      ))

    /**
     * case UserMenu =>
     *
     * val unpickleDoc = (u: ByteBuffer) => Unpickle[Either[String, (User, EventsDBResult)]].fromBytes(u)
     * val pickleDoc = (p: User, event: EventsDBWillDo) => Pickle.intoBytes[(User, EventsDBWillDo)](p, event)
     *
     * implicit val repoDocument = new RepositoryDocument[User]("users", pickleDoc, unpickleDoc)
     *
     * val frm = new manik1.modules.user.FrmUser
     * colibri.Observable(frm.render)
     *
     * case TypeDoctoMenu =>
     *
     * val unpickleDoc = (u: ByteBuffer) => Unpickle[Either[String, (TypeDocument, EventsDBResult)]].fromBytes(u)
     * val pickleDoc = (p: TypeDocument, event: EventsDBWillDo) => Pickle.intoBytes[(TypeDocument, EventsDBWillDo)](p, event)
     *
     * implicit val repoDocument = new RepositoryDocument[TypeDocument]("type_documents", pickleDoc, unpickleDoc)
     *
     * val frm = new manik1.modules.type_document.FrmTypeDocument
     * colibri.Observable(frm.render)
     */

    case DocumentPharmacyMenu =>
      val frm = new pharmacy.FrmDocument
      colibri.Observable(frm.render)
    /**
    case DocumentEntityMenu =>
      val frm = new institution.FrmDocument
      colibri.Observable(frm.render)

    case WorkWithFilesMenu =>
      val frm = new work_with_files.FrmDocument
      colibri.Observable(frm.render)*/

    /**
     * case DocumentRequisitionMenu =>
     *
     * val pickleLstItems = (p: Requisition) => Pickle.intoBytes[Requisition](p)
     * val unpickleLstItems = (u: ByteBuffer) => Unpickle[Either[String, (List[ItemRequisition], EventsDBResult)]].fromBytes(u)
     *
     * implicit val repo = new  RepositoryLstItemsDocument[Requisition, ItemRequisition]("getDetailsRequisition", pickleLstItems, unpickleLstItems)
     *
     * val unpickleDocument = (u: ByteBuffer) => Unpickle[Either[String, (Requisition, EventsDBResult)]].fromBytes(u)
     * val pickleDocument = (p: Requisition, event: EventsDBWillDo) => Pickle.intoBytes[(Requisition, EventsDBWillDo)](p, event)
     *
     * implicit val repoDocument = new RepositoryDocument[Requisition]("saveRequisition", pickleDocument, unpickleDocument)
     *
     * val unpickleItemDocument = (u: ByteBuffer) => Unpickle[Either[String, (ItemRequisition, EventsDBResult)]].fromBytes(u)
     * val pickleItemDocument = (p: ItemRequisition, event: EventsDBWillDo) => Pickle.intoBytes[(ItemRequisition, EventsDBWillDo)](p, event)
     *
     * implicit val repoItem = new RepositoryItemDocument[ItemRequisition]("saveItemRequisition", pickleItemDocument, unpickleItemDocument)
     *
     * val frm = new requisitions.FrmDocument
     *
     * colibri.Observable(frm.render)
     **/

      case DocumentInputsMenu =>

            /**val pickleLstItems = (p: Inputs) => Pickle.intoBytes[Inputs](p)
            val unpickleLstItems = (u: ByteBuffer) => Unpickle[Either[String, (List[ItemInput], EventsDBResult)]].fromBytes(u)

            implicit val repo = new  RepositoryLstItemsDocument[Inputs, ItemInput]("getDetailsInput", pickleLstItems, unpickleLstItems)

            val unpickleDocument = (u: ByteBuffer) => Unpickle[Either[String, (Inputs, EventsDBResult)]].fromBytes(u)
            val pickleDocument = (p: Inputs, event: EventsDBWillDo) => Pickle.intoBytes[(Inputs, EventsDBWillDo)](p, event)

            implicit val repoDocument = new RepositoryDocument[Inputs]("saveInputs", pickleDocument, unpickleDocument)

            val unpickleItemDocument = (u: ByteBuffer) => Unpickle[Either[String, (ItemInput, EventsDBResult)]].fromBytes(u)
            val pickleItemDocument = (p: ItemInput, event: EventsDBWillDo) => Pickle.intoBytes[(ItemInput, EventsDBWillDo)](p, event)

            implicit val repoItem = new RepositoryItemDocument[ItemInput]("eventsOnItemInput", pickleItemDocument, unpickleItemDocument)*/

            val frm = new inputs.FrmDocument()
            colibri.Observable(frm.render)
     /**
     * case ArticuloMenu =>
     * implicit val articulosRepository = new repositories.CArticulosRepository
     * val frm = new manik1.modules.FrmArticulo
     * frm.render
     * case StocksMenu =>
     * implicit val stocksRepository = new repositories.CStocksRepository
     * val frm = new manik1.modules.FrmStocks
     * frm.render
     */

  }

  private def click(p: Page): VMod = onClick.as(p) --> hashRouter

  private def menuItem(txt: String, p: Page): VNode = div(li(txt, idAttr := "menuItem" + txt, key := "menuItem" + txt, cls := "itemMenuDropDown", click(p)))

  private def subMenu(txt: String, items: Map[String, Page]): VNode =
    li(txt, idAttr := "subMenu" + txt, key := "subMenu" + txt, cls := "menuDropDown",
      div(idAttr := "subMenuGroup" + txt, key := "subMenuGroup" + txt, cls := "menuDropDown2", items.map {
        case (k, v) => menuItem(k, v)
      }.toSeq
      )
    )

  private def menuLoggin: VNode = menuItem("Loggin", LogginMenu)

  private def mainMenu: VNode = ul(
    subMenu("Inventarios",
      ListMap(
        "Salidas Farmacia" -> DocumentPharmacyMenu,
        "Entradas" -> DocumentInputsMenu,
        "Requisition" -> DocumentRequisitionMenu,
      )
    ),
    subMenu("Catalogos",
      ListMap(
        "Instituciones" -> DocumentEntityMenu,
        "Articulos" -> ArticuloMenu,
        "Existencias" -> StocksMenu,
        "Users" -> UserMenu,
        "Tipo de Documentos" -> TypeDoctoMenu,
      )
    )
  )

  def render: VNode =
    div(clear.both, idAttr := "MainSite", key := "MainSite", marginTop := "-15px",
      div(clear.both, idAttr := "mainMenu", key := "mainMenu",
        mainMenu,
      ),
      div(clear.both, idAttr := "screenSite", key := "screenSite",
        renderRouter
      )
    )
}
