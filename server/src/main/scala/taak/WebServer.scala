//package manik1.manik1

import cats.effect.{IO, IOApp}
import org.http4s._
import org.http4s.server.Router
import org.http4s.HttpRoutes
import org.http4s.Method.GET
import org.http4s.dsl.io._
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.implicits._
import org.http4s.server.middleware.{ErrorAction, ErrorHandling}
import org.http4s.circe._
import org.http4s.circe.jsonOf
import org.http4s.circe.CirceEntityDecoder._
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import com.comcast.ip4s.{Host, IpLiteralSyntax, Port}
import io.circe.Encoder
import io.circe.generic.semiauto.deriveEncoder
import io.circe.literal._
import io.circe.syntax._
import io.circe.generic.auto._
import server_routes._
import shared.EventsDB.EventsDBResult

//import spatutorial.shared.{Articulo, Id_Comparativo, QueryCatalog}
//import spatutorial.shared.comparativostypes.ViewProcess
//import spatutorial.shared.types.qryrow.QryRow

//import scala.concurrent.Future

import cats.implicits._

import scala.concurrent.duration._

//object WebServerSicap  extends IOApp.Simple {
object Main  extends IOApp.Simple {

  import org.http4s.ember.client.EmberClientBuilder
  import cats.effect.unsafe.implicits.global

  implicit val ec: scala.concurrent.ExecutionContext = scala.concurrent.ExecutionContext.global

  /*val routes = HttpRoutes.of[IO] {
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

  }*/

  private def staticFileAllowed(path: String) = List(".gif", ".js", ".css", ".map", ".html", ".webm").exists(path.endsWith)

  val allRoutes = StaticFilesRoutes.route <+>
                  GetItemsFromCatalogRoute.route <+>
                  EventsOnCollectionRoute.route <+>
                  GetLstItemsRecipeRoute.route <+>
                  FTS_Route.route <+>
                  ItemRecipeRoute.route <+>
                  FileRoute.route

  val app = Router(
                    "/" -> allRoutes
                ).orNotFound

  val appWithErrorLogging = ErrorHandling.Recover.total(
                                                ErrorAction.log(
                                                    app,
                                                    messageFailureLogAction = (t, msg) => IO.println(msg) >> IO.println(t),
                                                    serviceErrorLogAction = (t, msg) => IO.println(msg) >> IO.println(t)
                                                )
                                          )

  val runServer: IO[Unit] =
    EmberServerBuilder
          .default[IO]
          .withPort(port"8091")
          .withHost(host"127.0.0.1") // Usually people use 0.0.0.0 to bind to any public IP address of your machine.
          .withHttpApp(appWithErrorLogging)
          .withShutdownTimeout(1.second)
          .build
          .evalTap { server =>
            IO.println(s"Server started at ${server.address}")
          }.useForever

    override final val run: IO[Unit] = runServer
}
