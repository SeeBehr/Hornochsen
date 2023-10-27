package de.htwg.se.hornochsen
import de.htwg.se.hornochsen._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class BoardSpec extends AnyWordSpec {
    "Board" should {
        val row1 = Row(nummer = 1, cards = Vector(3, 4))

        "have scalable Row" in {
            row1.toString() should be("Row 1: 3, 4")
        }

        "have scalable Board" in {
        intiBoard(1,1).toString() should be("Board:\n\tRow 1: 0\n")
        intiBoard(2,2).toString() should be("Board:\n\tRow 1: 0, 0\n\tRow 2: 0, 0\n")
        }
    }
}