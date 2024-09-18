package test_create_excel

//import cats.data.EitherT
//import cats._
import cats.effect.IO
//import org.http4s.server.staticcontent.FileService.Config
import org.scalatest.freespec.AsyncFreeSpec
//import cats.implicits._
import scarango_context.CCreateExcel

import cats.effect.testing.scalatest.AsyncIOSpec
//import scala.concurrent.ExecutionContext.global
//import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.Tag
import scarango_context.COutputs

import com.typesafe.config.{Config, ConfigFactory}

import java.text.DateFormatSymbols

//import store_movements.COutputs
//import types_scarango.{ItemOutputs, Recipe}

//import scala.concurrent.Future
//import scala.util.{Failure, Success}

import com.typesafe.config.ConfigFactory

object SaveOutputToExcelSpec extends Tag("SaveOutputToExcelSpec")

//class COutputsSpec extends AsyncFlatSpec with Matchers {
class COutputsSpec extends AsyncFreeSpec with AsyncIOSpec with Matchers {

  private val config: Config = ConfigFactory.load()

  "Get the outpus shows the outputs" taggedAs (SaveOutputToExcelSpec) in {

      implicit val ec: scala.concurrent.ExecutionContext = scala.concurrent.ExecutionContext.global

      val outputs = new COutputs
      val createExcel = new CCreateExcel

      val year: Int = config.getInt("excel.year")          //2022
      val currentMonth: Int = config.getInt("excel.month")    //8    /***   Mes del que se va ha ser el corte.        */

      //val monthFormat = new DateFormatSymbols().getMonths()( month - 1)

      val res = for {
          //xx <- outputs.getDetailsRecipe ( year, currentMonth )
          _ <- IO(createExcel.clearDataFile(currentMonth))
          //_ <- IO(createExcel.saveToExcel ( currentMonth, xx ))
      } yield {
        println("Termino bien")
        "Termino bien"
      }

    res.redeem (
          recover => {
              println(s"$recover X " * 80)
              //(println(recover.getMessage))
              assert(false)
          },
          success => {
              println(s" $success Y " * 80)
              assert(true)
          }
      )
  }
}
