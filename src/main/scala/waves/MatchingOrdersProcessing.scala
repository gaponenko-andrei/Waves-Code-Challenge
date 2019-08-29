package waves

import waves.matching.Match
import waves.vo.{Buy, Client, Order, Sell}

import scala.annotation.tailrec
import scala.collection._

object MatchingOrdersProcessing {

  private type Clients = Set[Client]
  private type Matches = List[Match]

  def apply(clients: Clients, orders: Match): immutable.Set[Client] =
    processMatch(orders, mutableSetOf(clients)).toSet

  def apply(clients: Clients, orders: Matches): immutable.Set[Client] =
    processMatches(orders, mutableSetOf(clients)).toSet

  private def mutableSetOf(clients: Clients): mutable.Set[Client] =
    mutable.Set.empty ++ clients

  @tailrec
  private def processMatches(matches: Matches, clients: Clients): Clients =
    if (matches.isEmpty) clients else {
      val clientsAfterProcessing = processMatch(matches.head, clients)
      processMatches(matches.tail, clientsAfterProcessing)
    }

  private def processMatch(orders: Match, clients: Clients): Clients = {

    def process(order: Order, clients: Clients): Clients = {
      val ownerBefore: Client = findOrderOwner(order, clients)
      val ownerAfter: Client = order processFor ownerBefore
      clients - ownerBefore + ownerAfter
    }

    val clientsAfterSell = process(orders.sell, clients)
    process(orders.buy, clientsAfterSell)
  }

  private def findOrderOwner(order: Order, clients: Set[Client]): Client =
    clients.collectFirst { case c if c.name == order.client => c }.get
}