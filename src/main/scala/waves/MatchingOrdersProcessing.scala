package waves

import waves.matching.Match
import waves.vo.{Buy, Client, Order, Sell}

import scala.annotation.tailrec

object MatchingOrdersProcessing {

  private type Clients = Set[Client]
  private type Matches = List[Match]

  def apply(clients: Clients, orders: Matches): Clients = processMatches(clients, orders)
  def apply(clients: Clients, orders: Match): Clients = processMatch(clients, orders)

  @tailrec
  private def processMatches(clients: Clients, orders: Matches): Clients =
    if (orders.isEmpty) clients else {
      val clientsAfterProcessing = processMatch(clients, orders.head)
      processMatches(clientsAfterProcessing, orders.tail)
    }

  private def processMatch(clients: Clients, orders: Match): Clients = {
    val clientsAfterSellProcessing = process(clients, orders.sell)
    process(clientsAfterSellProcessing, orders.buy)
  }

  private def process(clients: Clients, order: Order): Clients = {
    val clientBeforeProcessing = findOrderClient(clients, order)
    val clientAfterProcessing: Client = order match {
      case sell: Sell => process(clientBeforeProcessing, sell)
      case buy: Buy => process(clientBeforeProcessing, buy)
    }
    clients - clientBeforeProcessing + clientAfterProcessing
  }

  private def process(client: Client, sell: Sell): Client = client copy(
    balance = client.balance + sell.price * sell.count,
    papersCount = {
      val before = client.papersCount(sell.paper)
      val after = before - sell.count
      client.papersCount.updated(sell.paper, after)
    }
  )

  private def process(client: Client, buy: Buy): Client = client copy(
    balance = client.balance - buy.price * buy.count,
    papersCount = {
      val before = client.papersCount(buy.paper)
      val after = before + buy.count
      client.papersCount.updated(buy.paper, after)
    }
  )

  private def findOrderClient(clients: Set[Client], order: Order): Client =
    clients.collectFirst { case c if c.name == order.client => c }.get
}
