package client

import cats.effect.SyncIO  //IO
import outwatch._

object HelloWoutWatch {

  def main(args: Array[String]): Unit = {
    val program = for {
        //program <- OutWatch.renderInto[IO]("#app", Menu.render)
        program <- Outwatch.renderInto[SyncIO]("#app", Menu.render)
    } yield program
    program.unsafeRunSync()
  }
}