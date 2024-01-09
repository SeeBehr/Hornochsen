package de.htwg.se.hornochsen.aview

import de.htwg.se.hornochsen.controler.*
import de.htwg.se.hornochsen.model.*
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.hornochsen.controler.BaseControler._

class TUISpec extends AnyWordSpec {
    "The TUI" should {
        val p1 = makePlayer(name="Patrick",cards=Vector[Int](2))
        val p2 = makePlayer(name="Patrick",cards=Vector[Int](0))
        val controler = Controler(initGameState(allP=Vector[InterfacePlayer](p1), board=initBoard(numRows=1,numRowCards=6,deck=initDeck(1))._1, deck=initDeck(0)))
        val tui = TUI(controler)
    }
}