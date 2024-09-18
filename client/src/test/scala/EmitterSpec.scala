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

object EmitterTest extends Tag("EmitterTest")

class EmitterSpec extends JSDomAsyncSpec {

  it should "be converted to a generic emitter correctly" taggedAs(EmitterTest) in {

    val message = "changos monos y gorilas"

    val vtree = Handler.createF[IO, String].map { handler =>
        div(idAttr := "click", onClick.use(message) --> handler,
          span(idAttr := "child", handler)
        )
    }

    for {
        vtree <- vtree
        _ <- OutWatch.renderInto[IO]("#app", vtree)
    } yield {

      document.getElementById("cmdTesting")

      val event = new Event("click", new EventInit {
            bubbles = true
            cancelable = false
      })

      document.getElementById("click").dispatchEvent(event)

      println(document.getElementById("child").innerHTML)

      document.getElementById("child").innerHTML shouldBe message

      //dispatch another event
      document.getElementById("click").dispatchEvent(event)

      println(document.getElementById("child").innerHTML)

      document.getElementById("child").innerHTML shouldBe message

    }

  }
}