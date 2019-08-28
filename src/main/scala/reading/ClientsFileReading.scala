package reading

import org.scalactic.{Bad, Good, Or}
import reading.PackageUtils.readLinesFrom
import vo._

object ClientsFileReading extends (String => Iterator[Client] Or Exception) {

  override def apply(resourcePath: String): Iterator[Client] Or Exception =
    try {
      Good(readClientsFrom(resourcePath))
    } catch {
      case ex: Exception => Bad(ex)
    }

  private def readClientsFrom(resourcePath: String): Iterator[Client] =
    readLinesFrom(resourcePath) map line2Client

  private def line2Client(line: String): Client = {
    val seq = line.split("[\\t]").toIndexedSeq
    Client(
      name = seq(0),
      balance = seq(1).toInt,
      aPapersCount = seq(2).toInt,
      bPapersCount = seq(3).toInt,
      cPapersCount = seq(4).toInt,
      dPapersCount = seq(5).toInt
    )
  }
}
