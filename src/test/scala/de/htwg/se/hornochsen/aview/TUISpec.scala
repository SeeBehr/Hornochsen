package de.htwg.se.hornochsen.aview

import de.htwg.se.hornochsen.controler.*
import de.htwg.se.hornochsen.model.*
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import com.google.inject.Guice
import com.google.inject.Injector
import de.htwg.se.hornochsen.HornochsenTestModule

class TUISpec extends AnyWordSpec {
    val injector: Injector = Guice.createInjector(new HornochsenTestModule(decksize = 1))

    "The TUI" should {
        val p1 = makePlayer(name="Patrick",cards=Vector[Int](2))
        val p2 = makePlayer(name="Patrick",cards=Vector[Int](0))
        val controler = makeControler(initGameState(allP=Vector[InterfacePlayer](p1), board=initBoard(numRows=1,numRowCards=6,deck=injector.getInstance(classOf[InterfaceDeck]))._1, deck=initDeck(0)))
        val tui = TUI(controler)
    }
}