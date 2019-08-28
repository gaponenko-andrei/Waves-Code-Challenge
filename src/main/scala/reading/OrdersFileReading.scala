package reading

import org.scalactic.{Bad, Good, Or}
import reading.PackageUtils.readLinesFrom
import vo._

object OrdersFileReading extends (String => Iterator[Order] Or Exception) {

  override def apply(resourcePath: String): Iterator[Order] Or Exception =
    try {
      Good(readOrdersFrom(resourcePath))
    } catch {
      case ex: Exception => Bad(ex)
    }

  private def readOrdersFrom(resourcePath: String): Iterator[Order] =
    readLinesFrom(resourcePath).zipWithIndex map {
      case (line, id) => newOrderFrom(id, line)
    }

  private def newOrderFrom(id: Int, orderLine: String): Order = {
    val seq = orderLine.split("[\\t]").toIndexedSeq
    seq(1) match {
      case "s" => Sell(
        id = id,
        clientName = seq(0),
        paperType = seq(2),
        paperPrice = seq(3).toInt,
        papersCount = seq(4).toInt
      )
      case "b" => Buy(
        id = id,
        clientName = seq(0),
        paperType = seq(2),
        paperPrice = seq(3).toInt,
        papersCount = seq(4).toInt
      )
    }
  }
}
