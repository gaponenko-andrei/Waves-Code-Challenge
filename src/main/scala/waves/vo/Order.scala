package waves.vo

sealed trait Order {
  def id: Int
  def client: String
  def paper: String
  def price: Int
  def count: Int
}

final case class Sell(
  id: Int,
  client: String,
  paper: String,
  price: Int,
  count: Int
) extends Order


final case class Buy(
  id: Int,
  client: String,
  paper: String,
  price: Int,
  count: Int
) extends Order

