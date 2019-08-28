package matching

import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}
import vo.{Buy, Sell}

class OrdersPairMatchingSpec extends FlatSpec with Matchers with GivenWhenThen {

  "OrdersPairMatching" should "return Some pair of orders when they match" in {

    Given("matching pair of orders")
    val sell = Sell(0, "C1", "A", 15, 20)
    val buy = Buy(1, "C2", "A", 15, 20)

    When("matching is applied")
    val result = OrdersPairMatching(sell, buy)

    Then("result should be Some pair of matched orders")
    result shouldBe Some(sell -> buy)
  }

  it should "return None when orders almost match, but have the same id" in {

    Given("non-matching pair of orders")
    val sell = Sell(id = 2, "C1", "A", 15, 20)
    val buy = Buy(id = 2, "C2", "A", 15, 20)

    When("matching is applied")
    val result = OrdersPairMatching(sell, buy)

    Then("result should be None")
    result shouldBe None
  }

  it should "return None when orders almost match, but have the same client" in {

    Given("non-matching pair of orders")
    val sell = Sell(0, clientName = "C1", "A", 15, 20)
    val buy = Buy(1, clientName = "C1", "A", 15, 20)

    When("matching is applied")
    val result = OrdersPairMatching(sell, buy)

    Then("result should be None")
    result shouldBe None
  }

  it should "return None when orders almost match, but papers are different" in {

    Given("non-matching pair of orders")
    val sell = Sell(0, "C1", paperType = "A", 15, 20)
    val buy = Buy(1, "C2", paperType = "B", 15, 20)

    When("matching is applied")
    val result = OrdersPairMatching(sell, buy)

    Then("result should be None")
    result shouldBe None
  }

  it should "return None when orders almost match, but papers price is different" in {

    Given("non-matching pair of orders")
    val sell = Sell(0, "C1", "A", paperPrice = 15, 20)
    val buy = Buy(1, "C2", "A", paperPrice = 25, 20)

    When("matching is applied")
    val result = OrdersPairMatching(sell, buy)

    Then("result should be None")
    result shouldBe None
  }

  it should "return None when orders almost match, but papers count is different" in {

    Given("non-matching pair of orders")
    val sell = Sell(0, "C1", "A", 15, papersCount = 20)
    val buy = Buy(1, "C2", "A", 15, papersCount = 30)

    When("matching is applied")
    val result = OrdersPairMatching(sell, buy)

    Then("result should be None")
    result shouldBe None
  }
}
