package server_routes

import org.http4s._
import org.http4s.dsl.io._
import cats.effect._
import scarango_context.CCreateExcel
import fs2.io.file.{Files, Path}

object FileRoute {

    val path = "/home/elyphas/Documentos/herramientas/2024/"

    val route = HttpRoutes.of[IO] {
                      case req@POST -> Root / "sendFile" =>
                          val createExcel = new CCreateExcel
                          val res = for {
                            _ <- req.body.through ( Files[IO].writeAll ( Path ( path + "1.xlsx" ) ) ).compile.drain
                            _ <- IO(createExcel.clearDataFile(2))
                            y <- IO(Files[IO].readAll(Path(path + "2.xlsx")))
                          } yield Ok(y)
                          res.flatten
                    }

}