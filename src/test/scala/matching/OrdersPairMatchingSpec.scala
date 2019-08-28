package matching

import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}
import vo.Order

class OrdersPairMatchingSpec extends FlatSpec with Matchers with GivenWhenThen {

  "OrdersPairMatching" should "return Some pair of orders when they match" in {

    Given("matching pair of orders")
    val order1 = Order(0, "C1", "s", "A", 15, 20)
    val order2 = Order(1, "C2", "b", "A", 15, 20)

    When("matching is applied")
    val result = OrdersPairMatching(order1, order2)

    Then("result should be Some pair of matched orders")
    result shouldBe Some(order1, order2)
  }

  it should "return None when orders almost match, but have the same id" in {

    Given("non-matching pair of orders")
    val order1 = Order(id = 2, "C1", "s", "A", 15, 20)
    val order2 = Order(id = 2, "C2", "b", "A", 15, 20)

    When("matching is applied")
    val result = OrdersPairMatching(order1, order2)

    Then("result should be None")
    result shouldBe None
  }

  it should "return None when orders almost match, but have the same client" in {

    Given("non-matching pair of orders")
    val order1 = Order(0, clientName = "C1", "s", "A", 15, 20)
    val order2 = Order(1, clientName = "C1", "b", "A", 15, 20)

    When("matching is applied")
    val result = OrdersPairMatching(order1, order2)

    Then("result should be None")
    result shouldBe None
  }

  it should "return None when orders almost match, but have the same operation" in {

    Given("non-matching pair of orders")
    val order1 = Order(0, "C1", operation = "s", "A", 15, 20)
    val order2 = Order(1, "C2", operation = "s", "A", 15, 20)

    When("matching is applied")
    val result = OrdersPairMatching(order1, order2)

    Then("result should be None")
    result shouldBe None
  }

  it should "return None when orders almost match, but papers are different" in {

    Given("non-matching pair of orders")
    val order1 = Order(0, "C1", "s", paperType = "A", 15, 20)
    val order2 = Order(1, "C2", "b", paperType = "B", 15, 20)

    When("matching is applied")
    val result = OrdersPairMatching(order1, order2)

    Then("result should be None")
    result shouldBe None
  }

  it should "return None when orders almost match, but papers price is different" in {

    Given("non-matching pair of orders")
    val order1 = Order(0, "C1", "s", "A", paperPrice = 15, 20)
    val order2 = Order(1, "C2", "b", "A", paperPrice = 25, 20)

    When("matching is applied")
    val result = OrdersPairMatching(order1, order2)

    Then("result should be None")
    result shouldBe None
  }

  it should "return None when orders almost match, but papers count is different" in {

    Given("non-matching pair of orders")
    val order1 = Order(0, "C1", "s", "A", 15, papersCount = 20)
    val order2 = Order(1, "C2", "b", "A", 15, papersCount = 30)

    When("matching is applied")
    val result = OrdersPairMatching(order1, order2)

    Then("result should be None")
    result shouldBe None
  }
}
