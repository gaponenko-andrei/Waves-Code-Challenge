package matching

import vo.{Buy, Order, Sell}

class OrdersMatching(pairMatching: OrdersPairMatching = OrdersPairMatching) extends (List[Order] => List[Match]) {

  override def apply(orders: List[Order]): List[Match] = {
    val (sellOrders, buyOrders) = separate(orders)
    performMatchingBetween(sellOrders, buyOrders)
  }

  private def separate(orders: List[Order]): (List[Sell], List[Buy]) = {
    val zeroOrders: (List[Sell], List[Buy]) = (Nil, Nil)
    orders.foldRight(zeroOrders) {
      case (order, (sellOrders, buyOrders)) => order match {
        case sell: Sell => (sell :: sellOrders, buyOrders)
        case buy: Buy => (sellOrders, buy :: buyOrders)
      }
    }
  }

  private def performMatchingBetween(sells: List[Sell], buys: List[Buy]): List[Match] = {
    val unmatchedBuys = buys.toBuffer
    for {
      sell <- sells
      (_, buy) <- findFirstMatchBetween(sell, unmatchedBuys)
    } yield {
      unmatchedBuys -= buy
      (sell, buy)
    }
  }

  private def findFirstMatchBetween(sell: Sell, buys: Iterable[Buy]): Option[Match] =
    buys collectFirst { case buy if pairMatching(sell, buy).isDefined => sell -> buy }
}
