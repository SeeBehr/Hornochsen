package de.htwg.se.hornochsen

import de.htwg.se.hornochsen.controler._
import de.htwg.se.hornochsen.model._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class UpdateSpec extends AnyWordSpec {
    val p1 = Player(name="Patrick",cards=Vector[Int](2))
    val b1 = Board(rows=Vector[Row](Row(nummer=1,cards=Vector(1,0))))
    val gameState: GameState = GameState(players=Vector(p1), board=b1,remDeck=initDeck(0))
    "update" should {
        "return an updated gamestate" in {
            updateGamestate(gameState=gameState, playCards=players=>Vector((2,p1)), WhichRowTake=(s:String)=>0)._1.toString() should be ("Vector(Patrick:\n\tcards: \n\tOchsen: 0\n)") 
            updateGamestate(gameState=gameState, playCards=players=>Vector((2,p1)), WhichRowTake=(s:String)=>0)._2.toString() should be ("Board:\n\tRow 1: 2, 0 filled: 1\n\nPlayed cards: \n")
        }
    }
}