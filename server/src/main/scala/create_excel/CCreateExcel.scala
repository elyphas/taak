package scarango_context

import com.typesafe.config.{Config, ConfigFactory}

import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator

import scala.language.postfixOps
import org.apache.poi.ss.usermodel.{CellType, WorkbookFactory}

import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator
import spoiwo.model.Sheet

import types_scarango.ItemOutputs

import java.io.{File, FileOutputStream}
import scala.jdk.CollectionConverters._

class CCreateExcel {

    private val config: Config = ConfigFactory.load()

    def saveToExcel(curMonth: Int, lst: List[ItemOutputs]) = {

      val path: String = config.getString("excel.path")

      val sheet_outputs: String = config.getString("excel.sheet_outputs")

      val first_row: Int = config.getInt("excel.first_row")

      val last_row: Int = config.getInt("excel.last_row")

      val key_column: Int = config.getInt("excel.key_column")

      val baseFile: String = (curMonth - 1) + ".xls"   //BASE.xls"

      val newFile: String = curMonth + ".xls"

      val file: File = new File(path + baseFile)

      val workbook = new XSSFWorkbook(file)   //WorkbookFactory.create(file)
      val sheet = workbook.getSheet(sheet_outputs)

      val rows = List.range ( first_row, last_row )

      lst.zipWithIndex.foreach { case ( item, idx@_ ) =>
          rows.foreach { r =>
              val row = sheet.getRow(r)
              val columnDia = item.fecha.getOrElse("").substring( 0, 2 ).toInt
              val clave = row.getCell(key_column)   /**Creo es el numero de columna */
              if (clave.getStringCellValue == item.cve_articulo ) {
                  val output = row.getCell ( columnDia + 8 )
                  output.setCellValue ( item.output.getOrElse ( 0 ) )
              }
          }
      }

      val file2: FileOutputStream = new FileOutputStream(new File(path + newFile))

      workbook.write(file2)
      workbook.close()

     }


  def clearDataFile(curMonth: Int) = {

    val extFile: String = ".xls"

    val path: String = config.getString("excel.path")

    val sheet_outputs_name: String = config.getString("excel.sheet_outputs")

    val sheet_stocks_name: String = config.getString("excel.sheet_stocks")

    val columnStockPrevious: Int = config.getInt("excel.column_stock_previous")

    val columnStockCurrent: Int = config.getInt("excel.column_stock_current")

    val first_row: Int = config.getInt("excel.first_row")

    val last_row: Int = config.getInt("excel.last_row")

    val key_column: Int = config.getInt("excel.key_column")

    val baseFile: String = path + (curMonth - 1) + ".xlsx"   //extFile

    val newFile: String = path + curMonth + ".xlsx"          //extFile

    val file: File = new File(baseFile)

    val workbook = new XSSFWorkbook(file)  // WorkbookFactory.create(file)

    val rows = List.range(first_row, last_row - 5000)

    /** copy stock previous */
    val sheet_stock_previous = workbook.getSheet(sheet_stocks_name)

    val evaluator = workbook.getCreationHelper.createFormulaEvaluator()

    rows.foreach { r =>
      val row = sheet_stock_previous.getRow( r + 5 ) /** Revisar por que se tiene que sumar 5, parece que first_row tiene que ser igual a 9 */
      val cellPreviousStock = row.getCell(columnStockPrevious)
      val cellCurrentStock = row.getCell(columnStockCurrent)

      if (cellPreviousStock.getCellType == CellType.FORMULA) {
        val cellValue = evaluator.evaluate(cellPreviousStock)
        if ( cellValue.getCellType == CellType.NUMERIC)
          cellCurrentStock.setCellValue(cellPreviousStock.getNumericCellValue)
      }
    }

    /** Clear outputs */
    val sheet_outputs = workbook.getSheet(sheet_outputs_name)

    val columns = List.range ( 9, 39 )

    columns.foreach { column =>
      rows.foreach { r =>
        val row = sheet_outputs.getRow(r)
        val cell = row.getCell(column)
        if ( cell != null && cell.getCellType != CellType.BLANK) cell.setBlank()
      }
    }

    //HSSFFormulaEvaluator.evaluateAllFormulaCells(workbook)
    XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook)

    workbook.setForceFormulaRecalculation(true)

    val file2: FileOutputStream = new FileOutputStream(new File(newFile))
    workbook.write(file2)
    workbook.close()

  }
}