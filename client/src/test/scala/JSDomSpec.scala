package taak_client_testear

import cats.effect.{ContextShift, IO}
import org.scalatest._
import org.scalatest.matchers.should.Matchers
import org.scalatest.flatspec.{AnyFlatSpec, AnyFlatSpecLike}
import org.scalajs.dom._
import monix.execution.ExecutionModel.SynchronousExecution
import monix.execution.schedulers.TrampolineScheduler
import monix.execution.{Cancelable, Scheduler}
import monix.execution.schedulers.TestScheduler

abstract class JSDomSpec extends AnyFlatSpec with Matchers with BeforeAndAfterEach {

  implicit val scheduler = TrampolineScheduler(Scheduler.global, SynchronousExecution)
  implicit val cs: ContextShift[IO] = IO.contextShift(scheduler)

  val sc = TestScheduler()

  override def beforeEach(): Unit = {

    document.body.innerHTML = ""

    // prepare body with <div id="app"></div>
    val root = document.createElement("div")
    root.id = "root"
    document.body.appendChild(root)
    ()
  }

}
