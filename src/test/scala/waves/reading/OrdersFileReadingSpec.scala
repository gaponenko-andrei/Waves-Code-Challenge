package waves.reading

import org.scalatest.{FlatSpec, GivenWhenThen, Inside, Matchers}
import waves.vo.{Buy, Sell}

class OrdersFileReadingSpec extends FlatSpec with Matchers with GivenWhenThen with Inside {

  "OrdersFileReading" should "return exception when something went wrong" in {

    Given("some non-existing resource file")
    val resourceFilePath = "non-existing-file.txt"

    Then("exception should be thrown")
    an[IllegalArgumentException] shouldBe thrownBy {

      When("waves.reading is applied to file path")
      OrdersFileReading(resourceFilePath)
    }
  }

  it should "return expected orders when applied to valid resource file" in {

    Given("an existing & valid resource file")
    val resourceFilePath = "/test-orders.txt"

    When("waves.reading is applied to file path")
    val orders = OrdersFileReading(resourceFilePath)

    Then("result should hold expected orders")
    orders.toList shouldEqual List(
      Buy(0, "C8", "C", 15, 4),
      Sell(1, "C2", "C", 14, 5),
      Sell(2, "C2", "C", 13, 2),
      Buy(3, "C9", "B", 16, 4)
    )
  }
}
