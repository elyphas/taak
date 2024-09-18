package taak_client_testear

import cats.effect.IO
import monix.reactive.subjects.PublishSubject
import monix.reactive.Observable
import org.scalajs.dom.{html, _}
import colibri.ext.monix._
import org.scalajs.dom.raw.EventInit

import org.scalatest.Tag
import outwatch.reactive.handlers.monix._

//import outwatch.definitions._
import outwatch.dsl._
import outwatch._

import scala.scalajs.js

object TestingButtonsTest extends Tag("TestingButtonsTest")

class TestingButtons extends JSDomAsyncSpec {

  "testing TestingButtonsTest" should "Testing TestingButtonsTest" taggedAs(TestingButtonsTest) in {

    val vtree = Handler.createF[IO, MouseEvent].map { handler =>
        val buttonDisabled = handler.map( _ => true ).startWith(Seq(false))
        div(idAttr := "click", onClick --> handler,
          button(idAttr := "btn", disabled <-- buttonDisabled)
        )
    }

    val but = div(idAttr := "click",
      button(idAttr := "btn")
    )

    for {
      vtree <- vtree
      _ <- OutWatch.renderInto[IO]("#root", vtree)
      hasD <- IO(document.getElementById("btn").hasAttribute("disabled"))
        _ <- IO { hasD shouldBe false }

      event <- IO {
                  new Event("click", new EventInit {
                                  bubbles = true
                                  cancelable = false
                            }
                  )
              }
      _  <- IO(document.getElementById("click").dispatchEvent(event))
      d <- IO(document.getElementById("btn").getAttribute("disabled"))
      _ <- IO(d shouldBe "")
    } yield succeed

  }
}