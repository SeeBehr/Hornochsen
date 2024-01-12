package de.htwg.se.hornochsen.controler

import de.htwg.se.hornochsen.controler
import de.htwg.se.hornochsen.controler.BaseControler.*
import de.htwg.se.hornochsen.model.*
import de.htwg.se.hornochsen.model.BaseModel.*
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class ControlerSpec extends AnyWordSpec {
    val deck: InterfaceDeck = initDeck(3)
    val gameState: InterfaceGameState = initializeGame(deck=deck, numRows=1, numRowCards=2, numPlayer=2, numHandCards=1, input= Int=>"Patrick")
    val controler: InterfaceControler = Controler(gameState)

    "Controler" should {
        "have a isrunning method" in {
            controler.isrunning should be (true)
        }

        "have a gameState method" in {
            controler.gameState should be (gameState)
        }

        "have a doOp method" in {
            controler.doOp("undo").isSuccess should be (true)
            controler.doOp("redo").isSuccess should be (true)
            controler.doOp("save").isSuccess should be (true)
            controler.doOp("load").isSuccess should be (true)
            controler.doOp("end").isSuccess should be (true)
            controler.doOp("asdfkl").isSuccess should be (false)
        }

        "have a playCard method" in {
            val p1 = makePlayer(name="Patrick",cards=Vector[Int](2))
            val p2 = makePlayer(name="Patrick",cards=Vector[Int](3))
            val g = GameState(playeractive=p1, playerswaiting=Vector(p2), playersdone=Vector.empty, myBoard=Board(rows=Vector(Row(Nummer=1, myCards=Vector(1,0,0,0))), playedCards=Vector.empty), RemDeck=Deck(cards=Vector.empty))
            val baseC = Controler(g)
            baseC.playCard(p1,1) should be (false)
            baseC.playCard(p1,2) should be (true)
            baseC.playCard(p2,3) should be (true)   
        }

        /*
        "have a placeCard method" in {
            val p1 = makePlayer(name="Patrick",cards=Vector[Int](2))
            val p2 = makePlayer(name="Patrick",cards=Vector[Int](3))
            val g = GameState(playeractive=makePlayer(), playerswaiting=Vector.empty, playersdone=Vector(p1,p2), myBoard=Board(rows=Vector(Row(Nummer=1, myCards=Vector(1,0,0,0))), playedCards=Vector((2,p1),(3,p2))), RemDeck=Deck(cards=Vector.empty))
            val baseC = Controler(g)
            baseC.placeCards()
            baseC.gameState.toString() should be ("GameState:\n\tPlayers waiting: \n\tPlayers done: \n\t\tName: Patrick\n\t\tCards: 2\n\t\tOchsen: 0\n\t\tName: Patrick\n\t\tCards: 3\n\t\tOchsen: 0\n\tBoard:\n\t\tRow 1: 1, 0, 0, 0 filled: 1\n\t\tRow 2: 2, 3 filled: 1\n\tPlayed cards: \n\t\tPatrick: 2\n\t\tPatrick: 3\n\tDeck:\n\t\tCards: \n")
        }
        */

        "have a initialiseGame method" in {
            val deck = initDeck(3)
            val gameState = initializeGame(deck=deck, numRows=1, numRowCards=2, numPlayer=2, numHandCards=1, input=Int=>"Patrick")
            controler.gameState should be (gameState)
        }
    }
}