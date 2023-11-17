package de.htwg.se.hornochsen.controler

import de.htwg.se.hornochsen.controler._
import de.htwg.se.hornochsen.model._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class ActionStateSpec extends AnyWordSpec {
    "ActionStates" should {
        var state = ActionState
        val defaultState = state.handle(ActionEvent.default)
        val addCardState = state.handle(ActionEvent.addCard)
        val takeRowState = state.handle(ActionEvent.takeRow)
        val selectRowState = state.handle(ActionEvent.selectRow)
        def method1(s: String, f: () => String): Int = {
            return 1
        }
        def method2(): String = {
            return "1"
        }

        val player = Player(name = "Patrick", cards = Vector(2, 5), ochsen = 0)
        val board = Board(rows=Vector[Row](Row(nummer=1, cards=Vector[Int](4,0),filled=1)))

        "have a default state" in {
            defaultState(board,5,1,player, method1, method2) should be ()
        }

        "have a addCard state" in {
            addCardState(board,5,1,player, method1, method2) should be ()
        }

        "have a takeRow state" in {
            takeRowState(board,2,1,player, method1, method2) should be ()
        }
        
        "have a selectRow state" in {
            selectRowState(board,2,1,player, method1, method2) should be ()
        }
    }
}