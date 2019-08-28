package waves.service

import waves.matching.{Match, OrdersMatching}
import waves.reading.OrdersFileReading
import waves.vo.Order

object OrderService {

  def getOrders: List[Order] = OrdersFileReading("/orders.txt").toList

  def findMatchesAmong(orders: List[Order]): List[Match] = new OrdersMatching()(orders)
}
