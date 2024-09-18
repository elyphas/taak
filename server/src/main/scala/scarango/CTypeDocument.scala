package scarango_context

import cats.implicits._

import com.outr.arango.query._

import shared.EventsDB._
import scala.language.postfixOps
import types_scarango.TypeDocumentScarango

class CTypeDocument extends ConnectionScarango {

    def getAll() = {
        val query = aql"FOR doc IN tbl_type_documents RETURN doc"
        for {
            results <- db
                            .tbltype_documents
                            .query(query)
                            .as[TypeDocumentScarango]
                            .toList
        } yield ((results.map(TypeDocumentScarango.toNative), FoundDB():EventsDBResult )).asRight[String]
    }

}