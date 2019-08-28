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
      Client("C1", 1000, 130, 240, 760, 320),
      Client("C2", 4350, 370, 120, 950, 560),
      Client("C3", 2760, 100, 200, 300, 400),
      Client("C4", 5600, 450, 540, 480, 950)
    )
  }
}
