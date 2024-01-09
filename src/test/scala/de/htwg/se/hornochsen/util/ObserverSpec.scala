package de.htwg.se.hornochsen.util

import de.htwg.se.hornochsen.model._
import de.htwg.se.hornochsen.aview._
import de.htwg.se.hornochsen.util._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import de.htwg.se.hornochsen.controler._
import de.htwg.se.hornochsen.model._
import de.htwg.se.hornochsen.controler.BaseControler.*

class ObserverSpec extends AnyWordSpec {
    "The Observer" should {
        val p1 = makePlayer(name="Patrick",cards=Vector[Int](2))
        val p2 = makePlayer(name="Patrick",cards=Vector[Int](0))
        val gs = initGameState(allP=Vector[InterfacePlayer](p1,p2),board=initBoard(1,6,initDeck(1))._1,deck=initDeck(0))
        val controler = Controler(gs)
        val tui = TUI(controler)
        "have a add method" in {
            controler.add(tui) should be (())
        }
        "have observers" in {
            controler.subscribers should be (Vector(tui))
        }
        "have a notify method" in {
            controler.notifyObservers(Event.End) should be (())
        }
        "have a remove method" in {
            controler.remove(tui) should be (())
        }
    }
}