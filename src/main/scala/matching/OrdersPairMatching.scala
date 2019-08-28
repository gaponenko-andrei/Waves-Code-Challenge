package matching

import vo.Order

trait OrdersPairMatching extends ((Order, Order) => Option[(Order, Order)]) {
  def apply(first: Order, second: Order): Option[(Order, Order)]
}

object OrdersPairMatching extends OrdersPairMatching {

  override def apply(first: Order, second: Order): Option[(Order, Order)] = {
    val ordersHaveEqualValueFor = haveEqualFieldValue(first, second)(_)
    val ordersHaveDifferentValueFor = haveDifferentFieldValue(first, second)(_)

    if (ordersHaveDifferentValueFor(_.id) &&
        ordersHaveDifferentValueFor(_.clientName) &&
        ordersHaveDifferentValueFor(_.operation) &&
        ordersHaveEqualValueFor(_.paperType) &&
        ordersHaveEqualValueFor(_.paperPrice) &&
        ordersHaveEqualValueFor(_.papersCount))
      Some(first, second) else None
  }

  private def haveEqualFieldValue(i: Order, j: Order)(f: Order => Any): Boolean = f(i) == f(j)
  private def haveDifferentFieldValue(i: Order, j: Order)(f: Order => Any): Boolean = f(i) != f(j)
}
