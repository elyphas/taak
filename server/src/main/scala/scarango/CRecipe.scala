package scarango_context

//import cats._
import cats.effect.IO
//import cats.effect.unsafe.implicits.global
import cats.implicits._
//import com.outr.arango._
import com.outr.arango.query._
//import com.outr.arango.query.dsl._

//import scala.concurrent._
import scala.language.postfixOps
//import java.util.Date
import types_scarango.RecipeScarango
import shared.mytypes.Recipe
import shared.EventsDB._

class CRecipe extends ConnectionScarango {

  def listAll() = {
            val query = aql"FOR prov IN tblrecipe RETURN prov"
            for {
                results <- db
                            .recipe
                            .query(query)
                            .as[RecipeScarango]
                            .toList
            } yield {
              ((results, FoundDB())).asRight[String]
            }
  }

  def byId(item: Recipe): IO[Either[String, (Recipe, EventsDBResult)]] =
        //println(s" el id que va ha buscar: ${RecipeScarango.createId(item)}")
        for {
            res <- db.recipe.get ( RecipeScarango.id ( RecipeScarango.createId ( item ) ) )
        } yield {
             res match {
                case Some(itemScarango) =>
                    val recipe: Recipe = RecipeScarango.toNative(itemScarango)
                    Right((recipe, FoundDB():EventsDBResult ) )
                case None => 
                    Right((Recipe(), NotFoundDB():EventsDBResult))
            }
        }

    private def insert(item: Recipe): IO[Either[String, (Recipe, EventsDBResult)]] =
          for {
              res <- db.recipe.upsert(RecipeScarango.toScarango(item))
          } yield ((item, InsertedDB())).asRight[String]


  private def delete(item: Recipe): IO[Either[String, (Recipe, EventsDBResult)]] =
         for {
           _ <- db.recipe.delete(RecipeScarango.id(RecipeScarango.createId(item)))
         } yield ((item, DeletedDB())).asRight[String]

  private def upsert(item: Recipe): IO[Either[String, (Recipe, EventsDBResult)]] =
        for {
            res <- db.recipe.upsert( RecipeScarango.toScarango(item))
        } yield ((item, InsertedDB())).asRight[String]


  def event(item: Recipe, event: EventsDBWillDox):  IO[Either[String, (Recipe, EventsDBResult)]] =
      event match {
          case InsertDBx(p, id) => for { res <- insert(item) } yield res
          case UpdateDBx(p, id) => for { res <- upsert(item) } yield res
          case DeleteDBx(p, id) => for { res <- delete(item) } yield res
          case FindDBx(p, id) => for { res <- byId(item)} yield res
      }
}