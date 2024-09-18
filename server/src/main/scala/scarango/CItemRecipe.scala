package scarango_context

//import cats._
import cats.effect.IO
//import cats.effect.unsafe.implicits.global
//import com.outr.arango.core.CreateOptions
import cats.implicits._
import com.outr.arango.query.dsl._

//import scala.concurrent.duration._
import scala.language.postfixOps
//import shared.ArticuloStock
import shared.EventsDB._
import shared.mytypes.{ItemRecipe, Recipe}
//import spatutorial.shared.typeclass.Show
//import spatutorial.shared.typeclass.Show._
import types_scarango.ItemRecipeScarango

//import scala.util.{Failure, Success}

class CItemRecipe extends ConnectionScarango {

  def getDetails(item: Recipe): IO[Either[String, (List[ItemRecipe], EventsDBResult)]] = {

      val i = ItemRecipeScarango.ref

      val query = aql {
                        FOR (i) IN db.detailsRecipe
                        FILTER ( (i.type_document === item.type_document)
                                  && (i.fiscal_period === item.fiscal_period)
                                  && (i.folio === item.folio)
                                  && (i.date === item.date2)
                                  && (i.user === item.user)
                              )
                        RETURN (i)
                  }

        db
            .detailsRecipe
            .query(query)
            .as[ItemRecipeScarango]
            .toList
            .map {
                    case Nil => Right(List.empty[ItemRecipe], NotFoundDB())
                    case y => Right(y.map(ItemRecipeScarango.toNative),FoundDB())
            }
    }

  private def deleteMany(item: Recipe): IO[Either[String, EventsDBResult]] = {

        val i = ItemRecipeScarango.ref

        val query = aql {
                          FOR (i) IN db.detailsRecipe
                          FILTER ((i.type_document === item.type_document) && (i.fiscal_period === item.fiscal_period) && (i.folio === item.folio))
                          REMOVE (i) IN db.detailsRecipe
                      }

        db
          .detailsRecipe
          .query(query)
          .as[String]
          .toList
          .map { _ =>
              (DeletedDB()).asRight[String]
              /*x.result match {
                  case Nil => (DeletedDoobie).asRight[String]
                  case x => Right(x.head.toInputs, FoundDoobie())
                  case _ => Left("Hubo un problema")
              }*/
          }
    }

  private def delete(item: ItemRecipe): IO[Either[String, (ItemRecipe, EventsDBResult)]] = {

    val identity = ItemRecipeScarango.createId(item)  //item.fiscal_period + "-" + item.folio + "-" + item.key_item

    for {
      res <- db.detailsRecipe.delete( ItemRecipeScarango.id ( identity ) )
    } yield ((item, DeletedDB()) ).asRight[String]

  }

  private def upsert(item: ItemRecipe): IO[Either[String, (ItemRecipe, EventsDBResult)]] = {
        db
          .detailsRecipe
          .upsert( ItemRecipeScarango.toScarango(item)/**, CreateOptions(waitForSync = true)*/)
          .map { x => (item, UpdatedDB()).asRight[String] }
  }

  private def insert(item: ItemRecipe): IO[Either[String, (ItemRecipe, EventsDBResult)]] = {
    db
      .detailsRecipe
      .insert( ItemRecipeScarango.toScarango(item)/**, CreateOptions(waitForSync = true)*/)
      .map { x => (item, InsertedDB()).asRight[String] }
  }

  private def save(item: ItemRecipe, event: EventsDBWillDo) =
            if (event == InsertDB) upsert(item) else delete(item)

  def event(item: ItemRecipe, event: EventsDBWillDox): IO[Either[String, (ItemRecipe, EventsDBResult)]] = {
        event match {
          case InsertDBx(path, id) => insert(item)
          case DeleteDBx(path, id) => delete(item)
          case UpdateDBx(path, id) => upsert(item)
        }
  }

  /*def saveItemRecipe00(item: ItemRecipe, event: EventsDBWillDo) = {
      
          val articulo = new CQueryArticulo

          val calCollective = new CCalcCollective
          val itemPackCollective = ItemRecipe.toItemPackCollectiveOutputs(item)
          val cveXPieza = ItemRecipe.toClaveXPieza(item)
          val clavesXPieza = new CClavesXPieza

          for {

            cItemRec <- EitherT(upsert(item))         /** <<-- registra un item en la tabla tblrecipe_details */
            pieceByPiece <- EitherT(clavesXPieza.byId(cveXPieza)) /** <<--  lee si el articulo se encuentra en los articulos que se encuentran por piezas*/

            pack <- EitherT(
                if (pieceByPiece._1.presentacion > 1) /** <<-- verifica si el articulo que estaba registrado que es entregado por piezas es mayor que 1 */
                    calCollective.calculateMovementInPackCollective(itemPackCollective, item.supplied, event )
                else Future.successful (
                    (ItemPackCollectiveOutputs(), ItemPackCollectiveOutputsFromStock()).asRight[String]
                )
            )
            _ <- if (pack._2.output > 0)
                EitherT{
                    val itemArticulo: ItemPackCollectiveOutputsFromStock = pack._2
                    articulo.updateStock(ItemPackCollectiveOutputsFromStock.toStock(itemArticulo), pack._2.output )
                }
            else
                EitherT.right[String](Future.successful( ( 0, SavedDB() )))
          } yield cItemRec
  }*/

}