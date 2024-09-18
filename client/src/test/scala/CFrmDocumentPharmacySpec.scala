package taak_client_testear

import boopickle.Default._

import java.util.Date
import outwatch._
import cats.effect.{IO, SyncIO}
import manik1.manik1.shared.EventsDB.{EventsDBResult, EventsDBWillDo}
import manik1.modules.pharmacy
import manik1.repositories.{RepositoryDocument, RepositoryItemDocument, RepositoryLstItemsDocument}
import spatutorial.shared.{Father, ItemRecipe, Recipe}
import org.scalajs.dom.{CustomEvent, Element, Event, document, html}
import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpecLike
import matchers.should._
import spatutorial.shared.{ItemRecipe, Recipe}

import java.nio.ByteBuffer

//import manik1.modules.pharmacy.FrmDocument
//import manik1.modules.pharmacy.ListItemsRepositoryRecipe    //ListItemsRepository
//import teotl.typeclass.show._

object FrmDocumentPharmacyTest extends Tag("FrmDocumentPharmacyTest")

class CFrmDocumentPharmacySpec extends JSDomSpec with AnyFlatSpecLike with Matchers {

  /*def sendEvent(elem: Element, eventType: String) = {
          val event = document.createEvent("Events")
          initEvent(event)(eventType, canBubbleArg = true, cancelableArg = false)
          elem.dispatchEvent(event)
  }*/

  /*val click = IO {
        val event = document.createEvent("Events")
        initEvent(event)("click", canBubbleArg = false, cancelableArg = true) //initEvent(event)("input", canBubbleArg = false, cancelableArg = true)
        event
  }*/

  "testing handler" should "Testing FrmDocumentPharmacy" taggedAs(FrmDocumentPharmacyTest) in {

    println ( "Changos ta chingon tamos testeando y es más facil      !!!!!!!!!!!!!!!!!!!!!!!! " )

    val pickleLstItems = (p: Recipe) => Pickle.intoBytes[Recipe](p)
    val unpickleLstItems = (u: ByteBuffer) => Unpickle[Either[String, (List[ItemRecipe], EventsDBResult)]].fromBytes(u)

    implicit val repo = new  RepositoryLstItemsDocument[Recipe, ItemRecipe]("getDetailsRecipe", pickleLstItems, unpickleLstItems)

    val unpickleDocument = (u: ByteBuffer) => Unpickle[Either[String, (Recipe, EventsDBResult)]].fromBytes(u)
    val pickleDocument = (p: Recipe, event: EventsDBWillDo) => Pickle.intoBytes[(Recipe, EventsDBWillDo)](p, event)

    implicit val repoDocument = new RepositoryDocument[Recipe]("saveRecipe", pickleDocument, unpickleDocument)

    val unpickleItemDocument = (u: ByteBuffer) => Unpickle[Either[String, (ItemRecipe, EventsDBResult)]].fromBytes(u)
    val pickleItemDocument = (p: ItemRecipe, event: EventsDBWillDo) => Pickle.intoBytes[(ItemRecipe, EventsDBWillDo)](p, event)

    implicit val repoItem = new RepositoryItemDocument[ItemRecipe]("saveItemRecipe", pickleItemDocument, unpickleItemDocument)

    val frm = new pharmacy.FrmDocument

    frm.hdlUser.onNext("GUADALUPEDDD")

    frm.getMainItem.foreach(println)

    frm.frmLstItems.getId.foreach { x =>
          println("\n\n")
          println("Tamos en el frmLstItems")
          println("Valor del getId")
          println(x)
    }

    for {
        _ <- OutWatch.renderInto[IO]("#root", frm.render)
    } yield {
        succeed
    }
  }

}