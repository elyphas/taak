package server_routes

import org.http4s._
import org.http4s.dsl.io._
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import org.http4s.circe._
import io.circe.generic.auto._
import io.circe.syntax._
import io.circe.literal._
import cats.effect._
import scarango_context.{CInstitution, CRecipe}
import shared.EventsDB.{EventsDBResult, EventsDBWillDox}
import shared.mytypes.{Institution, Recipe}

object EventsOnCollectionRoute {

    val route = HttpRoutes.of[IO] {
                 case req @ POST -> Root / "eventRecipe" =>

                   import circe_codec.Circe_codec.{recipeEncoder, recipeDecoder}

                   implicit val bodyPostEncode: EntityEncoder[IO, (Recipe, EventsDBResult)] = jsonEncoderOf
                   implicit val responseDecode: EntityDecoder[IO, (Recipe, EventsDBWillDox)] = jsonOf

                   val res = for {
                        (item, event) <- req.as[(Recipe, EventsDBWillDox)]
                        res <- {
                            val ctable  = new CRecipe
                            ctable.event(item, event)
                        }
                    } yield res.fold(l => NotFound(l), { case (item, event) => Ok((item.asJson, event:EventsDBResult))})

                    res.flatten
                 case req @ POST -> Root / "eventInstitution" =>

                   implicit val bodyPostEncode: EntityEncoder[IO, (Institution, EventsDBResult)] = jsonEncoderOf
                   implicit val responseDecode: EntityDecoder[IO, (Institution, EventsDBWillDox)] = jsonOf

                   val res = for {
                     (item, event) <- req.as[(Institution, EventsDBWillDox)]
                     res <- {
                       val ctable  = new CInstitution
                       ctable.event(item, event)
                     }
                   } yield res.fold(l => NotFound(l), { case (item, event) => Ok((item.asJson, event:EventsDBResult))})

                   res.flatten

    }
            
}