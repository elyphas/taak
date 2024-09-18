package server_routes

import org.http4s._
import org.http4s.dsl.io._
import shared.EventsDB.{EventsDBResult, EventsDBWillDox, NotFoundDB}
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import org.http4s.circe._
import io.circe.generic.auto._
import io.circe.syntax._
import io.circe.literal._
import io.circe.{Decoder, Encoder}
import cats.effect._
import io.circe.generic.semiauto.deriveEncoder
import scarango_context.CItemRecipe
import shared.mytypes.{ItemRecipe, Recipe, TypeDocument}

object GetLstItemsRecipeRoute {

    //implicit val decoder: EntityDecoder[IO, List[TypeDocument]] = jsonOf[IO, List[TypeDocument]]
    //implicit val decoder: EntityDecoder[IO, List[TypeDocument]] = jsonOf[IO, List[TypeDocument]]
    //import circe_taak_decoders.circe_taak_decoders.recipeDecoder
    implicit val bodyPostEncode: EntityEncoder[IO, (List[ItemRecipe], EventsDBResult)] = jsonEncoderOf
    //implicit val responseDecode: EntityDecoder[IO, (Recipe, EventsDBWillDox)] = jsonOf[IO, (Recipe, EventsDBWillDox)]

    //implicit val responseDecode: EntityDecoder[IO, Recipe] = jsonOf[IO, Recipe]
    import circe_codec.Circe_codec.{recipeEncoder, recipeDecoder}
    implicit val responseDecode: EntityDecoder[IO, (Recipe, EventsDBWillDox)] = jsonOf[IO, (Recipe, EventsDBWillDox)]

    val route = HttpRoutes.of[IO] {
          case req @ POST -> Root / "getLstItemsRecipe" =>
              val result = for {
                (item, event@_) <- req.as[(Recipe, EventsDBWillDox)]
                  res <- {
                      val ctable  = new CItemRecipe
                      ctable.getDetails(item)
                  }
              } yield {
                res.fold( l => NotFound(NotFoundDB(s"Hubo problemas ${l}")), { case (l, e) => Ok ( (l.map ( _.asJson ), e.asJson) ) } )
              }
              result.flatten
      }
}