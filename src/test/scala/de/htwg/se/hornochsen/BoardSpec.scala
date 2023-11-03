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
        initBoard(1,1).toString() should be("Board:\n\tRow 1: 0\n")
        initBoard(2,2).toString() should be("Board:\n\tRow 1: 0, 0\n\tRow 2: 0, 0\n")
        }

        "take row should be" in {
            (initBoard(1,1).takeRow(5,0)) should be (Board(rows=Vector(Row(nummer=1,cards=Vector(5),filled=1))),10)
        }
        "add Cardshould be" in {
            (Board(rows=Vector(Row(nummer=1,cards=Vector(0),filled=0)),
            playedCards=(Vector[(Int, Player)]((5, Player(name="Seb",cards=Vector(),
            ochsen=0))))).addCard(5,1)) should be (Board(rows=Vector(Row(nummer=1,cards=Vector(5),filled=1))))
        }
    }
}