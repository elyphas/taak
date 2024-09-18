package taak_client_testear

import cats.effect.SyncIO
import org.scalajs.dom.html
import org.scalajs.dom.raw.MouseEvent

import outwatch_components.inputs._

import cats.implicits._
import manik1.services.{WSCovenant, WSMyCelium}
import monix.execution.{Ack, Cancelable}
import monix.execution.Ack.{Continue, Stop}

import scala.concurrent.duration._
import monix.execution.Scheduler.Implicits.global
import monix.execution.Cancelable
import monix.reactive.{Observable, Observer}
import colibri.ext.monix._
import colibri.ext.monix.ops._
import outwatch.reactive.handlers.monix._
import outwatch._
import outwatch.dsl.{span => sp, _}

import org.scalajs.dom

import scala.concurrent.Future

class FrmClass {

  val testClick = new Observer[String] {
      def onNext(elem: String): Future[Ack] = {
          Future {
              println("Tamos en el obsGetId ******************************")
              println(elem)
              Continue
          }
      }
      def onError(ex: Throwable): Unit = { println(ex.printStackTrace.toString) }
      def onComplete(): Unit = println("O completed Handler")
  }

  val but = button( idAttr := "cmdTestOnClass", "Guardar", cls := "myButton",
      onClick.use("Changos monos y gorilas tamos en el click del button cmdTesting") --> testClick
  )

}
