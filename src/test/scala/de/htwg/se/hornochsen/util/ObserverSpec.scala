package de.htwg.se.hornochsen.util

import de.htwg.se.hornochsen.model._
import de.htwg.se.hornochsen.controler._
import de.htwg.se.hornochsen.aview._
import de.htwg.se.hornochsen.util._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class ObserverSpec extends AnyWordSpec {
    "The Observer" should {
        val p1 = Player(name="Patrick",cards=Vector[Int](2))
        val p2 = Player(name="Patrick",cards=Vector[Int](0))
        val controler = Controler(GameState(playersDone= Vector.empty[Player], playerActive = Player(), playersWaiting=Vector(p1, p2), board=Board(rows=Vector[Row](Row(nummer=1,cards=Vector(1,0)))),remDeck=initDeck(0)))
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