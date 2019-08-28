package reading

import org.scalactic.{Bad, Good}
import org.scalatest.{FlatSpec, GivenWhenThen, Inside, Matchers}
import vo.Order

class OrdersFileReadingSpec extends FlatSpec with Matchers with GivenWhenThen with Inside {

  "OrdersFileReading" should "return exception when something went wrong" in {

    Given("some non-existing resource file")
    val resourceFilePath = "non-existing-file.txt"

    When("reading is applied to file path")
    val result = OrdersFileReading(resourceFilePath)

    Then("result should hold an exception")
    result should matchPattern { case Bad(_: Exception) => }
  }

  it should "return expected orders when applied to valid resource file" in {

    Given("an existing & valid resource file")
    val resourceFilePath = "/test-orders.txt"

    When("reading is applied to file path")
    val result = OrdersFileReading(resourceFilePath)

    Then("result should hold expected orders")
    inside(result) { case Good(orders) =>
      orders.toList shouldEqual List(
        Order(0, "C8", "b", "C", 15, 4),
        Order(1, "C2", "s", "C", 14, 5),
        Order(2, "C2", "s", "C", 13, 2),
        Order(3, "C9", "b", "B", 16, 4)
      )
    }
  }
}
