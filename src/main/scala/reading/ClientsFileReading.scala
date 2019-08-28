package reading

import reading.PackageUtils.readLinesFrom
import vo._

object ClientsFileReading extends (String => Set[Client]) {

  override def apply(resourcePath: String): Set[Client] =
    readLinesFrom(resourcePath).map(line2Client).toSet

  private def line2Client(line: String): Client = {
    val fields = line.split("[\\t]").toIndexedSeq
    Client(
      name = fields(0),
      balance = fields(1).toInt,
      papersCount = Map(
        "A" -> fields(2).toInt,
        "B" -> fields(3).toInt,
        "C" -> fields(4).toInt,
        "D" -> fields(5).toInt
      )
    )
  }
}
