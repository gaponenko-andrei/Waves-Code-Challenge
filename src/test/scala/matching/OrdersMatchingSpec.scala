package matching

import org.scalatest.{FlatSpec, GivenWhenThen, Inside, Matchers}
import vo.{Buy, Sell}

import scala.util.Random

class OrdersMatchingSpec extends FlatSpec with Matchers with GivenWhenThen with Inside {

  "OrdersMatching" should "return a list of matches" in {

    Given("pair matching always returning Some pair of matched orders")
    val pairMatching: OrdersPairMatching = (s, b) => Some(s -> b)

    And("orders matching using it")
    val matching = new OrdersMatching(pairMatching)

    And("some orders to match")
    val buy = someBuyOrder
    val sell = someSellOrder
    val orders = List(sell, buy)

    When("matching is applied")
    val matches = matching(orders)

    Then("result should be expected")
    matches shouldBe List(sell -> buy)
  }

  it should "match orders based on their positions in a given list" in {

    Given("pair matching always returning Some pair of matched orders")
    val pairMatching: OrdersPairMatching = (s, b) => Some(s -> b)

    And("orders matching using it")
    val matching = new OrdersMatching(pairMatching)

    And("some orders to match")
    val (b1, b2, b3, b4) = (someBuyOrder, someBuyOrder, someBuyOrder, someBuyOrder)
    val (s1, s2, s3, s4) = (someSellOrder, someSellOrder, someSellOrder, someSellOrder)
    val orders = List(s3, s4, s2, s1, b1, b3, b2, b4)

    When("matching is applied")
    val matches = matching(orders)

    Then("result should be expected")
    matches shouldBe List(s3 -> b1, s4 -> b3, s2 -> b2, s1 -> b4)
  }

  it should "return an expected number of matches" in {

    Given("pair matching always returning Some pair of matched orders")
    val pairMatching: OrdersPairMatching = (s, b) => Some(s -> b)

    And("orders matching using it")
    val matching = new OrdersMatching(pairMatching)

    When("matching is applied to list of 4 sell & buy orders")
    val matches = matching(List(
      someSellOrder, someSellOrder,
      someSellOrder, someSellOrder,
      someBuyOrder, someBuyOrder,
      someBuyOrder, someBuyOrder
    ))

    Then("result should have 4 matches")
    matches.size shouldBe 4
  }

  it should "return an empty list, when pair matching always returning None" in {

    Given("pair matching always returning None")
    val pairMatching: OrdersPairMatching = (s, b) => None

    And("orders matching using it")
    val matching = new OrdersMatching(pairMatching)

    When("matching is applied to list of 4 sell & buy orders")
    val matches = matching(List(
      someSellOrder, someSellOrder,
      someSellOrder, someSellOrder,
      someBuyOrder, someBuyOrder,
      someBuyOrder, someBuyOrder
    ))

    Then("result should be empty")
    matches shouldBe empty
  }

  it should "return an empty list, when there are no orders to match" in {

    Given("pair matching always returning Some pair of matched orders")
    val pairMatching: OrdersPairMatching = (s, b) => Some(s -> b)

    And("orders matching using it")
    val matching = new OrdersMatching(pairMatching)

    When("matching is applied to empty list of orders")
    val matches = matching(Nil)

    Then("result should be emtpy")
    matches shouldBe empty
  }

  it should "return an empty list, when only Sell orders provided" in {

    Given("pair matching always returning Some pair of matched orders")
    val pairMatching: OrdersPairMatching = (s, b) => Some(s -> b)

    And("orders matching using it")
    val matching = new OrdersMatching(pairMatching)

    When("matching is applied to list of Buy orders")
    val matches = matching(List(
      someSellOrder, someSellOrder,
      someSellOrder, someSellOrder
    ))

    Then("result should be emtpy")
    matches shouldBe empty
  }

  it should "return an empty list, when only Buy orders provided" in {

    Given("pair matching always returning Some pair of matched orders")
    val pairMatching: OrdersPairMatching = (s, b) => Some(s -> b)

    And("orders matching using it")
    val matching = new OrdersMatching(pairMatching)

    When("matching is applied to list of Buy orders")
    val matches = matching(List(
      someBuyOrder, someBuyOrder,
      someBuyOrder, someBuyOrder
    ))

    Then("result should be emtpy")
    matches shouldBe empty
  }

  // Utils

  private def someBuyOrder = Buy(
    id = anyInt,
    clientName = "_",
    paperType = "_",
    paperPrice = anyInt,
    papersCount = anyInt
  )

  private def someSellOrder = Sell(
    id = anyInt,
    clientName = "_",
    paperType = "_",
    paperPrice = anyInt,
    papersCount = anyInt
  )

  private def anyInt: Int = Random.nextInt()
}
