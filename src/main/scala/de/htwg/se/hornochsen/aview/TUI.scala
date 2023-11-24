package de.htwg.se.hornochsen.aview

import de.htwg.se.hornochsen.controler._
import de.htwg.se.hornochsen.model._
import de.htwg.se.hornochsen.util._
import scala.io.StdIn.readLine

// Start of the Programm.
case class TUI(controller: Controler) extends Observer{
    override def update(e: Event) = {
        e match
        case Event.Undo =>
            println("Undo/Redo:\n new Gamestate:")
            println(controller.gameState.board.toString())
            println(controller.gameState.players.mkString("\n"))
        case Event.PlayRound => run
        case Event.RoundFinished => 
            println(controller.gameState.board.toString())
            println(controller.gameState.players.mkString("\n"))
        case Event.CardsSelected =>
            println(controller.gameState.board.toString())
        case Event.End => end
        case Event.NextRound =>
            println(controller.gameState.board.toString())
            println(controller.gameState.players.mkString("\n"))

    }

    def run = {
        controller.gameState = controller.updatePlayedCards(playCards(controller.gameState.players, readLine))
        controller.notifyObservers(Event.CardsSelected)
        controller.gameState = controller.updateGamestate(readLine, WhichRowTake)
        controller.notifyObservers(Event.RoundFinished)
        if (controller.beginNextRound(TUIwhatToDo, readLine) == false)
            controller.notifyObservers(Event.Undo)
        else controller.notifyObservers(Event.NextRound)
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
    def end = {
        println("Spiel beendet")
        for (p <- controller.gameState.players.sortBy(_.ochsen)) {
            println(s"${p.name} hat ${p.ochsen} Ochsen")
        }
    }
}
def TUIplayerNames(a: Int): String = {
    println(s"Spielername $a")
    readLine
}
def TUIwhatToDo(ausgabe:String) = {
    print(ausgabe)
}