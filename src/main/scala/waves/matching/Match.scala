package waves.matching

import waves.vo.{Buy, Sell}

case class Match(sell: Sell, buy: Buy) {
  require(OrdersPairMatching.isDefinedFor(sell, buy))
}