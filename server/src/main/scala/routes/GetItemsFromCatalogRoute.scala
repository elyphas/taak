package server_routes

import org.http4s._
import org.http4s.dsl.io._
import shared.EventsDB.NotFoundDB
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import org.http4s.circe._
import io.circe.generic.auto._
import io.circe.syntax._
import io.circe.literal._
import io.circe.{Decoder, Encoder}
import cats.effect._
import scarango_context.{CTypeDocument, CUsers}
import io.circe.generic.semiauto.deriveEncoder
import shared.mytypes.TypeDocument

object GetItemsFromCatalogRoute {

    //implicit val decoder: EntityDecoder[IO, List[TypeDocument]] = jsonOf[IO, List[TypeDocument]]
    //implicit val decoder: EntityDecoder[IO, List[TypeDocument]] = jsonOf[IO, List[TypeDocument]]

    val route = HttpRoutes.of[IO] {
           case GET -> Root / "getAllTypeDocs" =>
              val result = for {
                  res <- {
                      val ctable  = new CTypeDocument
                      ctable.getAll()
                  }
              } yield res.fold( l => NotFound(NotFoundDB(s"Hubo problemas ${l}")), { case (l, e) => Ok ( (l.map ( _.asJson ), e.asJson) ) } )
              result.flatten

           case GET -> Root / "getAllUser" =>
             val result = for {
               res <- {
                 val ctable  = new CUsers
                 ctable.getAll()
               }
             } yield res.fold( l => NotFound(NotFoundDB(s"Hubo problemas ${l}")), { case (l, e) => Ok ( (l.map ( _.asJson ), e.asJson) ) } )
             result.flatten
      }
}