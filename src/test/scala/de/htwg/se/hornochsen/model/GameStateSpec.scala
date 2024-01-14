package de.htwg.se.hornochsen.model


import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.hornochsen.model._
import de.htwg.se.hornochsen.model.BaseModel._
import de.htwg.se.hornochsen.controler.BaseControler.initializeGame

class GameStateSpec extends AnyWordSpec {
    "The Gamestate" should {
        val gameState: InterfaceGameState = GameState(
            playerswaiting = Vector(makePlayer(name="Sebastian",cards=Vector(3))),
            playeractive = makePlayer(name="Patrick",cards=Vector(2)),
            playersdone = Vector.empty,
            myBoard = Board(rows=Vector(Row(Nummer=1, myCards=Vector(1,0,0,0))), playedCards=Vector.empty),
            RemDeck = Deck(cards=Vector.empty)
        )
        
        "have a init Method" in {
            val deck = initDeck(0)
            val g: InterfaceGameState = initGameState(allP=Vector(makePlayer(name="Patrick",cards=Vector(2)),makePlayer(name="Sebastian",cards=Vector(3))), board=Board(rows=Vector(Row(Nummer=1, myCards=Vector(1,0,0,0))), playedCards=Vector.empty), deck=deck)
            g.toString should be (gameState.toString)
        }

        "have a players method" in {
            gameState.players should be (Vector(makePlayer(name="Patrick",cards=Vector(2)),makePlayer(name="Sebastian",cards=Vector(3))))
        }

        "have a playersDone method" in {
            gameState.playersDone should be (Vector.empty)
        }

        "have a playerActive method" in {
            gameState.playerActive should be (makePlayer(name="Patrick",cards=Vector(2)))
        }

        "have a playersWaiting method" in {
            gameState.playersWaiting should be (Vector(makePlayer(name="Sebastian",cards=Vector(3))))
        }

        "have a copy method" in {
            val g = gameState.copy(playerswaiting=Vector.empty, playeractive=makePlayer(name="Patrick",cards=Vector(2)), playersdone=Vector(makePlayer(name="Sebastian",cards=Vector(3))))
            g.playerActive should be (makePlayer(name="Patrick",cards=Vector(2)))
            g.playersWaiting should be (Vector.empty)
            g.playersDone should be (Vector(makePlayer(name="Sebastian",cards=Vector(3))))
        }

        "have a board method" in {
            gameState.board should be (Board(rows=Vector(Row(Nummer=1, myCards=Vector(1,0,0,0))), playedCards=Vector.empty))
        }

        "have a remDeck method" in {
            gameState.remDeck should be (Deck(cards=Vector.empty))
        }

        /*
        "have a save/loadToXML method" in {
            gameState.saveToXML("test.xml")
            val g = gameState.loadFromXML("test.xml")
            g.toString should be (gameState.toString)
        }

        "have a save/loadToJson method" in {
            gameState.saveToJson("test.json")
            val g = gameState.loadFromJson("test.json")
            g.toString should be (gameState.toString)
        }
        */
    }
}