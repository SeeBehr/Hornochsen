package de.htwg.se.hornochsen.aview

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import de.htwg.se.hornochsen.model._
import de.htwg.se.hornochsen.aview._
import de.htwg.se.hornochsen.util._
import de.htwg.se.hornochsen.controler.BaseControler.{initDeck, Controler}
import de.htwg.se.hornochsen.model.BaseModel.{Row, Board}
import de.htwg.se.hornochsen.model.BaseModel.GameState
import de.htwg.se.hornochsen.model.BaseModel.Player

class TUISpec extends AnyWordSpec {
    "The TUI" should {
        val p1 = Player(name="Patrick",cards=Vector[Int](2))
        val p2 = Player(name="Patrick",cards=Vector[Int](0))
        val controler = Controler(GameState(playersDone=Vector.empty, playerActive= Player(), playersWaiting=Vector(p1), board=Board(rows=Vector[Row](Row(nummer=1,cards=Vector(1,0)))),remDeck=initDeck(0)))
        val tui = TUI(controler)
    }
}