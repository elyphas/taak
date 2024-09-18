package shared

case class Stock (
       entidad: String,
       area: String,
       key_item: String,
       description_item: String,
       measure_unit: String,
       presentation: Option[Int],
       pack_unit_measure: Option[String],
       partida: Option[String],
       max: Option[Int],
       min: Option[Int],
       stock: Int
       //amount: Int
)

object Stock {
  def apply(
                 entidad: String = "1441",
                 area: String = "FARMACIA",
                 key_item: String = "",
                 description_item: String = "",
                 measure_unit: String = "",
                 presentation: Option[Int] = None,
                 pack_unit_measure: Option[String] = None,
                 partida: Option[String] = None,
                 max: Option[Int] = None,
                 min: Option[Int] = None,
                 stock: Int = 0   //amount: Int = 0
           ): Stock = new Stock(
                            entidad = entidad,
                            area = area,
                            key_item = key_item,
                            description_item = description_item,
                            measure_unit = measure_unit,
                            presentation = presentation,
                            pack_unit_measure = pack_unit_measure,
                            partida = partida,
                            max = max,
                            min = min,
                            stock = stock  //amount = amount
                )
}