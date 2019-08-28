package waves

import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}
import waves.matching.Match
import waves.vo.{Buy, Client, Sell}

class MatchingOrdersProcessingSpec extends FlatSpec with Matchers with GivenWhenThen {

  "MatchingOrdersProcessing" should "modify assets of given" +
  "clients according to given pair of matching orders" in {

    Given("some clients")
    val clients = Set(
      Client("C1", 1000, Map("A" -> 130, "B" -> 240, "C" -> 760, "D" -> 320)),
      Client("C2", 4350, Map("A" -> 370, "B" -> 120, "C" -> 950, "D" -> 560))
    )

    And("pair of matching orders")
    val orders = Match(Sell(1, "C1", "A", 15, 20), Buy(2, "C2", "A", 15, 20))

    When("processing is applied")
    val result = MatchingOrdersProcessing(clients, orders)

    Then("result should be expected")
    result shouldBe Set(
      Client("C1", 1300, Map("A" -> 110, "B" -> 240, "C" -> 760, "D" -> 320)),
      Client("C2", 4050, Map("A" -> 390, "B" -> 120, "C" -> 950, "D" -> 560))
    )
  }

  it should "modify assets of given clients according to given pairs of matching orders" in {

    Given("some clients")
    val clients = Set(
      Client("C1", 1000, Map("A" -> 130, "B" -> 240, "C" -> 760, "D" -> 320)),
      Client("C2", 4350, Map("A" -> 370, "B" -> 120, "C" -> 950, "D" -> 560)),
      Client("C3", 2760, Map("A" -> 100, "B" -> 200, "C" -> 300, "D" -> 400))
    )

    And("pairs of matching orders")
    val orders = List(
      Match(Sell(1, "C1", "B", 15, 20), Buy(2, "C2", "B", 15, 20)),
      Match(Sell(3, "C2", "C", 10, 20), Buy(4, "C3", "C", 10, 20))
    )

    When("processing is applied")
    val result = MatchingOrdersProcessing(clients, orders)

    Then("result should be expected")
    result shouldBe Set(
      Client("C1", 1300, Map("A" -> 130, "B" -> 220, "C" -> 760, "D" -> 320)),
      Client("C2", 4250, Map("A" -> 370, "B" -> 140, "C" -> 930, "D" -> 560)),
      Client("C3", 2560, Map("A" -> 100, "B" -> 200, "C" -> 320, "D" -> 400))
    )
  }

  it should "throw when clients who made orders in the first place are not provided" in {

    Given("some clients")
    val clients = Set(
      Client("C3", 1000, Map("A" -> 130, "B" -> 240, "C" -> 760, "D" -> 320)),
      Client("C4", 4350, Map("A" -> 370, "B" -> 120, "C" -> 950, "D" -> 560))
    )

    And("pair of matching orders, not related to clients")
    val orders = Match(Sell(1, "C1", "A", 15, 20), Buy(2, "C2", "A", 15, 20))

    Then("exception should be thrown")
    an[NoSuchElementException] shouldBe thrownBy {

      When("processing is applied")
      MatchingOrdersProcessing(clients, orders)
    }
  }
}
