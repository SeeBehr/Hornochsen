package de.htwg.se.hornochsen.aview

import de.htwg.se.hornochsen.controler._
import de.htwg.se.hornochsen.model._
import de.htwg.se.hornochsen.util._
import scala.io.StdIn.readLine
import scala.util.{Try, Success, Failure}
import org.scalactic.Fail

// Start of the Programm.
case class TUI(controller: Controler) extends UI{
    override def update(e: Event) = {
        e match
        case Event.Undo =>
            println("Undo/Redo:\n new Gamestate:")
            println(controller.gameState.board.toString())
            println(controller.gameState.players.mkString("\n"))
        case Event.GameStart => 
            println(controller.gameState.board.toString())
            println(controller.gameState.players.mkString("\n"))
        case Event.PlayRound => 
            println(controller.gameState.board.toString())
            println(controller.gameState.players.mkString("\n"))
            run
        case Event.RoundFinished => 
            println(controller.gameState.board.toString())
            println(controller.gameState.players.mkString("\n"))
        case Event.CardsSelected =>
            println(controller.gameState.board.toString())
        case Event.End => end

    }

    override def run = controller.run(playCards, readLine, WhichRowTake, println)

    override def playCards(player: Player, read: () => String): (Int,Player) = {
        getPlayedCardFromPlayer(player, read)
    }

    def getIntFromConsole(read: () => String): Try[Int] = Try {read().strip().toInt}
    
    def getPlayedCardFromPlayer(p: Player, read: () => String): (Int, Player) = {
        println(s"Welche Karte soll ${p.name} legen?: ")
        val card = getIntFromConsole(read)
        if (card.isSuccess && p.cards.contains(card.get))
        then
            (card.get, p)
        else
            println("Karte nicht vorhanden!")
            getPlayedCardFromPlayer(p, read)
    }

    override def WhichRowTake(name: String, read: () => String): Int = {
        println(s"Welche Reihe nimmt ${name}? ")
        val input: Try[Int] = getIntFromConsole(read)
        if input.isSuccess
        then
            print("Gewaehle Reihe: " + input.get + "\n")
            input.get
        else
            WhichRowTake(name = name, read = read)
    }

    override def end = {
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