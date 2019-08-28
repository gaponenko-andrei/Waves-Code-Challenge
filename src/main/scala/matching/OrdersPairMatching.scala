package matching

import vo.{Buy, Order, Sell}

trait OrdersPairMatching extends ((Sell, Buy) => Option[Match]) {
  def apply(s: Sell, b: Buy): Option[Match]
}

object OrdersPairMatching extends OrdersPairMatching {

  override def apply(s: Sell, b: Buy): Option[Match] =
    if (areMatch(s, b)) Some(s -> b) else None

  private def areMatch(s: Sell, b: Buy): Boolean = {
    val ordersHaveEqualValueFor = haveEqualFieldValue(s, b)(_)
    val ordersHaveDifferentValueFor = haveDifferentFieldValue(s, b)(_)

    ordersHaveDifferentValueFor(_.id) &&
    ordersHaveDifferentValueFor(_.clientName) &&
    ordersHaveEqualValueFor(_.paperType) &&
    ordersHaveEqualValueFor(_.paperPrice) &&
    ordersHaveEqualValueFor(_.papersCount)
  }

  private def haveEqualFieldValue(s: Sell, b: Buy)(f: Order => Any): Boolean = f(s) == f(b)
  private def haveDifferentFieldValue(s: Sell, b: Buy)(f: Order => Any): Boolean = f(s) != f(b)
}
