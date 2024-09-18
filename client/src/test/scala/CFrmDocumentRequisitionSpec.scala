package taak_client_testear

import boopickle.Default._

import java.util.Date
import outwatch._
import cats.effect.{IO, SyncIO}

import monix.reactive.{Observable, Observer}
import manik1.manik1.shared.EventsDB.{EventsDBResult, EventsDBWillDo}
import manik1.modules.requisitions
import manik1.repositories.{RepositoryDocument, RepositoryItemDocument, RepositoryLstItemsDocument}

import org.scalajs.dom.{CustomEvent, Element, Event, document, html}

import monix.execution.{Ack, Cancelable}
import monix.execution.Ack.{Continue, Stop}

import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpecLike
import matchers.should._
import spatutorial.shared.{ItemRequisition, Requisition}
import org.scalajs.dom._
import java.nio.ByteBuffer
import scala.concurrent.Future
import scala.util.{Failure, Success}

import org.scalajs.dom.raw.EventInit

object FrmDocumentRequisitionTest extends Tag("FrmDocumentRequisitionTest")

class CFrmDocumentRequisitionSpec extends JSDomAsyncSpec /*with AnyFlatSpecLike with Matchers*/ {

  /*def sendEvent(elem: Element, eventType: String) = {
          val event = document.createEvent("Events")
          initEvent(event)(eventType, canBubbleArg = true, cancelableArg = false)
          elem.dispatchEvent(event)
  }
  val click = IO {
        val event = document.createEvent("Events")
        initEvent(event)("click", canBubbleArg = false, cancelableArg = true)
        event
  }*/

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

  "testing FrmDocumentRequisition" should "Testing FrmDocumentRequisition" taggedAs(FrmDocumentRequisitionTest) in {

    val observer = new Observer[String] {
      def onNext(elem: String): Future[Ack] = {
        Future {
          println(s" Changos en el observer: $elem")
          Continue
        }
      }
      def onError(ex: Throwable): Unit = { alert(ex.printStackTrace.toString) }
      def onComplete(): Unit = println("O completed Handler")
    }

    val obsGetId = new Observer[Requisition] {
      def onNext(elem: Requisition): Future[Ack] = {
        Future {
          println("Tamos en el obsGetId ******************************")
          println(elem)
          Continue
        }
      }
      def onError(ex: Throwable): Unit = { alert(ex.printStackTrace.toString) }
      def onComplete(): Unit = println("O completed Handler")
    }

    val obsGetIdCombine = new Observer[(Requisition, Requisition)] {
      def onNext(elem: (Requisition, Requisition)): Future[Ack] = {
        Future {
          println("Tamos en el obsGetIdCombine #################################")
          println(elem)
          Continue
        }
      }
      def onError(ex: Throwable): Unit = { alert(ex.printStackTrace.toString) }
      def onComplete(): Unit = println("O completed Handler")
    }

    frm.hdlUser.subscribe (observer)
    frm.hdlFolio.subscribe(observer)

    /** Only when all Handler have an element then the getId is going to fire!!,
      * Because of parMapN.
      */
    frm.getId.subscribe(obsGetId)

    /** Only when all Handler have an element then the getId is going to fire!!  **/
    frm.hdlMainItem
        .combineLatest(frm.getId)
        .subscribe(obsGetIdCombine)

    val ver = for {
        _ <- OutWatch.renderInto[IO]("#root", frm.render)
        _ <- IO(frm.hdlUser.onNext("GUADALUPE"))
        _ <- IO(frm.hdlFolio.onNext("QUITAR"))
        event <- IO {
              new Event("click", new EventInit {
                bubbles = true
                cancelable = false
              })
        }
        buttonSave <- IO(document.getElementById("cmdTesting").dispatchEvent(event))
    } yield succeed

    ver.unsafeToFuture().transformWith {
            case Success(value) => Future.successful {
                println( s"El resultado fue exitoso !!!!!!!!!! $value")
                assert(true)
            }
            case Failure(error) => Future.successful {
                val err = error.getMessage + "--" + error.getLocalizedMessage
                println(s"Hubo errores ******** $err")
                assert(false)
            }
    }

  }

}