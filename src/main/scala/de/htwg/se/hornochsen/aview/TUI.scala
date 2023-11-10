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
            controller.updatePlayedCards(playCards(controller.gameState.players))
            controller.updateGamestate(WhichRowTake)
    }

    def playCards(players: Vector[Player]): Vector[(Int,Player)] = {
        players.map(p=>
            (readLine(s"Welche Karte soll ${p.name} legen?: ").toInt, p)
            )
    }

    def WhichRowTake(name: String): Int = {
        return readLine(s"Welche Reihe nimmt ${name}? ").toInt
    }
}