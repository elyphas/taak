package server_routes

import cats.Show
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import org.http4s.circe._
import io.circe.generic.auto._
import io.circe.syntax._
import io.circe.literal._
import shared.QueryCatalog
import cats.effect._
import scarango_context.{CQueryArticulo, CQueryInstitution}
import shared.EventsDB.{EventsDBResult, EventsDBWillDox}

object FTS_Route {

    implicit val responseDecode: EntityDecoder[IO, (QueryCatalog, EventsDBWillDox)] = jsonOf

    val route = HttpRoutes.of[IO] {
                 case req @ POST -> Root / "fts" =>
                   val res = for {
                        (query, event@_) <- req.as[(QueryCatalog, EventsDBWillDox)]
                        res <- {

                            val ctable  = new CQueryArticulo
                            query.field match {
                              case "key_item" => ctable.idContains(query.search)
                              case "description_item" => ctable.search(query.search)
                            }
                        }
                    } yield res.fold(l => NotFound(l), { case (lst, event) => Ok((lst .asJson, event:EventsDBResult ))})
                    res.flatten
                 case req @ POST -> Root / "ftsInstitutions" =>
                   val res = for {
                     (query, event@_) <- req.as[(QueryCatalog, EventsDBWillDox)]
                     res <- {

                       val ctable  = new CQueryInstitution
                       query.field match {
                         case "id_institution" => ctable.idContains(query.search)
                         case "description" => ctable.search(query.search)
                       }
                     }
                   } yield res.fold(l => NotFound(l), { case (lst, event) => Ok((lst .asJson, event:EventsDBResult ))})
                   res.flatten
            }
}