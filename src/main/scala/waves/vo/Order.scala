package waves.vo

sealed trait Order {
  def id: Int
  def client: String
  def paper: String
  def price: Int
  def count: Int
  def processFor(owner: Client): Client
}

final case class Sell(
  id: Int,
  client: String,
  paper: String,
  price: Int,
  count: Int
) extends Order {

  override def processFor(owner: Client): Client = {
    require(owner.name == client)
    owner copy(
      balance = owner.balance + price * count,
      papersCount = {
        val before = owner.papersCount(paper)
        val after = before - count
        owner.papersCount.updated(paper, after)
      }
    )
  }
}

final case class Buy(
  id: Int,
  client: String,
  paper: String,
  price: Int,
  count: Int
) extends Order {

  override def processFor(owner: Client): Client = {
    require(owner.name == client)
    owner copy(
      balance = owner.balance - price * count,
      papersCount = {
        val before = owner.papersCount(paper)
        val after = before + count
        owner.papersCount.updated(paper, after)
      }
    )
  }
}

