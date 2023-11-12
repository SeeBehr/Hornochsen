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
        controller.gameState = controller.updatePlayedCards(playCards(controller.gameState.players, readLine))
        controller.notifyObservers(Event.CardsSelected)
        controller.gameState = controller.updateGamestate(WhichRowTake)
        controller.notifyObservers(Event.RoundFinished)
    }

    def playCards(players: Vector[Player], read: () => String): Vector[(Int,Player)] = {
        players.map(p=>
            var gueltig = true
            var input = ""
            while gueltig do
                println(s"Welche Karte soll ${p.name} legen?: ")
                input = read()
                if (p.cards.contains(input.toInt))
                then
                    gueltig = false
                else
                    println("Karte nicht vorhanden!")
            (input.toInt, p)
            )
    }

    def WhichRowTake(name: String, read: () => String): Int = {
        println(s"Welche Reihe nimmt ${name}? ")
        val input = read()
        input.toInt
    }
}