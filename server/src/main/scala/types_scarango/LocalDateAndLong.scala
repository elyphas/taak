package types_scarango

import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.time.{Instant, LocalDate, ZoneId}
import java.util.Date

trait DateAndLong {
  //private val getZoneId: ZoneId = ZoneId.of("CST", ZoneId.SHORT_IDS)

  /**protected def localDatetoLong(localDate: LocalDate): Long =
    localDate.atStartOfDay(getZoneId).toInstant.toEpochMilli*/



  /**protected def localDateFromLong(value: Long): LocalDate =
    Instant.ofEpochMilli(value).atZone(getZoneId).toLocalDate

  protected def localDateFormated(localDate: LocalDate): String = {
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    localDate.format(formatter)
  }*/

  protected def dateFormated(date: Date): String =
    (new SimpleDateFormat("dd-MM-yyyy")).format(date)

}
