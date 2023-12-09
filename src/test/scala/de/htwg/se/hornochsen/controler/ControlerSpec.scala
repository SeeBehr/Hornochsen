package de.htwg.se.hornochsen.controler

import de.htwg.se.hornochsen._
import model._
import controler._
import util._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class Controlerspec extends AnyWordSpec {
    "The logic" should {
        val board1 = Board(rows=Vector[Row](Row(nummer=1, cards=Vector[Int](1,0,0,0,0,0),filled=1)))
        val player1 = Player(name = "Sebastian", cards = Vector(2, 5), ochsen = 0)
        val player2 = Player(name = "Nicht Sebastian", cards = Vector(1, 3), ochsen = 0)
        val p1 = player1.playCard(2)
        val p2 = player2.playCard(3)
        val boardselect = Board(rows=board1.rows,playedCards=Vector((2,p1),(3,p2)))
        val gameState = GameState(playersDone=Vector.empty, playerActive=Player(), playersWaiting=Vector(player1, player2), board=boardselect, remDeck=initDeck(1))
        var controler = Controler(gameState)

        "be able to put card" in {
            controler.canAdd(-1) should be (false)
            controler.canAdd(0) should be (true)
        }

        "have all Player's" in {
            // Test with one player
            val (players, remainingDeck) = PlayerFactory.getInstance(
                playerCount = 1, 
                numHandCards = 1, 
                input = (a)=>"Seebastian", 
                deck = initDeck(2)
            )
            players.toString() should be (
                Vector(Player(name = "Seebastian", cards = Vector(1), ochsen = 0)).toString
            )
            remainingDeck.toString() should be("Deck: 2\n")

            // Test with two players
            val (players_2, remainingDeck_2) = PlayerFactory.getInstance(
                playerCount = 2, 
                numHandCards = 2, 
                input = (a)=>"Seebastiaan", 
                deck = initDeck(5)
            )
            players_2.toString() should be (
                Vector(
                    Player(name = "Seebastiaan", cards = Vector(1, 2), ochsen = 0), 
                    Player(name = "Seebastiaan", cards = Vector(3, 4), ochsen = 0), 
                ).toString
            )
            remainingDeck_2.toString() should be("Deck: 5\n")
        }

        "return a Gamestate" in {
            val newgame = initializeGame(shuffle=false, sizeDeck=2, numRows=1, numRowCards=1, numPlayer=1, numHandCards=1, input = Int => "A")
            newgame._1.toString() should be ("Vector(A:\n\tcards: 2\n\tOchsen: 0\n)")
            newgame._2.toString() should be (Player().toString())
            newgame._3.toString() should be (Vector.empty.toString())
            newgame._4.toString() should be ("Board:\n\tRow 1: 1 filled: 1\n\n")
            newgame._5.toString() should be ("Deck: \n")
        }

        "have a history" in {
            def ausgabe = "1"
            def eingabe(player1: Player, ausgabe: () => String) = (1,player1)
            val vergleich = controler.gameState
            controler.playCard(player1, 1, "StatePlayCards")
            controler.playCard(player2, 3, "StatePlayCards")
            vergleich == controler.gameState should be (false)
            controler.undo("StatePlayCards")
            vergleich == controler.gameState should be (true)
            controler.redo("StatePlayCards")
            vergleich == controler.gameState should be (false)
        }
    }
}