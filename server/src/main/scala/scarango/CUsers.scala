package scarango_context

import types_scarango._
import cats._
import cats.effect.unsafe.implicits.global
import cats.implicits._
import com.outr.arango._
import com.outr.arango.query._
import com.outr.arango.query.dsl._
import shared.EventsDB.{EventsDBResult, FoundDB}

import scala.concurrent._
import scala.concurrent.duration._
import scala.language.postfixOps

class CUsers extends ConnectionScarango {

    def getAll() = {
        val query = aql"FOR doc IN tbl_users RETURN doc"
        for {
            results <- db
                            .tblusers
                            .query(query)
                            .as[UserScarango]
                            .toList

        } yield ((results.map(UserScarango.toNative), FoundDB():EventsDBResult )).asRight[String]
    }
}