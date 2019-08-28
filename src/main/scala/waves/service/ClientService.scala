package waves.service

import waves.matching.Match
import waves.reading.ClientsFileReading
import waves.vo.Client

object ClientService {

  def getClients: Set[Client] = ClientsFileReading("/clients.txt")

  def processOrders(clients: List[Client], orderMatches: List[Match]): List[Client] = ???
}
