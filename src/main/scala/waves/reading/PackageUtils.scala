package waves.reading

import scala.io.Source

private[reading] object PackageUtils {

  def readLinesFrom(resourcePath: String): Iterator[String] =
    Option(getClass.getResource(resourcePath))
      .map(Source.fromURL(_).getLines)
      .getOrElse(throw new IllegalArgumentException(
        s"Resource '$resourcePath' doesn't exist."))
}
