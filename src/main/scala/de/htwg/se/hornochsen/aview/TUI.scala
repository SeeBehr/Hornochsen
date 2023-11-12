package de.htwg.se.hornochsen.aview

import de.htwg.se.hornochsen.controler._
import de.htwg.se.hornochsen.model._
import de.htwg.se.hornochsen.util._
import scala.io.StdIn.readLine

// Start of the Programm.
case class TUI(controller: Controler) extends Observer{
    var play = true
    override def update(e: Event) = {
        e match
        case Event.RoundFinished => 
            println(controller.gameState.board.toString())
            println(controller.gameState.players.mkString("\n"))
        case Event.CardsSelected =>
            println(controller.gameState.board.toString())
        case Event.Quit => play = false
    }

    def run = {
        while play do
            controller.updatePlayedCards(playCards(controller.gameState.players, readLine))
            controller.updateGamestate(WhichRowTake)
    }

    def playCards(players: Vector[Player], read: () => String): Vector[(Int,Player)] = {
        players.map(p=>
            println(s"Welche Karte soll ${p.name} legen?: ")
            val input = read()
            (input.toInt, p)
            )
    }

    def WhichRowTake(name: String, read: () => String): Int = {
        println(s"Welche Reihe nimmt ${name}? ")
        val input = read()
        input.toInt
    }

    def giveCards(cardCount: Int = 6): GameState = {
        val toBeDropped = this.controller.gameState.players.length * cardCount

        val drawnCards = this.controller.gameState.remDeck.cards.take(toBeDropped)
        val remDeck = this.controller.gameState.remDeck.remcount(toBeDropped)

        val newPlayers = this.controller.gameState.players.zipWithIndex.map((indexAndPlayer) => {
            indexAndPlayer._1.drawCards(drawnCards.slice(indexAndPlayer._2 * cardCount, (indexAndPlayer._2 + 1) * cardCount))
        })
        GameState(players = newPlayers, board = this.controller.gameState.board, remDeck = remDeck)
    }
}