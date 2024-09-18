package instance

import outwatch_components.Instance
import shared.mytypes.{Inputs, Institution, ItemRecipe, Recipe, User}
//import java.time.{LocalDate, ZoneId}
import java.util.Date
import scala.scalajs.js

object Instance {

  def apply[A](implicit instance: Instance[A]): A = instance.get

  //val today: LocalDate = LocalDate.now(ZoneId.of("CST", ZoneId.SHORT_IDS))
  //val zoneId: ZoneId = //ZoneId.systemDefault()

  //val zoneId: ZoneId =   ZoneId.of("CST", ZoneId.SHORT_IDS)
  //val epoch: Long = today.atStartOfDay(zoneId).toInstant.toEpochMilli

  val todayJs: js.Date = new js.Date()
  val todayJvm: Date = new Date()

  implicit val instanceRecipe: Instance[Recipe] = new Instance[Recipe] {
        def get: Recipe = {

          Recipe(
            date = todayJvm,
            year = todayJs.getFullYear().toInt,
            month = todayJs.getMonth().toInt,
            day = todayJs.getDay().toInt,
            //date = epoch,
            //fiscal_period = today.getYear,    //today.getYear, // date.getYear + 1900, // 2023,
            id_institution = "FARMACIA",
            folio = "VER",
            user = "JCESAR",
            type_document = "CONSULTA",
            /**user = "ESTEBAN",
            folio = "31102022-ESTEBAN",
            type_document = "CONCENTRADO",*/
          )
        }
  }

  implicit val instanceInput: Instance[Inputs] = new Instance[Inputs] {
        def get: Inputs = Inputs()
  }

  implicit val instanceInstitution: Instance[Institution] = new Instance[Institution] {
    def get: Institution = Institution()
  }

  implicit val instanceUser: Instance[List[User]] = new Instance[List[User]] {
    def get: List[User] = List.empty[User]
  }

  implicit val instanceItemRecipe: Instance[ItemRecipe] = new Instance[ItemRecipe] {
    def get: ItemRecipe = ItemRecipe()
  }

}