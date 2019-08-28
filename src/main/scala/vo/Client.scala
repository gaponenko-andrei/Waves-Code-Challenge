package vo

case class Client(
  name: String,
  balance: Int,
  papersCount: Map[String, Int]
)