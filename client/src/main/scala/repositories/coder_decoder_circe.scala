package coders_decoders_circe

import io.circe.{Decoder, HCursor}
import shared.mytypes.{TypeDocument, User}

import java.util.Date

object coder_decoder_circe {

  /**
  user: String,
  entidad: String,
  area: String,
  fiscal_period: Int,
  folio: String,
  type_document: Option[String],
  timestamp: Option[Long],
  key_item: String,
  description_item: String,
  measure_unit: String,
  presentation: Option[Int],
  pack_unit_measure: Option[String],
  prescription: Int,
  supplied: Int,
  */


  implicit val userDecoder: Decoder[User] = new Decoder[User] {
    override def apply(c: HCursor): Decoder.Result[User] =
      for {
        user <- c.get[String]("user")
      } yield User(user)
  }

  implicit val typeDocumentDecoder: Decoder[TypeDocument] = new Decoder[TypeDocument] {
    override def apply(c: HCursor): Decoder.Result[TypeDocument] =
      for {
        type_document <- c.get[String]("type_document")
      } yield TypeDocument(type_document)
  }
}
