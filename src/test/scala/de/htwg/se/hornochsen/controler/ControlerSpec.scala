package de.htwg.se.hornochsen.controler

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.hornochsen.controler
import controler.*
import controler.BaseControler.*
import de.htwg.se.hornochsen.model.*

class ControlerSpec extends AnyWordSpec {
    val deck = initDeck(1)
    val gameState = initializeGame(deck=deck, numRows=1, numRowCards=2, numPlayer=0, numHandCards=0, input=Int=>"Patrick")
    val controler: InterfaceControler = Controler(gameState)

    "has a isrunning method" in {
        controler.isrunning should be (true)
    }

    "has a gameState method" in {
        controler.gameState should be (gameState)
    }

    "has a doOp method" in {
        controler.doOp("undo", "StatePlayCards").isSuccess should be (true)
        controler.doOp("redo", "StatePlayCards").isSuccess should be (true)
        controler.doOp("asdfkl", "StatePlayCards").isSuccess should be (false)
    }

    "has a playCard method" in {
        val p1 = makePlayer(name="Patrick",cards=Vector[Int](2))
        val p2 = makePlayer(name="Sebastian",cards=Vector[Int](3))
        controler.playCard(p1,1, "StatePlayCard") should be (false)
        controler.playCard(p1,2, "StatePlayCard") should be (true)
        controler.playCard(p2,3, "StatePlayCard") should be (true)   
    }
}