package de.htwg.se.hornochsen.util

import de.htwg.se.hornochsen.model._
import de.htwg.se.hornochsen.controler._
import de.htwg.se.hornochsen.aview._

object Facade {
    def initializeGame(shuffle: Boolean = true, sizeDeck: Int = 120, numRows: Int = 4, numRowCards: Int = 6, numPlayer: Int = 4, numHandCards: Int = 6, input: Int => String = TUIplayerNames): GameState = {
        val deck = if (shuffle) {
            initDeck(sizeDeck).shuffle()
        } else {
            initDeck(sizeDeck)
        }
        val (board, playerdeck) = initBoard(numRows, numRowCards, deck)
        val (allP, refilldeck) = PlayerFactory.getInstance(playerCount = numPlayer, numHandCards = numHandCards, input = input, deck = playerdeck)
        GameState(allP, board, refilldeck)
    }
}