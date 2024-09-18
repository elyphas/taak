package scarango_context

import shared.EventsDB._
import shared.mytypes.Institution
import types_scarango._
import cats.effect.IO
import cats.implicits._
import com.outr.arango.query._

import scala.language.postfixOps

class CQueryInstitution extends ConnectionScarango {

    def search(txt: String): IO[Either[String, (List[Institution], EventsDBResult)]] = {

      val query = aql""" FOR d IN v_tblinstitutions
                              SEARCH NGRAM_MATCH(d.description, $txt, 0.6, "analizer_tblinstitutions")
                            SORT BM25(d) DESC
                            RETURN {
                                  id_institution: d.id_institution,
                                  description: d.description,
                            }"""

        for {
            results <- db
                          .institution
                          .query(query)
                          .as[InstitutionScarango]
                          .toList
        } yield {
            val results2 = results.map { itemScarango => InstitutionScarango.toNative(itemScarango) }
            val event = if(results2.nonEmpty) FoundDB():EventsDBResult else NotFoundDB(): EventsDBResult
            ((results2, event)).asRight[String]
        }
    }

    def idContains(txt: String): IO[Either[String, (List[Institution], EventsDBResult)]] = {
        val query =aql""" FOR d IN v_tblinstitutions
                            SEARCH NGRAM_MATCH(d.id_institution, $txt, 0.6, "analizer_tblinstitutions")
                            SORT BM25(d) DESC
                            RETURN {
                                  id_institution: d.id_institution,
                                  description: d.description,
                            }
                        """

        for {
              results <- db
                              .institution
                              .query(query)
                              .as[InstitutionScarango]
                              .toList
        } yield {
              val results2 = results.map(InstitutionScarango.toNative)
              val event: EventsDBResult = if (results2.nonEmpty) FoundDB() else NotFoundDB()

              ((results2, event)).asRight[String]
        }
    }

}