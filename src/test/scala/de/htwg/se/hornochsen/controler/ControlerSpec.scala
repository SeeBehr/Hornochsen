package de.htwg.se.hornochsen.controler

import de.htwg.se.hornochsen.*
import de.htwg.se.hornochsen.model.*
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class Controlerspec extends AnyWordSpec {
    val p1 = makePlayer(name="Patrick",cards=Vector[Int](2))
    val p2 = makePlayer(name="Sebastian",cards=Vector[Int](0))
    val (b,d) = initBoard(1,6,initDeck(1))
    var gs = initGameState(allP=Vector[InterfacePlayer](p1,p2),board=b,deck=d)
    var controler1 = makeControler(gs)
    var controler2 = makeControler(gs)

    "has a playCard method" in {
        controler1.playCard(p1,1,"StatePlayCard") should be (false)
        controler1.playCard(p1,2,"StatePlayCards") should be (true)
    }

    "runns" in {
        controler2.isrunning should be (true)
    }

    "has a gameState" in {
        controler2.gameState should be (gs)
    }

    "have a Rownum" in {
        controler2.rownum(-1).isFailure should be (true)
        controler2.rownum(1).isSuccess should be (true)
    }

    "have a doOp" in {
        controler2.doOp("undo","StatePlayCards").isSuccess should be (true)
        controler2.doOp("redo","StatePlayCards").isSuccess should be (true)
        controler2.doOp("asdfkl","StatePlayCards").isSuccess should be (false)
    }
}