package waves.matching

import waves.vo.{Buy, Order, Sell}

trait OrdersPairMatching extends ((Sell, Buy) => Option[Match]) {
  def apply(s: Sell, b: Buy): Option[Match]
}

object OrdersPairMatching extends OrdersPairMatching {

  def isDefinedFor(s: Sell, b: Buy): Boolean = areMatch(s, b)

  override def apply(s: Sell, b: Buy): Option[Match] =
    if (areMatch(s, b)) Some(Match(s, b)) else None

  private def areMatch(s: Sell, b: Buy): Boolean = {
    val ordersHaveEqualValueFor = haveEqualFieldValue(s, b)(_)
    val ordersHaveDifferentValueFor = haveDifferentFieldValue(s, b)(_)

    ordersHaveDifferentValueFor(_.client) &&
    ordersHaveEqualValueFor(_.paper) &&
    ordersHaveEqualValueFor(_.price) &&
    ordersHaveEqualValueFor(_.count)
  }

  private def haveEqualFieldValue(s: Sell, b: Buy)(f: Order => Any): Boolean = f(s) == f(b)
  private def haveDifferentFieldValue(s: Sell, b: Buy)(f: Order => Any): Boolean = f(s) != f(b)
}
