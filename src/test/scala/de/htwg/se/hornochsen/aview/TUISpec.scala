package de.htwg.se.hornochsen.aview

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import de.htwg.se.hornochsen.controler._
import de.htwg.se.hornochsen.model._
import de.htwg.se.hornochsen.aview._
import de.htwg.se.hornochsen.util._

class TUISpec extends AnyWordSpec {
    "The TUI" should {
        val p1 = Player(name="Patrick",cards=Vector[Int](2))
        val p2 = Player(name="Patrick",cards=Vector[Int](0))
        val controler = Controler(GameState(players=Vector(p1), board=Board(rows=Vector[Row](Row(nummer=1,cards=Vector(1,0)))),remDeck=initDeck(0)))
        val tui = TUI(controler)

        "have a playCards method" in {
            tui.playCards(p1, ()=>"2").toString() should be ("(2,Patrick:\n\tcards: 2\n\tOchsen: 0\n)")
        }

        "have a WhichRowTake method" in {
            tui.WhichRowTake("Patrick", () => "1") should be (1)
        }
    }
}