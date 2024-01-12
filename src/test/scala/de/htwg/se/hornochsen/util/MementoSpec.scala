package de.htwg.se.hornochsen.util

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import de.htwg.se.hornochsen.model._
import de.htwg.se.hornochsen.util._
import de.htwg.se.hornochsen.controler._
import de.htwg.se.hornochsen.controler.BaseControler.*


class MementoSpec extends AnyWordSpec {
    val (board, remDeck) = initBoard(1,1,initDeck(1))
    val gamestate = initGameState(allP=Vector[InterfacePlayer](makePlayer("Patrick",Vector[Int](1),ochsen=0)),board=board,deck=remDeck)
    var controler = Controler(gamestate)
    val memento = ConcreteMemento(gamestate)
    "The Memento" should {
        "have a originator" in {
            memento.originator should be (gamestate)
        }

        "be as a string" in {
            memento.originator.toString() should be (gamestate.toString())
        }
        
        "restore the originator" in {
            memento.restore() should be (gamestate)
        }
        "have a History" in {
            val history = new History()
            history.save(memento)
            history.mementos should be (List(memento))
            history.toString() should be ("History: \n" + memento.toString() + "\n")
            history.restore().get should be (memento)
        }
        "have a max undo" in {
            val history = new History()
            history.save(memento)
            history.save(memento)
            history.save(memento)
            history.save(memento)
            history.save(memento)
            history.save(memento)
            history.save(memento)
            history.save(memento)
            history.save(memento)
            history.save(memento)
            history.save(memento)
            history.mementos.length should be (10)
        }
    }
}