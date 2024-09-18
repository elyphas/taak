package scarango_context

import cats.effect.IO
import types_scarango.ItemOutputs

import scala.language.postfixOps

import com.outr.arango.query._

class COutputs extends ConnectionScarango {

    def qryOutputs(year: Int, month: Int) = aql"""
          FOR item IN tblrecipe_details

              LET item_por_pieza = (
                  FOR i IN tbl_claves_por_pieza FILTER i.cve_articulo == item.key_item RETURN i
              )

              FOR itemPre IN ( LENGTH(item_por_pieza) > 0 ? item_por_pieza: [ { /* no match exists*/ } ] )

              LET recipesFechas = (
                  FOR r IN tblrecipe
                      FILTER  r.fiscal_period == item.fiscal_period &&
                              r.folio == item.folio &&
                              r.type_document == item.type_document

                  RETURN r.date
              )

              FOR recipeFech IN ( LENGTH ( recipesFechas ) > 0 ? recipesFechas: [ { /* no match exists*/ } ] )

              COLLECT
                  year = DATE_YEAR(recipeFech),
                  month = DATE_MONTH(recipeFech),
                  day = DATE_DAY(recipeFech),
                  fecha = DATE_FORMAT(recipeFech, "%dd/%mm/%yyyy"),
                  cve_articulo = item.key_item,
                  descripcion = item.description_item,
                  presentacion = itemPre.presentacion
              AGGREGATE output = SUM(item.supplied)
              SORT fecha, cve_articulo, output DESC
              FILTER
                  //day >= 14 &&
                  month == ${month} &&
                  year == ${year} &&
                  presentacion == null

              RETURN {
                          fecha,
                          cve_articulo,
                          descripcion,
                          output,
                          presentacion
              }"""

    def getDetailsRecipe(year: Int, month: Int): IO[List[ItemOutputs]] =
              db.query[ItemOutputs](qryOutputs(year, month)).toList


}