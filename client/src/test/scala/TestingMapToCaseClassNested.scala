package taak_client_testear

import cats.effect.IO
import monix.reactive.subjects.PublishSubject
import monix.reactive.{Observable, Observer}
import org.scalajs.dom.{html, _}
import colibri.ext.monix._
import encoder_json.HelpersLabelledGeneric.JsonEncoder
import encoder_json.types.{JsonNull, JsonObject, JsonValue}
import monix.execution.Ack
import monix.execution.Ack.Continue
import org.scalajs.dom.raw.EventInit
import org.scalatest.Tag
import outwatch.reactive.handlers.monix._
import outwatch.dsl._
import outwatch._

import scala.scalajs.js
import spatutorial.shared.{BatchAndExpiration, ItemRequisition}

object MapToCCNestedTest extends Tag("MapToCCNestedTest")

class TestingMapToCaseClassNested extends JSDomAsyncSpec {
  it should "be test the save button" taggedAs(MapToCCNestedTest) in {

    val today = new java.util.Date

    val item = ItemRequisition (
                    user = "LILIA",
                    entidad = "1442",
                    id_area = "FARMACIA",
                    fiscal_period = 2021,
                    folio = "QUITAR_TEST",
                    key_item = "010.000.1711.00.00",
                    description_item = "ACIDO FOLICO TABLETA CADA TABLETA CONTIENE: ACIDO FOLICO 0.4 MG ENVASE CON 90 TABLETAS.",
                    measurement_unit = "ENV C/90 TAB",
                    presentation = Some(0),
                    package_unit_measurement = None,
                    batchAndExpiration = Some(BatchAndExpiration("LOTE-CHANGOS", today.getTime.toLong)),
                    quantity = 50
              )

    for {
        _ <- OutWatch.renderInto[IO]("#app", div())
    } yield {

        implicit val jsonEncod = implicitly[JsonEncoder[ItemRequisition]]
        val JsonObject(itemEncode) = jsonEncod.encode(item)

        val itemJson: List[List[(String, JsonValue)]] = itemEncode.map { case (k, v) =>
                  v match {
                        case JsonObject(value) => value.toMap.toList
                        case _ => List((k -> v))
                  }
            }

        /****  from this itemEncode: List[String, JsonValue]  to this:   itemJson: List[List[(String, JsonValue)]]  */

        println("")
        println("")
        println("")

        println(itemJson.flatten)

        succeed
    }
  }
}