package waves

import java.io.{File, PrintWriter}

import waves.matching.{Match, OrdersMatching}
import waves.reading.{ClientsFileReading, OrdersFileReading}
import waves.vo.{Client, Order}

object Application extends App {

  private def write(clients: Set[Client]): Unit = {

    def mkStringsFrom(clients: Set[Client]): List[String] =
      clients.toList sortBy (_.name) map { it =>
        s"${it.name}	${it.balance}	${it.papersCount.values mkString "\t"}\n"
      }

    new PrintWriter(new File("result.txt")) {
      private val lines = mkStringsFrom(clients)
      try lines foreach write
      finally close()
    }
  }

  val orders: List[Order] = OrdersFileReading("/orders.txt")
  val clients: Set[Client] = ClientsFileReading("/clients.txt")
  val matchingOrders: List[Match] = new OrdersMatching()(orders)
  write(clients = MatchingOrdersProcessing(clients, matchingOrders))
}