package waves.matching

import waves.vo.{Buy, Order, Sell}

class OrdersMatching(pairMatching: OrdersPairMatching = OrdersPairMatching) extends (List[Order] => List[Match]) {

  private type Matches = List[Match]
  private type Orders = List[Order]
  private type Sells = List[Sell]
  private type Buys = List[Buy]

  override def apply(orders: Orders): Matches = {
    val (sells, buys) = separate(orders)
    performMatchingBetween(sells, buys)
  }

  private def separate(orders: Orders): (Sells, Buys) = {
    val zeroOrders: (Sells, Buys) = (Nil, Nil)
    orders.foldRight(zeroOrders) {
      case (order, (sells, buys)) => order match {
        case sell: Sell => (sell :: sells, buys)
        case buy: Buy => (sells, buy :: buys)
      }
    }
  }

  private def performMatchingBetween(sells: Sells, buys: Buys): Matches = {
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
