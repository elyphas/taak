package taak_client_testear

import cats.effect.{ContextShift, IO}
import monix.execution.ExecutionModel.SynchronousExecution
import monix.execution.Scheduler
import monix.execution.schedulers.TrampolineScheduler
import org.scalactic.source.Position
import org.scalajs.dom.raw.EventInit
import org.scalatest.{BeforeAndAfterEach, _}
import org.scalatest.flatspec.{AnyFlatSpec, AsyncFlatSpec}
import org.scalatest.matchers.dsl.ResultOfAtLeastOneOfApplication
import org.scalatest.matchers.should.Matchers
import org.scalajs.dom.{Element, Event, document, window}

import scala.concurrent.Future

trait OutwatchSpec extends Matchers with BeforeAndAfterEach /*with EasySubscribe*/ { self: Suite =>
  implicit val scheduler: TrampolineScheduler = TrampolineScheduler(Scheduler.global, SynchronousExecution)
  implicit val cs: ContextShift[IO] = IO.contextShift(scheduler)

  override def beforeEach(): Unit = {
      document.body.innerHTML = ""
      window.localStorage.clear()
      //prepare body with <div id="app"></div>
      val root = document.createElement("div")
      root.id = "app"
      document.body.appendChild(root)
      ()
  }
}

abstract class JSDomSpec2 extends AnyFlatSpec with OutwatchSpec {
  implicit def executionContext = scheduler
}

abstract class JSDomAsyncSpec extends AsyncFlatSpec with OutwatchSpec {
  override def executionContext = scheduler
  implicit def ioAssertionToFutureAssertion(io:IO[Assertion]): Future[Assertion] = io.unsafeToFuture()

  def sendEvent(elem: Element, eventType: String) = {
          val event = new Event(eventType, new EventInit {
                bubbles = true
                cancelable = false
          })
          elem.dispatchEvent(event)
  }
}