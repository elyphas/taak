package repositories

import outwatch_components.repositories.{Repository, RepositorySendFile}
import io.circe.generic.auto._
import org.scalajs.dom.File
import outwatch_components.mytypes.EventsDB.EventsDBResult
import shared.ArticuloStock
import outwatch_components.types.QueryCatalog
import shared.mytypes.{Institution, Inputs, ItemRecipe, Recipe, TypeDocument, User}

import instance.Instance.instanceRecipe

object Repository {

  import circe_codec.Circe_codec.{recipeEncoder, recipeDecoder, inputsEncoder, inputsDecoder}

  def apply[I, O](implicit repo: Repository[I, O]): Repository[I, O] = repo

  implicit val repoSendFile: RepositorySendFile[File, EventsDBResult] = new RepositorySendFile[File, EventsDBResult]("sendFile"){}

  implicit val repoRecipe: Repository[Recipe, Recipe] = new Repository[Recipe, Recipe]("eventRecipe", instanceRecipe.get, method = "POST"){}

  implicit val repoInstitution: Repository[Institution, Institution] = new Repository[Institution, Institution]("eventInstitution", Institution(), method = "POST"){}

  implicit val repoInput: Repository[Inputs, Inputs] = new Repository[Inputs, Inputs]("eventInputs", Inputs(), method = "POST"){}

  implicit val repoItemRecipe: Repository[ItemRecipe, ItemRecipe] = new Repository[ItemRecipe, ItemRecipe]("eventItemRecipe", ItemRecipe(), method = "POST"){}

  implicit val repoGetAllUsers: Repository[Unit, List[User]] = new Repository[Unit, List[User]]("getAllUser", List.empty[User], method = "GET"){}

  implicit val repoGetAllTypeDocs: Repository[Unit, List[TypeDocument]] = new Repository[Unit, List[TypeDocument]]("getAllTypeDocs", List.empty[TypeDocument], method = "GET"){}

  implicit val ftsRepoArticuloStock : Repository[QueryCatalog, List[ArticuloStock]] = new Repository[QueryCatalog, List[ArticuloStock]]("fts", List.empty[ArticuloStock], method = "POST"){}

  implicit val ftsRepoInstitutions : Repository[QueryCatalog, List[Institution]] = new Repository[QueryCatalog, List[Institution]]("ftsInstitutions", List.empty[Institution], method = "POST"){}

  implicit val repoGetLstItemsRecipe: Repository[Recipe, List[ItemRecipe]] = new Repository[Recipe, List[ItemRecipe]](endP = "getLstItemsRecipe", List.empty[ItemRecipe], method = "POST"){}


}