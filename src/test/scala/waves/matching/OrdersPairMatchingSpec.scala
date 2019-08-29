package waves.matching

import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}
import waves.vo.{Buy, Sell}

class OrdersPairMatchingSpec extends FlatSpec with Matchers with GivenWhenThen {

  "OrdersPairMatching" should "return Some pair of orders when they match" in {

    Given("waves.matching pair of orders")
    val sell = Sell("C1", "A", 15, 20)
    val buy = Buy("C2", "A", 15, 20)

    When("waves.matching is applied")
    val result = OrdersPairMatching(sell, buy)

    Then("result should be Some pair of matched orders")
    result shouldBe Some(sell -> buy)
  }

  it should "return None when orders almost match, but have the same client" in {

    Given("non-waves.matching pair of orders")
    val sell = Sell(client = "C1", "A", 15, 20)
    val buy = Buy(client = "C1", "A", 15, 20)

    When("waves.matching is applied")
    val result = OrdersPairMatching(sell, buy)

    Then("result should be None")
    result shouldBe None
  }

  it should "return None when orders almost match, but papers are different" in {

    Given("non-waves.matching pair of orders")
    val sell = Sell("C1", paper = "A", 15, 20)
    val buy = Buy("C2", paper = "B", 15, 20)

    When("waves.matching is applied")
    val result = OrdersPairMatching(sell, buy)

    Then("result should be None")
    result shouldBe None
  }

  it should "return None when orders almost match, but papers price is different" in {

    Given("non-waves.matching pair of orders")
    val sell = Sell("C1", "A", price = 15, 20)
    val buy = Buy("C2", "A", price = 25, 20)

    When("waves.matching is applied")
    val result = OrdersPairMatching(sell, buy)

    Then("result should be None")
    result shouldBe None
  }

  it should "return None when orders almost match, but papers count is different" in {

    Given("non-waves.matching pair of orders")
    val sell = Sell("C1", "A", 15, count = 20)
    val buy = Buy("C2", "A", 15, count = 30)

    When("waves.matching is applied")
    val result = OrdersPairMatching(sell, buy)

    Then("result should be None")
    result shouldBe None
  }
}
