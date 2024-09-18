package taak_client_testear

import cats.effect.IO
import monix.reactive.subjects.PublishSubject
import monix.reactive.{Observable, Observer}
import org.scalajs.dom.{html, _}
import colibri.ext.monix._
import monix.execution.Ack
import monix.execution.Ack.Continue
import org.scalajs.dom.raw.EventInit
import org.scalatest.Tag
import outwatch.reactive.handlers.monix._
import outwatch.dsl._
import outwatch._

import scala.scalajs.js
import manik1.modules.pharmacy.FrmClass

object ButtonTestingTest extends Tag("ButtonTestingTest")

class TestingOnClassSpec extends JSDomAsyncSpec {
  /*class FrmClass {
    val testClick = new Observer[String] {
        def onNext(elem: String): Future[Ack] = {
            Future {
                println(elem)
                Continue
            }
        }
        def onError(ex: Throwable): Unit = { println(ex.printStackTrace.toString) }
        def onComplete(): Unit = println("O completed Handler")
    }
    val but = button( idAttr := "cmdTestOnClass", "Save", cls := "myButton",
        onClick.use("on the observer a class ----------") --> testClick
    )
  }*/
  it should "be test the save button" taggedAs(ButtonTestingTest) in {
    val frm = new FrmClass
    for {
        _ <- OutWatch.renderInto[IO]("#app", frm.but)
    } yield {
        val element = document.getElementById("cmdTestOnClass")
        sendEvent(element, "click")
        succeed
    }
  }
}