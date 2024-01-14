package de.htwg.se.hornochsen.controler

import de.htwg.se.hornochsen.controler
import de.htwg.se.hornochsen.controler.BaseControler.*
import de.htwg.se.hornochsen.model.*
import de.htwg.se.hornochsen.model.BaseModel.*
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import java.io.File

class ControlerSpec extends AnyWordSpec {
    val deck: InterfaceDeck = initDeck(3)
    val gameState: InterfaceGameState = initializeGame(deck=deck, numRows=1, numRowCards=2, numPlayer=2, numHandCards=1, input= Int=>"Patrick")
    val controler: InterfaceControler = Controler(gameState)
    val p1 = makePlayer(name="Patrick",cards=Vector[Int](2))
    val p2 = makePlayer(name="Patrick",cards=Vector[Int](3))
    val g = GameState(playeractive=makePlayer(), playerswaiting=Vector.empty, playersdone=Vector(p1,p2), myBoard=Board(rows=Vector(Row(Nummer=1, myCards=Vector(1,0,0,0)), Row(Nummer=2, myCards=Vector(5,0,0,0))), playedCards=Vector((2,p1),(3,p2))), RemDeck=Deck(cards=Vector.empty))
    val g2 = GameState(playeractive=makePlayer(), playerswaiting=Vector.empty, playersdone=Vector(p1,p2), myBoard=Board(rows=Vector(Row(Nummer=1, myCards=Vector(1,0,0,0)), Row(Nummer=2, myCards=Vector(5,6,7,8),Filled=4))), RemDeck=Deck(cards=Vector.empty))

    "Controler" should {

        "have a start method" in {
            var c = Controler(gameState)
            c.start(Vector("Patrick", "Sebastian"))
            c.gameState.playersWaiting(0).name should be ("Sebastian")
            c.gameState.playerActive.name should be ("Patrick")
        }

        "have a isrunning method" in {
            controler.isrunning should be (true)
        }

        "have a setrunning method" in {
            controler.setrunning(false)
            controler.isrunning should be (false)
            controler.setrunning(true)
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
            val baseC = Controler(g)
            baseC.playCard(p1,1) should be (false)
            baseC.playCard(p1,2) should be (true)
            baseC.playCard(p2,3) should be (true)   
        }

        "have a placeCard method" in {
            val baseC = Controler(g)
            baseC.placeCards()
            baseC.gameState.playersDone.length should be (0)
            baseC.gameState.playersWaiting(0) should be (p2)
            baseC.gameState.playerActive should be (p1)
            baseC.gameState.board.playedCards.length should be (0)
            baseC.gameState.board.rows(0).toString should be ("Row 1: 1, 2, 3, 0 filled: 3\n")
        }

        "have a where method" in {
            val baseC = Controler(g)
            baseC.where(baseC.gameState.board, 0) should be (-1)
            baseC.where(baseC.gameState.board, 2) should be (0)
            baseC.where(baseC.gameState.board, 3) should be (0)
            baseC.where(baseC.gameState.board, 7) should be (1)
        }

        "have a canAdd method" in {
            val baseC = Controler(g2)
            baseC.canAdd(0) should be (true)
            baseC.canAdd(1) should be (false)
        }

        "have a takeRow method" in {
            val baseC = Controler(g2)
            val (b: InterfaceBoard, o: Int) = baseC.takeRow(p1, 10, 2)
            b.rows(0).toString should be ("Row 1: 1, 0, 0, 0 filled: 1\n")
            b.rows(1).toString should be ("Row 2: 10, 0, 0, 0 filled: 1\n")
            o should be (8)
        }

        "have a undo/redo method" in {
            val baseC = Controler(g)
            val vgl1 = baseC.gameState
            baseC.playCard(p1,2)
            val vgl2 = baseC.gameState
            baseC.undo()
            baseC.gameState should be (vgl1)
            baseC.gameState should not be (vgl2)
            baseC.redo()
            baseC.gameState should not be (vgl1)
            baseC.gameState should be (vgl2)
        }

        "have a save/load method" in {
            val baseC = Controler(g)
            baseC.save
            val baseC2 = Controler(g)
            baseC2.load
            baseC.gameState should be (baseC2.gameState)
        }

        "have a initialiseGame method" in {
            val deck = initDeck(3)
            val gameState = initializeGame(deck=deck, numRows=1, numRowCards=2, numPlayer=2, numHandCards=1, input=Int=>"Patrick")
            controler.gameState should be (gameState)
        }

        "have a Object Playerfactory" in {
            val p = makePlayer("Patrick", Vector(1,2,3))
            p.name should be ("Patrick")
            p.getCards should be (Vector(1,2,3))
        }
    }
}