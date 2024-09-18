package server_routes

import cats.effect.IO
import org.http4s.StaticFile
import org.http4s.HttpRoutes
import org.http4s.Method.GET
import org.http4s.dsl.io._


object StaticFilesRoutes {

  implicit val ec: scala.concurrent.ExecutionContext = scala.concurrent.ExecutionContext.global

  val route = HttpRoutes.of[IO] {
      case request @ GET -> Root =>
          StaticFile
                .fromResource[IO]("index.html", Some(request)).getOrElseF(NotFound())


      case request @ GET -> Root / "assets" / "javascript" / path if staticFileAllowed(path) =>
          StaticFile
                .fromResource("public/javascript/" + path, Some(request))
                .getOrElseF(NotFound())

      case request@GET -> Root / "assets" / path if staticFileAllowed(path) =>
        StaticFile
          .fromResource("public/" + path, Some(request))
          .getOrElseF(NotFound())


      case request@GET -> Root / "assets" / "stylesheets" / path if staticFileAllowed(path) =>
          StaticFile
              .fromResource("public/stylesheets/" + path, Some(request))
              .getOrElseF(NotFound())
  }

  private def staticFileAllowed(path: String) = List(".gif", ".js", ".css", ".map", ".html", ".webm").exists(path.endsWith)


}
