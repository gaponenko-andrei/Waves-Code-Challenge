package vo

case class Order(
  id: Int,
  clientName: String,
  operation: String,
  paperType: String,
  paperPrice: Int,
  papersCount: Int
)