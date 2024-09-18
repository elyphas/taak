package scarango_context

import com.arangodb.ArangoDB
import com.outr.arango._
import profig.Profig
import com.outr.arango.collection.{DocumentCollection, EdgeCollection}
import com.outr.arango.core.ArangoDBConfig
import types_scarango.{ArticuloScarango, ArticuloStockScarango, InstitutionScarango, ItemRecipeScarango, RecipeScarango, TypeDocumentScarango, UserScarango}

//import types_scarango.{ArticuloStockScarango, ClavesXPiezaScarango, Inputs, InputsScarango, ItemInputScarango,  ItemRequisitionScarango, PackBalanceScarango, PackOutputFromStockScarango, Proveedor,  Requisition, RequisitionScarango, StockScarango, TypeDocumentScarango, UserScarango}
//import types_scarango.ArticuloScarango

class ConnectionScarango {
      implicit val ec: scala.concurrent.ExecutionContext = scala.concurrent.ExecutionContext.global

      Profig.initConfiguration()    //.map { in => println(in) }

      //lazy val db = new ArangoDB(credentials = Some(Credentials("root", "jctaurys")))
      //lazy val dbExample = db.api.db("example")

      //lazy val db = new ArangoDB()          //(credentials = Some(Credentials("root", "jctaurys")))
}

import com.outr.arango.core.Host

object db extends Graph(
                              name = "almacenes",
                              ArangoDBConfig(
                                    username = "root",
                                    password = "jctaurys",
                                    //hosts = List(Host("172.17.0.2", 8529))
                              )
                        ) {



      val tblartic: DocumentCollection[ArticuloScarango, ArticuloScarango.type] = vertex(ArticuloScarango)

      val stocks: DocumentCollection[ArticuloStockScarango, ArticuloStockScarango.type] = vertex(ArticuloStockScarango)

      val tblusers: DocumentCollection[UserScarango, UserScarango.type] = vertex(UserScarango)

      val tbltype_documents: DocumentCollection[TypeDocumentScarango, TypeDocumentScarango.type] = vertex(TypeDocumentScarango)
//
//      val tblStocks: DocumentCollection[StockScarango, StockScarango.type] = vertex(StockScarango)
//
//      val tblclavesxpieza: DocumentCollection[ClavesXPiezaScarango, ClavesXPiezaScarango.type] = vertex(ClavesXPiezaScarango)

      val institution: DocumentCollection[InstitutionScarango, InstitutionScarango.type] = vertex(InstitutionScarango)

      val recipe: DocumentCollection[RecipeScarango, RecipeScarango.type] = vertex(RecipeScarango)

      val detailsRecipe: DocumentCollection[ItemRecipeScarango, ItemRecipeScarango.type] = vertex(ItemRecipeScarango)

//      val tblinputs: DocumentCollection[InputsScarango, InputsScarango.type] = vertex(InputsScarango)
//
//      val tblinputs_details: DocumentCollection[ItemInputScarango, ItemInputScarango.type] = vertex(ItemInputScarango)
//
//      val requisition: DocumentCollection[RequisitionScarango, RequisitionScarango.type] = vertex(RequisitionScarango)
//
//      val requisition_details: DocumentCollection[ItemRequisitionScarango, ItemRequisitionScarango.type] = vertex(ItemRequisitionScarango)
//
//      val quitar_tblartic: DocumentCollection[ArticuloStockScarango, ArticuloStockScarango.type] = vertex(ArticuloStockScarango)
//
//      //val tblbalanceItemCollective: DocumentCollection[PackBalanceScarango] = vertex[PackBalanceScarango](PackBalanceScarango)
//      val tblPackBalance: DocumentCollection[PackBalanceScarango, PackBalanceScarango.type] = vertex(PackBalanceScarango)
//
//      val packOutputFromStock: DocumentCollection[PackOutputFromStockScarango, PackOutputFromStockScarango.type] = vertex(PackOutputFromStockScarango)
//
//      val stock: DocumentCollection[StockScarango, StockScarango.type] = vertex(StockScarango)

}