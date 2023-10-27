package de.htwg.se.hornochsen
import de.htwg.se.hornochsen._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class BoardSpec extends AnyWordSpec {
    "Board" should {
        val row1 = Row(nummer = 1, cards = Vector(3, 4))
        val row2 = Row(nummer = 2, cards = Vector(7, 20, 23))
        val board1 = Board(rows = Vector(row1))
        val board2 = Board(rows = Vector(row1, row2))

        "have scalable Row" in {
            row1.toString() should be("Row 1: 3, 4")
            row2.toString() should be("Row 2: 7, 20, 23")
        }

        "have scalable Board" in {
        board1.toString() should be("Board:\n\tRow 1: 3, 4\n")
        board2.toString() should be("Board:\n\tRow 1: 3, 4\n\tRow 2: 7, 20, 23\n")
        }
    }
}