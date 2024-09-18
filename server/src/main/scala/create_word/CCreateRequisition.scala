package create_word

import org.apache.poi.openxml4j.opc.OPCPackage
import org.apache.poi.xwpf.usermodel.{ParagraphAlignment, XWPFDocument, XWPFFooter, XWPFParagraph, XWPFRun, XWPFTable, XWPFTableCell, XWPFTableRow}

import java.io.{File, FileInputStream, FileOutputStream, OutputStream}
import com.typesafe.config.{Config, ConfigFactory}
import org.apache.poi.wp.usermodel.HeaderFooterType
import org.openxmlformats.schemas.wordprocessingml.x2006.main.{CTPageMar, CTSectPr}

import java.math.BigInteger

case class Rows(value: Int) extends AnyVal
case class Columns(value: Int) extends AnyVal

case class Dimesion(rows: Rows, columns: Columns)

class CCreateRequisition(file: String, newFile: String) {

  private val config: Config = ConfigFactory.load()

  private val path: String = config.getString("word.requisition.path")

  private val doc: XWPFDocument = new XWPFDocument(new FileInputStream(path + file))

  def create: Boolean = {

    addHeader(doc)

    addFooter(doc)

    margins(doc)

    val table: XWPFTable = createTableRequisition(doc, Dimesion(Rows(1), Columns(4)), List(1000, 5000, 2000, 2000))

    val headersText: List[String] = List("Clave", "Descripcion", "Presentación", "Cantidad")

    addRowTitleInTable(table, headersText)

    val valuesRow: List[String] = List("0104", "Paracetamol", "CAJA", "100")
    addRowInTable(table, valuesRow)

    /**val paragraph = doc.createParagraph()
    val splitTables = paragraph.createRun()
    splitTables.addBreak()*/

    createTableRequisitionSubtotales(doc)

    val newRequisition: OutputStream = new FileOutputStream(new File(path + newFile))

    doc.write(newRequisition)

    doc.close()
    true
  }

  private def margins(doc: XWPFDocument): Unit = {
    var sectPr: CTSectPr = doc.getDocument.getBody.getSectPr

    if (sectPr == null)
      sectPr = doc.getDocument.getBody.addNewSectPr()

    var pageMar: CTPageMar = sectPr.getPgMar

    if (pageMar == null) pageMar = sectPr.addNewPgMar()

    //pageMar.setLeft(BigInteger.valueOf(720)) //720 TWentieths of an Inch Point (Twips) = 720/20 = 36 pt = 36/72 = 0.5"
    //pageMar.setRight(BigInteger.valueOf(720))
    //pageMar.setTop(BigInteger.valueOf(1440)) //1440 Twips = 1440/20 = 72 pt = 72/72 = 1"

    pageMar.setFooter(BigInteger.valueOf(720)) //0.5" footer margin

    val notPrintableBottomPageRange = (0.038888 * 72 * 20).toLong //0.038888" gap for non printable bottom page range

    pageMar.setBottom(BigInteger.valueOf(1152 + 720 + notPrintableBottomPageRange)) //1152 Twips = 1152/20/72 = 0.8"

    //bottom margin = 0.8" footer spacing + 0.5" footer margin + 0.038888" gap for non printable bottom page range

  }

  private def addHeader(doc: XWPFDocument): Unit = {
    val header = doc.getHeaderList
    val head1 = header.get(0)
    val paragraph = head1.getParagraphs.get(0)
    val addTextToParagraph = paragraph.createRun()
    addTextToParagraph.addBreak()
    addTextToParagraph.setText("UNIDAD MEDICA")
  }

  private def addFooter(doc: XWPFDocument): Unit = {

    val footer: XWPFFooter = doc.createFooter(HeaderFooterType.DEFAULT)
    val table: XWPFTable = footer.getBodyElements.get(0).asInstanceOf[XWPFTable]

    val row1 = table.getRow(0)

    val atentamentLst: List[Int] = List(1,2,3)

    atentamentLst.foreach{ i =>
      val cel = row1.getCell( i - 1 )
      cel.setText("ATENTAMENTE")
      cel.getParagraphs.get(0).setAlignment(ParagraphAlignment.CENTER)
      cel.addParagraph()
      cel.addParagraph()
    }

    val signers: List[String] = List("JEFE 1", "JEFE 2", "JEFE 3")

    val row2 = table.getRow(1)

    signers.zipWithIndex.foreach { case (p, i) =>
      val cel21 = row2.getCell(i)
      cel21.addParagraph()
      val runParLine21 = cel21.getParagraphs.get(1).createRun
      runParLine21.setText("_______________________")
      runParLine21.addBreak()

      cel21.addParagraph()
      val runParPerson = cel21.getParagraphs.get(1).createRun
      runParPerson.setText(p)

      cel21.getParagraphs.get(1).setAlignment(ParagraphAlignment.CENTER)
    }

  }

  private def createTableRequisition(doc: XWPFDocument, dimension: Dimesion, widths: List[Int]): XWPFTable = {
    val table: XWPFTable = doc.createTable(dimension.rows.value, dimension.columns.value)
    table.getCTTbl.addNewTblGrid()
    widths.foreach( x => table.getCTTbl.getTblGrid.addNewGridCol().setW(BigInteger.valueOf(x)))
    table
  }

  private def addRowTitleInTable(table: XWPFTable, headersText: List[String]): Unit = {
    val row1: XWPFTableRow = table.getRow(0)
    headersText.zipWithIndex.foreach { case (text: String, id: Int) =>
      row1.getCell(id).setText(text)
      cellBoldCenter(row1.getCell(id))
    }
  }

  private def cellBoldCenter(cell: XWPFTableCell): Unit = {
        val paragraph: XWPFParagraph = cell.getParagraphs.get(0)
        paragraph.getRuns.get(0).setBold(true)
        paragraph.setAlignment(ParagraphAlignment.CENTER)
  }

  private def addRowInTable(table: XWPFTable, rowContent: List[String]): Unit = {
    val newRow: XWPFTableRow = table.createRow
    rowContent.zipWithIndex.foreach { case ( text, id ) => newRow.getCell ( id ).setText ( text ) }
  }

  private def createTableRequisitionSubtotales(doc: XWPFDocument): XWPFTable = {
    val table: XWPFTable = doc.createTable(1, 2)
    table.getCTTbl.addNewTblGrid()
    table.getCTTbl.getTblGrid.addNewGridCol().setW(BigInteger.valueOf(6000))
    table.getCTTbl.getTblGrid.addNewGridCol().setW(BigInteger.valueOf(4000))

    val newRow: XWPFTableRow = table.getRow(0)
    newRow.getCell(0).setText ( "Subtotal" )
    newRow.getCell(0).getParagraphs.get(0).setAlignment(ParagraphAlignment.RIGHT)
    newRow.getCell(1).setText ( "$ 1,000.00" )
    newRow.getCell(1).getParagraphs.get(0).setAlignment(ParagraphAlignment.RIGHT)

    table

  }


}
