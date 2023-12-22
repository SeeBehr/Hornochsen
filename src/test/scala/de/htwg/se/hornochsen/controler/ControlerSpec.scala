package de.htwg.se.hornochsen.controler

import de.htwg.se.hornochsen.*
import de.htwg.se.hornochsen.model.*
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.hornochsen.controler.BaseControler._
import de.htwg.se.hornochsen.model.BaseModel.Row

class InterfaceControlerspec extends AnyWordSpec {
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

    "have a doOp" in {
        controler2.doOp("undo","StatePlayCards").isSuccess should be (true)
        controler2.doOp("redo","StatePlayCards").isSuccess should be (true)
        controler2.doOp("asdfkl","StatePlayCards").isSuccess should be (false)
    }
}

class BaseControlerSpec extends AnyWordSpec {
    val Interfaceinitgame = initializeGame(initDeck(10),1,1,1,1,input=Int=>"Patrick")
    val initgame = Controler(Interfaceinitgame)
    val full = Controler(stateState=initgame.gameState.copy(Board=initgame.gameState.board.copy(Vector[InterfaceRow](Row(1,Vector[Int](1,2,3,4,5,6),6)))))
    "has a init method" in {
        Interfaceinitgame.board.toString should be (initBoard(1,1,initDeck(1))._1.toString)
        Interfaceinitgame.players.toString should be (Vector[InterfacePlayer](makePlayer(name="Patrick",cards=Vector[Int](2))).toString)
    }

    "has some state info" in {
        initgame.isrunning should be (true)
        initgame.gameState should be (Interfaceinitgame)
    
    }

    "has a where method" in {
        initgame.where(initgame.gameState.board, 2) should be (0)
        initgame.where(initgame.gameState.board, 0) should be (-1)
    }

    "has a canAdd method" in {
        full.canAdd(0) should be (false)
        initgame.canAdd(0) should be (false)
    }
}