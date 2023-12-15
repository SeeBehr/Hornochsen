/*
package de.htwg.se.hornochsen.aview

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import de.htwg.se.hornochsen.model._
import de.htwg.se.hornochsen.aview._
import de.htwg.se.hornochsen.util._

import de.htwg.se.hornochsen.model.initDeck
import de.htwg.se.hornochsen.controler.BaseControler.Controler
import de.htwg.se.hornochsen.model.BaseModel.GameState
import de.htwg.se.hornochsen.model.BaseModel.Player
import de.htwg.se.hornochsen.model.BaseModel.Board
import de.htwg.se.hornochsen.model.BaseModel.Row

class TUISpec extends AnyWordSpec {
    "The TUI" should {
        val p1 = Player(Name="Patrick", Cards=Vector[Int](2))
        val p2 = Player(Name="Patrick", Cards=Vector[Int](0))
        val controler = Controler(GameState(
            playersdone =Vector.empty, 
            playeractive = Player(), 
            playerswaiting =Vector(p1), 
            myBoard = Board(
                rows = Vector[Row](Row(
                    nummer = 1,
                    cards = Vector(1,0)
                ))
            ),
            RemDeck = initDeck(0)
        ))
        val tui = TUI(controler)
    }
}
*/