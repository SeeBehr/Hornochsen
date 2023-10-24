package de.htwg.se.hornochsen
import de.htwg.se.hornochsen._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class HornochsenSpec extends AnyWordSpec {
  "Hornochsen" should {
    val player1 = Player(name = "Sebastian", cards = Vector(1, 2), ochsen = 0)
    val player2 = Player(name = "Patrick", cards = Vector(11, 25, 17), ochsen = 1)
    val allplayers = AllPlayers(player = Vector(player1, player2))
    val row1 = Row(nummer = 1, cards = Vector(3, 4))
    val row2 = Row(nummer = 2, cards = Vector(7, 20, 23))
    val board1 = Board(rows = Vector(row1))
    val board2 = Board(rows = Vector(row1, row2))

    "have a Player as String of form\n'name:\n\tcards: x, y\n\tOchsen: n\n'" in {
      player1.toString() should be("Sebastian:\n\tcards: 1, 2\n\tOchsen: 0\n")
      player2.toString() should be("Patrick:\n\tcards: 11, 25, 17\n\tOchsen: 1\n")
    }
    "have all Player's" in {
      allplayers.toString() should be("Sebastian:\n\tcards: 1, 2\n\tOchsen: 0\n\nPatrick:\n\tcards: 11, 25, 17\n\tOchsen: 1\n")
    }

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