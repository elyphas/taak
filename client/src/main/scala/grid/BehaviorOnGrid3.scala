package gridCatalogs

import encoder_json.types.JsonValue
import grid.behavior.BehaviorOnGrid3
import outwatch.{StyleIsBuilder, VMod}
import outwatch.dsl.{border, cls, textAlign, width}
import outwatch_components.ColG_Json2
import shapeless.ops.hlist
import shapeless.ops.hlist.ToTraversable
import shapeless.ops.record.{Keys, Values}
import shapeless.{HList, HNil, LabelledGeneric, Poly0, Poly1, Typeable}
//import outwatch_components.mytypes.EventsDB.{EventsDBWillDox, InsertDBx, UpdateDBx}
import shared.mytypes.{ItemInput, ItemRecipe}

/**    ++++++++++++ Testing Shapeless   ++++++++++++      */

object BehaviorOnGrid3 {

  def apply[A](implicit behaviorOnGrid: BehaviorOnGrid3[A]): BehaviorOnGrid3[A] = behaviorOnGrid

  implicit val behaviorOnGridItemRecipe: BehaviorOnGrid3[ItemRecipe] = new BehaviorOnGrid3[ItemRecipe] {

    //val jsonEncoder: JsonEncoder[ItemRecipe] = JsonEncoder[ItemRecipe]
    val eventsRow: VMod = VMod.empty
    val eventsCell: VMod = VMod.empty
    val transitionOnInsert: Int = 9
    val transitionOnUpdateFields: Int = 14
    val identifier: Option[String] = Some("tblLstItems")

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

    val lstNamesAndTypes: List[(String, String)] = (new GetNamesAndTypes.Cpo[ItemRecipe] {}.withPrimaryKey(identity))

    val colsFmt: Seq[ColG_Json2] = Seq(

      ColG_Json2( /**field = ("user", JsonString(""))*/ ),
      ColG_Json2( /**field = ("entidad", JsonString(""))*/ ),
      ColG_Json2( /**field = ("area", JsonString(""))*/ ),
      ColG_Json2( /**field = ("fiscal_period", JsonInt(2020))*/ ),
      ColG_Json2( /**field = ("folio", JsonString(""))*/ ),
      ColG_Json2( /**field = ("date", JsonString(""))*/ ),
      ColG_Json2( /**field = ("type_document", JsonString(""))*/ ),
      ColG_Json2( /**field = ("timestamp", JsonLong(0L))*/ ),

      ColG_Json2(
        //field = ("key_item", JsonString("")),
        title = Some("Cve. Articulo"),
        styleTitle = Some(VMod(width := "5%")),
        styleCell = Some(VMod(textAlign := "center")), edit = true
      ),
      ColG_Json2(
        //field = ("description_item", JsonString("")),
        title = Some("Descripción"),
        styleTitle = Some(VMod(width := "25%")),
        styleCell = Some(VMod(cls := "ElementEditTextArea")),
        edit = true
      ),
      ColG_Json2(
        //field = ("measure_unit", JsonString("")),
        title = Some("Unidad de Medida"), styleTitle = Some(VMod(width := "8%")),
        styleCell = Some(VMod(textAlign := "center"))),

      ColG_Json2(
        //field = ("presentation", JsonInt(0)),
        title = Some("Presentación"),
        styleTitle = Some(VMod(width := "4%")),
        styleCell = Some(VMod(textAlign := "justify", border := "1px solid black")), inputType = "TextArea"
      ),
      ColG_Json2(
        //field = ("package_unit_measure", JsonString("")),
        title = Some("Unidad Medida Presentacion"), styleTitle = Some(VMod(width := "5%")),
        styleCell = Some(VMod(textAlign := "center", border := "1px solid black"))),

      //ColG_Json2(field = ("prescription", JsonInt(0))),

      ColG_Json2(
        //field = ("supplied", JsonInt(0)),
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

/*** Quitar esto es solo para probar la verdadera implentación esta en BehaviorOnGrid3    */
object GetNamesAndTypes {


  /**def fields[P <: Product, L <: HList, R <: HList](a: P)(
   implicit
   gen: LabelledGeneric.Aux[P, L],
   keys: Keys.Aux[L, R],
   ts: ToTraversable.Aux[R, List, Symbol]
   ): List[(String, String)] = {
   val fieldNames = keys().toList.map(_.name)
   val values = a.productIterator.toList.map(_.toString)
   fieldNames zip values
   }

   val fieldNamesAndValues: List[(String, String)] = fields(ItemRecipe())*/

  def fields[P <: Product, L <: HList, R <: HList](a: P)(
    implicit
    gen: LabelledGeneric.Aux[P, L],
    keys: Keys.Aux[L, R],
    ts: ToTraversable.Aux[R, List, Symbol]
  ): List[(String, String)] = {
    val fieldNames = keys().toList.map(_.name)
    val values = a.productIterator.toList.map(_.toString)
    fieldNames zip values
  }

  //val fieldNamesAndValues: List[(String, String)] = fields(ItemRecipe())

  private object typeablePoly extends Poly1 {
    implicit def default[A](implicit typeable: Typeable[A]): Case.Aux[A, String] = at(_ => typeable.describe)
  }

  private object nullPoly extends Poly0 {
    implicit def default[A]: ProductCase.Aux[HNil, A] = at(null.asInstanceOf[A])
  }

  trait Cpo[T] {

    def withPrimaryKey[R <: HList, K <: HList, V <: HList, V1 <: HList](f: Seq[Symbol] => Seq[Symbol])(implicit
                                                                                                       labellGeneric: LabelledGeneric.Aux[T, R],
                                                                                                       keys: Keys.Aux[R, K],
                                                                                                       ktl: hlist.ToList[K, Symbol],
                                                                                                       values: Values.Aux[R, V],
                                                                                                       mapper: hlist.Mapper.Aux[typeablePoly.type, V, V1],
                                                                                                       fillWith: hlist.FillWith[nullPoly.type, V],
                                                                                                       vtl: hlist.ToList[V1, String]
                                                                                                       //): Cpo[T] = new Cpo[T] {
    ) = {

      //println(ktl(keys())) // List('i, 's)
      //println(vtl(mapper(fillWith()))) // List(Int, String)

      val namesFields = ktl(keys()) // List('i, 's)
      val types = vtl(mapper(fillWith())) // List(Int, String)

      val ver: List[String] = ktl(keys()).toList.map(_.name)
      val verTypes = vtl(mapper(fillWith()))
      ver.zip(verTypes)

      //Cpo.this

    }
  }

}


/*** Quitar esto es solo para probar la verdadera implentación esta en BehaviorOnGrid3    */
object GetNamesAndTypes2 {


  /**def fields[P <: Product, L <: HList, R <: HList](a: P)(
   implicit
   gen: LabelledGeneric.Aux[P, L],
   keys: Keys.Aux[L, R],
   ts: ToTraversable.Aux[R, List, Symbol]
   ): List[(String, String)] = {
   val fieldNames = keys().toList.map(_.name)
   val values = a.productIterator.toList.map(_.toString)
   fieldNames zip values
   }

   val fieldNamesAndValues: List[(String, String)] = fields(ItemRecipe())*/

  def fields[P <: Product, L <: HList, R <: HList](a: P)(
    implicit
    gen: LabelledGeneric.Aux[P, L],
    keys: Keys.Aux[L, R],
    ts: ToTraversable.Aux[R, List, Symbol]
  ): List[(String, String)] = {
    val fieldNames = keys().toList.map(_.name)
    val values = a.productIterator.toList.map(_.toString)
    fieldNames zip values
  }

  //val fieldNamesAndValues: List[(String, String)] = fields(ItemRecipe())

  private object typeablePoly extends Poly1 {
    implicit def default[A](implicit typeable: Typeable[A]): Case.Aux[A, String] = at(_ => typeable.describe)
  }

  private object nullPoly extends Poly0 {
    implicit def default[A]: ProductCase.Aux[HNil, A] = at(null.asInstanceOf[A])
  }

  trait Cpo[T] {

    def withPrimaryKey[R <: HList, K <: HList, V <: HList, V1 <: HList](f: Seq[Symbol] => Seq[Symbol])(implicit
                                                                                                       labellGeneric: LabelledGeneric.Aux[T, R],
                                                                                                       keys: Keys.Aux[R, K],
                                                                                                       ktl: hlist.ToList[K, Symbol],
                                                                                                       values: Values.Aux[R, V],
                                                                                                       mapper: hlist.Mapper.Aux[typeablePoly.type, V, V1],
                                                                                                       fillWith: hlist.FillWith[nullPoly.type, V],
                                                                                                       vtl: hlist.ToList[V1, String]
                                                                                                       //): Cpo[T] = new Cpo[T] {
    ) = {

      //println(ktl(keys())) // List('i, 's)
      //println(vtl(mapper(fillWith()))) // List(Int, String)

      val namesFields = ktl(keys()) // List('i, 's)
      val types = vtl(mapper(fillWith())) // List(Int, String)

      val ver: List[String] = ktl(keys()).toList.map(_.name)
      val verTypes = vtl(mapper(fillWith()))
      ver.zip(verTypes)

      //Cpo.this

    }

  }

}