package scarango_context

import cats.effect.IO
import types_scarango.InstitutionScarango
import cats.implicits._
import com.outr.arango.query._
import instance.InstanceScarango

import scala.language.postfixOps
import types_scarango.RecipeScarango
import shared.mytypes.{Institution, Recipe}
import shared.EventsDB._

class CInstitution extends ConnectionScarango {

  type ItemScarango = InstitutionScarango
  type ItemNative = Institution

  def listAll() = {
            val query = aql"FOR prov IN tblrecipe RETURN prov"
            for {
                results <- db
                            .institution
                            .query(query)
                            .as[ItemScarango]
                            .toList
            } yield {
              ((results, FoundDB())).asRight[String]
            }
  }

  def byId(item: ItemNative): IO[Either[String, (ItemNative, EventsDBResult)]] =

        for {
            res <- db.institution.get(InstitutionScarango.id(InstitutionScarango.createId(item)))
        } yield {
             res match {
                case Some(itemScarango) =>
                    val itemNative: Institution = InstitutionScarango.toNative(itemScarango)
                    Right((itemNative, FoundDB():EventsDBResult ) )
                case None => 
                    Right((Institution(), NotFoundDB():EventsDBResult))
            }
        }

    private def insert(item: ItemNative): IO[Either[String, (ItemNative, EventsDBResult)]] =
          for {
              res <- db.institution.upsert(InstitutionScarango.toScarango(item))
          } yield ((item, InsertedDB())).asRight[String]


  private def delete(item: ItemNative): IO[Either[String, (ItemNative, EventsDBResult)]] =
         for {
           _ <- db.institution.delete(InstitutionScarango.id(InstitutionScarango.createId(item)))
         } yield ((item, DeletedDB())).asRight[String]

  private def upsert(item: ItemNative): IO[Either[String, (ItemNative, EventsDBResult)]] =
        for {
            res <- db.institution.upsert(InstitutionScarango.toScarango(item))
        } yield ((item, InsertedDB())).asRight[String]


  def event(item: ItemNative, event: EventsDBWillDox):  IO[Either[String, (ItemNative, EventsDBResult)]] =
      event match {
          case InsertDBx(p, id) => for { res <- insert(item) } yield res
          case UpdateDBx(p, id) => for { res <- upsert(item) } yield res
          case DeleteDBx(p, id) => for { res <- delete(item) } yield res
          case FindDBx(p, id) => for { res <- byId(item)} yield res
      }
}