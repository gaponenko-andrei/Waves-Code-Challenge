package matching

import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}
import vo.{Buy, Sell}

/* This is a test for 'OrdersMatching' working with (using) an actual implementation of
  'OrdersPairMatching', instead of test implementations we used in 'OrdersMatchingSpec' */

class OrdersMatchingComponentSpec extends FlatSpec with Matchers with GivenWhenThen {

  "Default OrdersMatching" should "return expected matches for provided orders" in {

    Given("default orders matching")
    val matching = new OrdersMatching()

    When("matching is applied to some orders")
    val matches = matching(List(
      Sell(0, "C1", "A", 10, 20), Buy(1, "C2", "A", 10, 20),
      Sell(2, "C2", "B", 15, 15), Buy(3, "C3", "B", 15, 15),
      Sell(4, "C3", "C", 10, 10), Sell(5, "C3", "C", 10, 10),
      Buy(6, "C3", "D", 40, 40), Sell(7, "C4", "D", 40, 40)
    ))

    Then("result should be expected")
    matches shouldEqual List(
      Sell(0, "C1", "A", 10, 20) -> Buy(1, "C2", "A", 10, 20),
      Sell(2, "C2", "B", 15, 15) -> Buy(3, "C3", "B", 15, 15),
      Sell(7, "C4", "D", 40, 40) -> Buy(6, "C3", "D", 40, 40)
    )
  }
}
