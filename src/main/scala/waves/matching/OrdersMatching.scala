package waves.matching

import waves.vo.{Buy, Order, Sell}

class OrdersMatching(pairMatching: OrdersPairMatching = OrdersPairMatching) extends (List[Order] => List[Match]) {

  override def apply(orders: List[Order]): List[Match] = {
    val (sells, buys) = separate(orders)
    performMatchingBetween(sells, buys)
  }

  private def separate(orders: List[Order]): (List[Sell], List[Buy]) = {
    val zeroOrders: (List[Sell], List[Buy]) = (Nil, Nil)
    orders.foldRight(zeroOrders) {
      case (order, (sells, buys)) => order match {
        case sell: Sell => (sell :: sells, buys)
        case buy: Buy => (sells, buy :: buys)
      }
    }
  }

  private def performMatchingBetween(sells: List[Sell], buys: List[Buy]): List[Match] = {
    val unmatchedBuys = buys.toBuffer
    for {
      sell <- sells
      Match(_, buy) <- findFirstMatchBetween(sell, unmatchedBuys)
    } yield {
      unmatchedBuys -= buy
      Match(sell, buy)
    }
  }

  private def findFirstMatchBetween(sell: Sell, buys: Iterable[Buy]): Option[Match] =
    buys collectFirst { case buy if pairMatching(sell, buy).isDefined => Match(sell, buy) }
}
