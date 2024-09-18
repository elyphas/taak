package scarango_context

import shared.EventsDB._
import types_scarango._
import cats._
import cats.effect.IO
import cats.effect.unsafe.implicits.global
import cats.implicits._
import com.outr.arango.query.dsl._
import com.outr.arango.query._
import shared.{ArticuloStock, Stock}

import scala.util.{Failure, Success}
import scala.concurrent._
import scala.concurrent.duration._
import scala.language.postfixOps
import types_scarango.{ArticuloScarango, ArticuloStockScarango}

class CQueryArticulo extends ConnectionScarango {

    def search(txt: String): IO[Either[String, (List[ArticuloStock], EventsDBResult)]] = {

      val query = aql""" FOR d IN v_tblartic7
                              SEARCH NGRAM_MATCH(d.descripcion, $txt, 0.6, "probar_trigram")

                            LET query_stocks = (FOR s IN tbl_stocks
                                FILTER s.area == 'FARMACIA' and s.cve_articulo == d.cve_articulo
                                    RETURN s
                            )

                            FOR stocks IN ( LENGTH(query_stocks) > 0 ? query_stocks : [{}])

                            SORT BM25(d) DESC

                            FILTER //'HBCZ' IN d.entidad and
                                stocks.stock != null

                            RETURN {
                                  //entidad: d.entidad,
                                  //area: stocks.area,
                                  cve_articulo: d.cve_articulo,
                                  descripcion: d.descripcion,
                                  unidad: d.unidad,
                                  presentacion: d.presentacion,
                                  unid_med_pres: d.unid_med_pres,
                                  partida: d.partida,
                                  stock: stocks.stock,
                            }"""

        for {
            results <- db
                          .tblartic
                          .query(query)
                          .as[ArticuloStockScarango]
                          .toList
        } yield {
            //val results2 = results.result.map { itemScarango => ArticuloStock.toNative(itemScarango) }
            val results2 = results.map { itemScarango => ArticuloStockScarango.toNative(itemScarango) }
            val event = if(results2.nonEmpty) FoundDB():EventsDBResult else NotFoundDB(): EventsDBResult
            ((results2, event)).asRight[String]
        }

        /**r.unsafeToFuture().transformWith {
            case Success(value) => Future.successful(value)
            case Failure(error) => Future.successful(Left(error.getMessage + "--" + error.getLocalizedMessage))
        }*/
    }

    def idContains(txt: String): IO[Either[String, (List[ArticuloStock], EventsDBResult)]] = {

        val txt2 = s"%$txt%"
        val query =aql"""FOR d IN tblartic
                            FILTER LIKE(d.cve_articulo, $txt2) //and 'HBCZ' in d.entidad

                                  LET query_stocks = (FOR s IN tbl_stocks
                                                FILTER s.area == 'FARMACIA' and  s.cve_articulo == d.cve_articulo
                                                              RETURN s)
                                    FOR stocks IN ( LENGTH(query_stocks) > 0 ? query_stocks : [{}] )
                                    //FILTER 'HBCZ' IN d.entidad
                                    RETURN {
                                          cve_articulo: d.cve_articulo,
                                          descripcion: d.descripcion,
                                          unidad: d.unidad,
                                          presentacion: d.presentacion,
                                          unid_med_pres: d.unid_med_pres,
                                          partida: d.partida,
                                          stock: stocks.stock,
                                    }
                        """

        for {
              results <- db
                              .tblartic
                              .query(query)
                              .as[ArticuloStockScarango]
                              .toList
                              //.all
                              //.cursor
        } yield {
              val results2 = results.map(ArticuloStockScarango.toNative)
              val event: EventsDBResult = if (results2.nonEmpty) FoundDB() else NotFoundDB()

              ((results2, event)).asRight[String]
        }
    }

    def upsert(item: ArticuloStock): IO[Either[String, (ArticuloStock, EventsDBResult)]] =
          for {
              _ <- db.stocks.upsert(ArticuloStockScarango.toScarango(item))
          } yield ( (item, SavedDB()) ).asRight[String]

    //def updateStock(item: Stock, output: Int): Future[Either[String, (Long, EventsDBResult)]] = {
    /**def updateStock(item: Stock, output: Int): Future[Either[String, (Long, EventsDBResult)]] = {
        val query =
            aql"""
                FOR a IN tblartic
                FILTER a.entidad == ${item.entidad} and a.area == ${item.area} and  a.cve_articulo == ${item.key_item}
                    UPDATE a WITH {
                        stock: a.stock - $output
                    }
                    IN tblartic
                    LET new = NEW
                    RETURN new
                """

        db
              .tblartic
              .query(query)

    }*/

}