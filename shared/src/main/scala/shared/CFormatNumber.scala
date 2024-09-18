package shared

//import scala.math.BigDecimal.RoundingMode

trait FormatNumber[A] {
  def miles(value: A): String
}

object FormatNumber {

  /********************   Better with BigDecimal    !!!!!!!!!!!!   ****************/
  implicit val dblFormatNumber: FormatNumber[Double]  = new FormatNumber[Double] {
    override def miles(value: Double): String = {
      val rounded: Double = ((math rint value * 100) / 100 )
      val indexOfDecimal: Int = rounded.toString.indexOf(".")
      val decimal = rounded.toString.substring(indexOfDecimal + 1).toInt
      val entera = rounded.toString.substring(0, indexOfDecimal)
      addComa(entera.toString) + "." + (if (decimal < 10) (decimal * 10).toString else decimal.toString)
    }
  }

  def addComa(str: String): String =
    if (str.length > 6) {
      val len = str.length
      str.substring(0, len - 6) + "," + str.substring(len - 6, (len - 3)) + "," + str.substring(len - 3, len)
    } else if (str.length > 3) {
      str.substring(0, str.length - 3) + "," + str.substring(str.length - 3, str.length)
    } else str

}

object FormatNumberSyntax {
  implicit class FormatNumberOps[A](value: A) {
    def toFormatMiles(implicit w: FormatNumber[A]): String = {
      w.miles(value)
    }
  }
}
