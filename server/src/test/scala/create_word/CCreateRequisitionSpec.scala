package test_create_excel

import org.scalatest.freespec.AsyncFreeSpec
import create_word.CCreateRequisition

import cats.effect.testing.scalatest.AsyncIOSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.Tag

object CreateRequisitionSpec extends Tag("CreateRequisitionSpec")

class CCreateRequisitionSpec extends AsyncFreeSpec with AsyncIOSpec with Matchers {

  "create requisition file microsoft" taggedAs (CreateRequisitionSpec) in {

    val fileNameRequision: String = "requisition.docx"
    val newFileRequisition: String = "requision-020224.docx"

    val requisition: CCreateRequisition = new CCreateRequisition(fileNameRequision, newFileRequisition)

    requisition.create

    assert(true)

  }
}
