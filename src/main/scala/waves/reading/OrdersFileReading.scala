package waves.reading

import waves.reading.PackageUtils.readLinesFrom
import waves.vo._

object OrdersFileReading extends (String => List[Order]) {

  override def apply(resourcePath: String): List[Order] =
    readLinesFrom(resourcePath).map(line2Order).toList

  private def line2Order(line: String): Order = {
    val seq = line.split("[\\t]").toIndexedSeq
    seq(1) match {
      case "s" => Sell(
        client = seq(0),
        paper = seq(2),
        price = seq(3).toInt,
        count = seq(4).toInt
      )
      case "b" => Buy(
        client = seq(0),
        paper = seq(2),
        price = seq(3).toInt,
        count = seq(4).toInt
      )
    }
  }
}
