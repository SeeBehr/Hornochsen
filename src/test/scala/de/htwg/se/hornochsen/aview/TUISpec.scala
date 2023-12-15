package de.htwg.se.hornochsen.aview

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import de.htwg.se.hornochsen.aview._
import de.htwg.se.hornochsen.util._
import de.htwg.se.hornochsen.controler._
import de.htwg.se.hornochsen.model._

class TUISpec extends AnyWordSpec {
    "The TUI" should {
        val p1 = makePlayer(name="Patrick",cards=Vector[Int](2))
        val p2 = makePlayer(name="Patrick",cards=Vector[Int](0))
        val controler = makeControler(initGameState(allP=Vector[InterfacePlayer](p1), board=initBoard(numRows=1,numRowCards=initDeck(1))._1, deck=initDeck(0)))
        val tui = TUI(controler)
    }
}