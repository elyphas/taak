package taak_client_testear

import boopickle.Default._
import cats.effect.IO
import monix.reactive.subjects.PublishSubject
import monix.reactive.{Observable, Observer}
import org.scalajs.dom.{html, _}
import colibri.ext.monix._
import manik1.manik1.shared.EventsDB.{EventsDBResult, EventsDBWillDo}
import manik1.modules.requisitions
import manik1.repositories.{RepositoryDocument, RepositoryItemDocument, RepositoryLstItemsDocument}
import monix.execution.Ack
import monix.execution.Ack.Continue
import org.scalajs.dom.raw.EventInit
import org.scalatest.Tag
import outwatch.reactive.handlers.monix._
import spatutorial.shared.{ItemRequisition, Requisition}

import java.nio.ByteBuffer
import scala.concurrent.Future

//import outwatch.definitions._
import outwatch.dsl._
import outwatch._

import scala.scalajs.js

object ButtonSaveTest extends Tag("ButtonSaveTest")

class TestButtonSaveSpec extends JSDomAsyncSpec {

  val pickleLstItems = (p: Requisition) => Pickle.intoBytes[Requisition](p)
  val unpickleLstItems = (u: ByteBuffer) => Unpickle[Either[String, (List[ItemRequisition], EventsDBResult)]].fromBytes(u)

  implicit val repo = new RepositoryLstItemsDocument[Requisition, ItemRequisition]("getDetailsRequisition", pickleLstItems, unpickleLstItems)

  val unpickleDocument = (u: ByteBuffer) => Unpickle[Either[String, (Requisition, EventsDBResult)]].fromBytes(u)
  val pickleDocument = (p: Requisition, event: EventsDBWillDo) => Pickle.intoBytes[(Requisition, EventsDBWillDo)](p, event)

  implicit val repoDocument = new RepositoryDocument[Requisition]("saveRequisition", pickleDocument, unpickleDocument)

  val unpickleItemDocument = (u: ByteBuffer) => Unpickle[Either[String, (ItemRequisition, EventsDBResult)]].fromBytes(u)
  val pickleItemDocument = (p: ItemRequisition, event: EventsDBWillDo) => Pickle.intoBytes[(ItemRequisition, EventsDBWillDo)](p, event)

  implicit val repoItem = new RepositoryItemDocument[ItemRequisition]("saveItemRequisition", pickleItemDocument, unpickleItemDocument)

  val frm = new requisitions.FrmDocument

  it should "be test the save button" taggedAs(ButtonSaveTest) in {

    val testClick = new Observer[String] {
        def onNext(elem: String): Future[Ack] = {
            Future {
                println("Tamos en el obsGetId ******************************")
                println(elem)
                Continue
            }
        }
        def onError(ex: Throwable): Unit = { alert(ex.printStackTrace.toString) }
        def onComplete(): Unit = println("O completed Handler")
    }

    val buton = button( idAttr := "cmdTesting", "Guardar", cls := "myButton",
                    onClick.use("Changos monos y gorilas tamos en el click del button cmdTesting") --> testClick
                )

    for {
        _ <- OutWatch.renderInto[IO]("#app", buton)
    } yield {
      val element = document.getElementById("cmdTesting")
      sendEvent(element, "click")
      succeed
    }

  }
}