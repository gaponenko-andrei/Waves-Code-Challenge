package vo

sealed trait Order {
  def id: Int
  def clientName: String
  def paperType: String
  def paperPrice: Int
  def papersCount: Int
}

final case class Sell(
  id: Int,
  clientName: String,
  paperType: String,
  paperPrice: Int,
  papersCount: Int
) extends Order


final case class Buy(
  id: Int,
  clientName: String,
  paperType: String,
  paperPrice: Int,
  papersCount: Int
) extends Order

