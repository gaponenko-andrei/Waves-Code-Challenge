package reader

import org.scalactic.{Bad, Good, Or}
import vo._

import scala.io.Source

object ClientsFileReader extends (String => Iterator[Client] Or Exception) {

  override def apply(resourcePath: String): Iterator[Client] Or Exception =
    try {
      Good(readClientsFrom(resourcePath))
    } catch {
      case ex: Exception => Bad(ex)
    }

  private def readClientsFrom(resourcePath: String): Iterator[Client] = {
    val fileStream = getClass.getResourceAsStream(resourcePath)
    val lines = Source.fromInputStream(fileStream).getLines
    lines map split2Sequence map seqElements2Client
  }

  private def split2Sequence(line: String): IndexedSeq[String] =
    line.split("[ \\t]").toIndexedSeq

  private def seqElements2Client(seq: IndexedSeq[String]) = Client(
    name = seq(0),
    balance = seq(1).toInt,
    aPapersCount = seq(2).toInt,
    bPapersCount = seq(3).toInt,
    cPapersCount = seq(4).toInt,
    dPapersCount = seq(5).toInt
  )
}
