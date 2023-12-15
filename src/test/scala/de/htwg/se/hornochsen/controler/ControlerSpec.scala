package de.htwg.se.hornochsen.controler

import de.htwg.se.hornochsen._
import model._
import controler._
import util._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

import BaseControler.{initializeGame, Controler}
import de.htwg.se.hornochsen.model.BaseModel.{Row, Board}
import de.htwg.se.hornochsen.model.BaseModel.GameState
import de.htwg.se.hornochsen.model.BaseModel.Player

class Controlerspec extends AnyWordSpec {
    "The logic" should {
        val board1 = initBoard(numRows=1, numRowCards=6, deck=initDeck(number = 1))._1
        val player1 = Player(Name = "Sebastian", Cards = Vector(2, 5), Ochsen = 0)
        val player2 = Player(Name = "Nicht Sebastian", Cards = Vector(1, 3), Ochsen = 0)

        val boardselect = initBoard(numRows = 1, numRowCards = 1, deck = initDeck(number = 4))
        val gameState = GameState(
            playersdone = Vector.empty,
            playeractive = Player(),
            playerswaiting = Vector(player1, player2),
            myBoard = boardselect,
            RemDeck = initDeck(1))


        var controler: InterfaceControler = makeControler(gameState)

        "be able to put card" in {
            controler.playCard(player1, -1, "StatePlayCards") should be (false)
            controler.playCard(player1, 0, "StatePlayCards") should be (true)
        }

        "have all Player's" in {
            // Test with one player
            val (players, remainingDeck) = BaseControler.PlayerFactory.getInstance(
                playerCount = 1, 
                numHandCards = 1, 
                input = (a)=>"Seebastian", 
                deck = initDeck(2)
            )
            players.toString() should be (
                Vector(Player(Name = "Seebastian", Cards = Vector(1), Ochsen = 0)).toString
            )
            remainingDeck.toString() should be("Deck: 2\n")

            // Test with two players
            val (players_2, remainingDeck_2) = BaseControler.PlayerFactory.getInstance(
                playerCount = 2, 
                numHandCards = 2, 
                input = (a)=>"Seebastiaan", 
                deck = initDeck(5)
            )
            players_2.toString() should be (
                Vector(
                    Player(Name = "Seebastiaan", Cards = Vector(1, 2), Ochsen = 0),
                    Player(Name = "Seebastiaan", Cards = Vector(3, 4), Ochsen = 0),
                ).toString
            )
            remainingDeck_2.toString() should be("Deck: 5\n")
        }

        "return a Gamestate" in {
            val newgame = initializeGame(shuffle=false, sizeDeck=2, numRows=1, numRowCards=1, numPlayer=1, numHandCards=1, input = Int => "A")
            //newgame._1.toString() should be (Vector.empty.toString())
            newgame.players.toString() should be ("Vector(A:\n\tcards: 2\n\tOchsen: 0\n)")
            //newgame._3.toString() should be (Vector.empty.toString())
            newgame.board.toString() should be ("Board:\n\tRow 1: 1 filled: 1\n\n")
            newgame.remDeck.toString() should be ("Deck: \n")
            String("") should be;
        }

        /*
        "have a history" in {
            val vergleich = controler.gameState
            controler.playCard(player1, 2, "StatePlayCards")
            controler.playCard(player2, 3, "StatePlayCards")
            vergleich == controler.gameState should be (false)
            controler.doOp("undo", "StatePlayCards")
            vergleich == controler.gameState should be (false)
            controler.doOp("undo", "StatePlayCards")
            vergleich == controler.gameState should be (true)
            controler.doOp("redo", "StatePlayCards")
            vergleich == controler.gameState should be (false)
            controler.doOp("redo", "StatePlayCards")
            vergleich == controler.gameState should be (false)
            controler.doOp("asdf", "StatePlayCards")
            vergleich == controler.gameState should be (false)
            controler.doOp("end", "StatePlayCards")
            controler.undoHistory.clear()
            controler.redoHistory.mementos should be (Vector.empty)
        }
         */
    }
}