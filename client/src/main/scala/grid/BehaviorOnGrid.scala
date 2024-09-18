package gridCatalogs

import encoder_json.HelpersLabelledGeneric.JsonEncoder
import encoder_json.types.{JsonInt, JsonLong, JsonString, JsonValue}
import grid.behavior.BehaviorOnGrid

import outwatch.{StyleIsBuilder, VMod}
import outwatch.dsl.{border, cls, textAlign, width}
import outwatch_components.ColG_Json

import shared.mytypes.{ItemInput, ItemRecipe}

object BehaviorOnGrid {

  def apply[A](implicit behaviorOnGrid: BehaviorOnGrid[A]): BehaviorOnGrid[A] = behaviorOnGrid

  implicit val behaviorOnGridItemRecipe: BehaviorOnGrid[ItemRecipe] = new BehaviorOnGrid[ItemRecipe] {

    val jsonEncoder: JsonEncoder[ItemRecipe] = JsonEncoder[ItemRecipe]
    val eventsRow: VMod = VMod.empty
    val eventsCell: VMod = VMod.empty
    val transitionOnInsert: Int = 9
    val transitionOnUpdateFields: Int = 14
    val identifier: Option[String] = Some("tblGrid") // tblGrid   tblLstItems

    val recordValid: ((Option[Map[String, JsonValue]], Boolean)) => (ItemRecipe, Boolean) = { case ((record, isNew)) =>
      (ItemRecipe.getValidItem(record).toOption.getOrElse(ItemRecipe()), isNew)
    }

    val isRecordValid: ((Option[Map[String, JsonValue]], Boolean)) => Boolean = { case ((record, isNew@_)) => ItemRecipe.isValid(record) }

    val getRecordValid: ((Option[Map[String, JsonValue]], Boolean)) => (ItemRecipe, Boolean) = { case ((record, isNew)) =>
                             ItemRecipe.getValidItem(record)
                                          .map((_, isNew))
                                          .getOrElse((ItemRecipe(), false))
                      }

    val getRecordInvalid: ((Option[Map[String, JsonValue]], Boolean)) => String  = { case ((record, isNew)) =>
      ItemRecipe.getValidItem(record).fold(l => l, r => "")
    }

    //val lstNamesAndTypes: GetNamesAndTypes.Cpo[ItemRecipe] = (new GetNamesAndTypes.Cpo[ItemRecipe] {}.withPrimaryKey(identity))
    //private val lstNamesAndTypes: List[(String, String)] = (new GetNamesAndTypes2.Cpo[ItemRecipe] {}.withPrimaryKey(identity))

    val colsFmt: Seq[ColG_Json] = Seq(

      ColG_Json( field = ("user", JsonString("")) ),
      ColG_Json( field = ("entidad", JsonString("")) ),
      ColG_Json( field = ("area", JsonString("")) ),
      ColG_Json( field = ("fiscal_period", JsonInt(2020)) ),
      ColG_Json( field = ("folio", JsonString("")) ),
      ColG_Json( field = ("date", JsonString("")) ),
      ColG_Json( field = ("type_document", JsonString("")) ),
      ColG_Json( field = ("timestamp", JsonLong(0L)) ),

      ColG_Json(field = ("key_item", JsonString("")), title = Some("Cve. Articulo"),
        styleTitle = Some(VMod(width := "5%")),
        styleCell = Some(VMod(textAlign := "center")), edit = true
      ),
      ColG_Json(field = ("description_item", JsonString("")),
        title = Some("Descripción"),
        styleTitle = Some(VMod(width := "25%")),
        styleCell = Some(VMod(cls := "ElementEditTextArea")),
        edit = true
      ),
      ColG_Json(field = ("measure_unit", JsonString("")),
        title = Some("Unidad de Medida"), styleTitle = Some(VMod(width := "8%")),
        styleCell = Some(VMod(textAlign := "center"))),

      ColG_Json(field = ("presentation", JsonInt(0)),
        title = Some("Presentación"),
        styleTitle = Some(VMod(width := "4%")),
        styleCell = Some(VMod(textAlign := "justify", border := "1px solid black")), inputType = "TextArea"
      ),
      ColG_Json(field = ("package_unit_measure", JsonString("")),
        title = Some("Unidad Medida Presentacion"), styleTitle = Some(VMod(width := "5%")),
        styleCell = Some(VMod(textAlign := "center", border := "1px solid black"))),

      ColG_Json(field = ("prescription", JsonInt(0))),

      ColG_Json(field = ("supplied", JsonInt(0)),
        title = Some("surtida"),
        styleTitle = Some(VMod(width := "5%")),
        styleCell = Some(VMod(textAlign := "center", border := "1px solid black")), edit = true
      )
    )

  }

  /**implicit val behaviorOnGridItemInput: BehaviorOnGrid[ItemInput] = new BehaviorOnGrid[ItemInput] {

    val recordValid: ((Option[Map[String, JsonValue]], Boolean)) => (ItemInput, Boolean) = { case ((record, isNew)) =>
      (ItemInput.getValidItem(record).toOption.getOrElse(ItemInput()), isNew)
    }

    val isRecordValid: ((Option[Map[String, JsonValue]], Boolean)) => Boolean = { case ((record, isNew@_)) => ItemInput.isValid(record) }

    val getRecordValid: ((Option[Map[String, JsonValue]], Boolean)) => (ItemInput, Boolean) = { case ((record, isNew)) =>
      ItemInput.getValidItem(record)
        .map((_, isNew))
        .getOrElse((ItemInput(), false))
    }

    val getRecordInvalid: ((Option[Map[String, JsonValue]], Boolean)) => String  = { case ((record, isNew)) =>
      ItemInput.getValidItem(record).fold(l => l, r => "")
    }

  }*/

}
