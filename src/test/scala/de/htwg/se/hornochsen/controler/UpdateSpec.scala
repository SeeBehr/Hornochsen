package de.htwg.se.hornochsen.controler

import de.htwg.se.hornochsen.controler._
import de.htwg.se.hornochsen.model._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class UpdateSpec extends AnyWordSpec {
    val p1 = Player(name="Patrick",cards=Vector[Int](2))
    val b1 = Board(rows=Vector[Row](Row(nummer=1,cards=Vector(1,0))),playedCards=Vector((2,p1)))
    val gameState1: GameState = GameState(players=Vector(p1), board=b1,remDeck=initDeck(0))
    val p2 = Player(name="Patrick",cards=Vector[Int](0))
    val b2 = Board(rows=Vector[Row](Row(nummer=1,cards=Vector(1,0))),playedCards=Vector((0,p2)))
    val gameState2: GameState = GameState(players=Vector(p2), board=b2,remDeck=initDeck(0))
    val controler1 = Controler(gameState1)
    val controler2 = Controler(gameState2)
    "update" should {
        "sort played cards" in {
            controler1.updatePlayedCards(cardsToPlay=Vector((2,p1),(0,p2)))._2.playedCards.toString() should be ("Vector((0,Patrick:\n\tcards: 0\n\tOchsen: 0\n)"
            +", (2,Patrick:\n\tcards: 2\n\tOchsen: 0\n))")
        }
        "return an updated gamestate" in {
            controler1.updateGamestate(WhichRowTake=(s:String, f:()=>String)=>1)._1.toString() should be ("Vector(Patrick:\n\tcards: \n\tOchsen: 0\n)") 
            controler1.updateGamestate(WhichRowTake=(s:String, f:()=>String)=>1)._2.toString() should be ("Board:\n\tRow 1: 1, 2 filled: 2\n\nPlayed cards: \n")

            controler2.updateGamestate(WhichRowTake=(s:String, f:()=>String)=>1)._1.toString() should be ("Vector(Patrick:\n\tcards: \n\tOchsen: 1\n)") 
            controler2.updateGamestate(WhichRowTake=(s:String, f:()=>String)=>1)._2.toString() should be ("Board:\n\tRow 1: 0, 0 filled: 1\n\nPlayed cards: \n0, von Patrick\n")
        }
    }
}