package de.htwg.se.hornochsen.model

import de.htwg.se.hornochsen.model._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import BaseModel.{Row, Board}
import BaseModel.Player
import play.api.libs.json.*

class BoardSpec extends AnyWordSpec {
    val row1 = Row(Nummer = 1, myCards = Vector(3, 4), Filled = 2)
    val board1 = Board(rows = Vector(row1), playedCards = Vector.empty)
    "Row" should {

        "have a nummer" in {
            row1.nummer should be(1)
        }

        "have a cards" in {
            row1.cards should be(Vector(3, 4))
        }

        "have a filled" in {
            row1.filled should be(2)
        }

        "have a value" in {
            row1.value should be(2)
        }

        "have a copy" in {
            row1.copy().toString() should be("Row 1: 3, 4 filled: 2\n")
        }

        "have a saveTo/loadFromXML" in {
            val xml = row1.saveToXML()
            val r = row1.loadFromXML(xml)
            r.toString() should be(row1.toString)
        }

        "have a saveTo/loadFromJson" in {
            val js = row1.saveToJson()
            val r = row1.loadFromJson(js)
            r.toString() should be(row1.toString)
        }
    }

    "Board" should {

        "have a rows" in {
            board1.rows should be(Vector(row1))
        }

        "have a playedCards" in {
            board1.playedCards should be(Vector.empty)
        }
        
        "havea addCard should be" in {
            val b =Board(rows=Vector(Row(Nummer=1,myCards=Vector(0),Filled=0)),
            playedCards=(Vector[(Int, Player)]((5, Player(Name="Patrick", Cards=Vector(),
                Ochsen=0))))).addCard(5,1)
            b.toString() should be ("Board:\n\tRow 1: 5 filled: 1\n\n")
        }

        "have a takeRow should be" in {
            val ret = initBoard(numRows=2, numRowCards=2, deck=initDeck(5))._1.takeRow(card=5,nummer=1) 
            ret._1.toString() should be ("Board:\n\tRow 1: 5, 0 filled: 1\n\n\tRow 2: 2, 0 filled: 1\n\n")
            ret._2 should be (1)
        }

        "have a copy" in {
            board1.copy(myRows=Vector(board1.rows(0).copy(cards=Vector(1), Filled=1))).toString should be("Board:\n\tRow 1: 1 filled: 1\n\n")
        }

        "have a playedCardsToString" in {
            board1.playedCardsToString should be("\nPlayed cards: \n")
        }

        "have a saveTo/loadFromXML" in {
            val xml = board1.saveToXML()
            val b = board1.loadFromXML(xml)
            b.toString() should be(board1.toString)
        }

        "have a saveTo/loadFromJson" in {
            val js = board1.saveToJson
            val b = board1.loadFromJson(js)
            b.toString() should be(board1.toString)
        }
    }
    "The interface" should {

        "have a makeDummyBoard" in {
            makeDummyBoard().toString() should be("Board:\n\t\n")
        }

        "have a makeDummyRow" in {
            makeDummyRow().toString() should be("Row 0:  filled: 0\n")
        }

        "have a init Board" in {

            initBoard(numRows=1, numRowCards=1, deck=initDeck(1))._1.toString() should be("Board:\n\tRow 1: 1 filled: 1\n\n")
        }
    }
}