package waves.matching

import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}
import waves.vo.{Buy, Sell}

/* This is a test for 'OrdersMatching' working with (using) an actual implementation of
  'OrdersPairMatching', instead of test implementations we used in 'OrdersMatchingSpec' */

class OrdersMatchingComponentSpec extends FlatSpec with Matchers with GivenWhenThen {

  "Default OrdersMatching" should "return expected matches for provided orders" in {

    Given("default orders matching")
    val matching = new OrdersMatching()

    When("waves.matching is applied to some orders")
    val matches = matching(List(
      Sell("C1", "A", 10, 20), Buy("C2", "A", 10, 20),
      Sell("C2", "B", 15, 15), Buy("C3", "B", 15, 15),
      Sell("C3", "C", 10, 10), Sell("C3", "C", 10, 10),
      Buy("C3", "D", 40, 40), Sell("C4", "D", 40, 40)
    ))

    Then("result should be expected")
    matches shouldEqual List(
      Sell("C1", "A", 10, 20) -> Buy("C2", "A", 10, 20),
      Sell("C2", "B", 15, 15) -> Buy("C3", "B", 15, 15),
      Sell("C4", "D", 40, 40) -> Buy("C3", "D", 40, 40)
    )
  }
}