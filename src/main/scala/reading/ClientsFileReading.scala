package reading

import reading.PackageUtils.readLinesFrom
import vo._

object ClientsFileReading extends (String => Set[Client]) {

  override def apply(resourcePath: String): Set[Client] =
    readLinesFrom(resourcePath).map(line2Client).toSet

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
