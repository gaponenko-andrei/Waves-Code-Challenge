import vo.{Buy, Sell}

package object matching {
  type Match = (Sell, Buy)
}
