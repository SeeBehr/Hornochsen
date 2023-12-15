package de.htwg.se.hornochsen.model
import de.htwg.se.hornochsen.model._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import BaseModel.{Row, Board}
import BaseModel.Player

class BoardSpec extends AnyWordSpec {
    "Board" should {
        val row1 = Row(Nummer = 1, myCards = Vector(3, 4), Filled = 2)

        "have scalable Row" in {
            row1.toString() should be("Row 1: 3, 4 filled: 2\n")
        }

        "have scalable Board" in {
            initBoard(numRows=1, numRowCards=1, deck=initDeck(1))._1.toString() should be("Board:\n\tRow 1: 1 filled: 1\n\n")
            initBoard(numRows=2, numRowCards=2, deck=initDeck(2))._1.toString() should be("Board:\n\tRow 1: 1, 0 filled: 1\n\n\tRow 2: 2, 0 filled: 1\n\n")
        }

        "take Row should be" in {
            val ret = initBoard(numRows=2, numRowCards=2, deck=initDeck(5))._1.takeRow(card=5,nummer=1) 
            ret._1.toString() should be ("Board:\n\tRow 1: 5, 0 filled: 1\n\n\tRow 2: 2, 0 filled: 1\n\n")
            ret._2 should be (1)
        }
        "add Card should be" in {
            val b =Board(rows=Vector(Row(Nummer=1,myCards=Vector(0),Filled=0)),
            playedCards=(Vector[(Int, Player)]((5, Player(Name="Patrick", Cards=Vector(),
                Ochsen=0))))).addCard(5,1)
            b.toString() should be ("Board:\n\tRow 1: 5 filled: 1\n\n")
        }
        "playedCardstoString" in {
            val b =Board(
                rows=Vector(Row(Nummer=1,myCards=Vector(0),Filled=0)),
                playedCards=(
                    Vector(((5, Player(Name="Patrick", Cards=Vector(), Ochsen=0))))))
            b.playedCardsToString should be ("\nPlayed cards: \n5, von Patrick\n")
        }
    }
}