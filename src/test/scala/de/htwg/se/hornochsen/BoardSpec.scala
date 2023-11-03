package de.htwg.se.hornochsen
import de.htwg.se.hornochsen._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import model.{Row, Board, initBoard}
import model.Player

class BoardSpec extends AnyWordSpec {
    "Board" should {
        val row1 = Row(nummer = 1, cards = Vector(3, 4), filled = 2)

        "have scalable Row" in {
            row1.toString() should be("Row 1: 3, 4 filled: 2\n")
        }

        "have scalable Board" in {
        initBoard(1,1).toString() should be("Board:\n\tRow 1: 0 filled: 1\n\nPlayed cards: \n")
        initBoard(2,2).toString() should be("Board:\n\tRow 1: 0, 0 filled: 1\n\n\tRow 2: 0, 0 filled: 1\n\nPlayed cards: \n")
        }

        "take Row should be" in {
            val ret = initBoard(2,2).takeRow(5,0) 
            ret._1.toString() should be ("Board:\n\tRow 1: 5, 0 filled: 1\n\n\tRow 2: 0, 0 filled: 1\n\nPlayed cards: \n")
            ret._2 should be (20)
        }
        "add Card should be" in {
            val b =Board(rows=Vector(Row(nummer=1,cards=Vector(0),filled=0)),
            playedCards=(Vector[(Int, Player)]((5, Player(name="Seb",cards=Vector(),
            ochsen=0))))).addCard(5,1)
            b.toString() should be ("Board:\n\tRow 1: 5 filled: 1\n\nPlayed cards: \n")
        }
    }
}