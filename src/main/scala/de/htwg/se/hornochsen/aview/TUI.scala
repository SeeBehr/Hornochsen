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
        case Event.NextRound =>
            println(controller.gameState.board.toString())
            println(controller.gameState.players.mkString("\n"))

    }

    override def run = controller.run(playCards(controller.gameState.players, readLine), readLine, WhichRowTake, println)

    override def playCards(players: Vector[Player], read: () => String): Vector[(Int,Player)] = {
        players.map(p=>
            var ungueltig = true
            var cardNr: Int = 0
            while ungueltig do
                println(s"Welche Karte soll ${p.name} legen?: ")
                val card = getCardFromConsole(read)
                if (card.isSuccess && p.cards.contains(card.get))
                then
                    ungueltig = false
                    cardNr = card.get
                else
                    println("Karte nicht vorhanden!")
   
            (cardNr, p)
            )
    }

    def getCardFromConsole(read: () => String): Try[Int] = Try {read().toInt}

    override def WhichRowTake(name: String, read: () => String): Int = {
        println(s"Welche Reihe nimmt ${name}? ")
        val input = read()
        input.toInt
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