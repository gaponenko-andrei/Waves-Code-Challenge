package reading

import scala.io.Source

private[reading] object PackageUtils {

  def readLinesFrom(resourcePath: String): Iterator[String] = {
    val fileStream = getClass.getResourceAsStream(resourcePath)
    Source.fromInputStream(fileStream).getLines
  }
}
