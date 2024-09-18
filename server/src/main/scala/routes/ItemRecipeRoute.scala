package server_routes

import org.http4s._
import org.http4s.dsl.io._
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import org.http4s.circe._
import io.circe.generic.auto._
import io.circe.syntax._
import io.circe.literal._
import cats.effect._
import scarango_context.{CItemRecipe, CRecipe}
import shared.EventsDB.{EventsDBResult, EventsDBWillDox}
import shared.mytypes.ItemRecipe

object ItemRecipeRoute {

    implicit val bodyPostEncode: EntityEncoder[IO, (ItemRecipe, EventsDBResult)] = jsonEncoderOf
    implicit val responseDecode: EntityDecoder[IO, (ItemRecipe, EventsDBWillDox)] = jsonOf

    val route = HttpRoutes.of[IO] {
                 case req @ POST -> Root / "eventItemRecipe" =>
                   val res = for {
                        (item, event) <- req.as[(ItemRecipe, EventsDBWillDox)]
                        res <- {
                            val ctable  = new CItemRecipe
                            ctable.event(item, event)
                        }
                    } yield res.fold(l => NotFound(l), { case (item, event) => Ok((item.asJson, event:EventsDBResult))})

                    res.flatten
            }
            
}