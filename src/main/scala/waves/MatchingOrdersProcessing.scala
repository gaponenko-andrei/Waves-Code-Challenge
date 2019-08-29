package waves

import waves.matching.Match
import waves.vo.{Client, Order}

import scala.annotation.tailrec
import scala.collection._

object MatchingOrdersProcessing {

  private type Clients = Set[Client]
  private type Matches = List[Match]

  def apply(clients: Clients, `match`: Match): immutable.Set[Client] =
    applyMatch(`match`, asMutableSet(clients)).toSet

  def apply(clients: Clients, matches: Matches): immutable.Set[Client] =
    applyMatches(matches, asMutableSet(clients)).toSet

  private def asMutableSet(clients: Clients): mutable.Set[Client] =
    mutable.Set.empty ++ clients

  @tailrec
  private def applyMatches(matches: Matches, clients: Clients): Clients =
    if (matches.isEmpty) clients else {
      val afterProcessing: Clients = applyMatch(matches.head, clients)
      applyMatches(matches = matches.tail, clients = afterProcessing)
    }

  private def applyMatch(`match`: Match, clients: Clients): Clients = {

    def applyOrder(order: Order, clients: Clients): Clients = {
      val ownerBeforeApplication: Client = findOrderOwner(order, clients)
      val ownerAfterApplication: Client = order(ownerBeforeApplication)
      clients - ownerBeforeApplication + ownerAfterApplication
    }

    val clientsAfterSell = applyOrder(`match`.sell, clients)
    applyOrder(`match`.buy, clientsAfterSell)
  }

  private def findOrderOwner(order: Order, clients: Set[Client]): Client =
    clients.collectFirst { case c if c.name == order.client => c }.get
}