package reading

import org.scalatest.{FlatSpec, GivenWhenThen, Inside, Matchers}
import vo.Client

class ClientsFileReadingSpec extends FlatSpec with Matchers with GivenWhenThen with Inside {

  "ClientFileReading" should "return exception when something went wrong" in {

    Given("some non-existing resource file")
    val resourceFilePath = "non-existing-file.txt"

    Then("exception should be thrown")
    an[IllegalArgumentException] shouldBe thrownBy {

      When("reading is applied to file path")
      ClientsFileReading(resourceFilePath)
    }
  }

  it should "return expected clients when applied to valid resource file" in {

    Given("an existing & valid resource file")
    val resourceFilePath = "/test-clients.txt"

    When("reading is applied to file path")
    val clients = ClientsFileReading(resourceFilePath)

    Then("result should hold expected clients")
    clients shouldEqual Set(
      Client("C1", 1000, Map("A" -> 130, "B" -> 240, "C" -> 760, "D" -> 320)),
      Client("C2", 4350, Map("A" -> 370, "B" -> 120, "C" -> 950, "D" -> 560)),
      Client("C3", 2760, Map("A" -> 100, "B" -> 200, "C" -> 300, "D" -> 400)),
      Client("C4", 5600, Map("A" -> 450, "B" -> 540, "C" -> 480, "D" -> 950))
    )
  }
}
