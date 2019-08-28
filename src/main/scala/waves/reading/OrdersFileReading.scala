package waves.reading

import waves.reading.PackageUtils.readLinesFrom
import waves.vo._

object OrdersFileReading extends (String => Iterator[Order]) {

  override def apply(resourcePath: String): Iterator[Order] =
    readLinesFrom(resourcePath).zipWithIndex map {
      case (line, id) => newOrderFrom(id, line)
    }

  private def newOrderFrom(id: Int, orderLine: String): Order = {
    val seq = orderLine.split("[\\t]").toIndexedSeq
    seq(1) match {
      case "s" => Sell(
        id = id,
        client = seq(0),
        paper = seq(2),
        price = seq(3).toInt,
        count = seq(4).toInt
      )
      case "b" => Buy(
        id = id,
        client = seq(0),
        paper = seq(2),
        price = seq(3).toInt,
        count = seq(4).toInt
      )
    }
  }
}
