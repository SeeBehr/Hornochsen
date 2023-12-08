package de.htwg.se.hornochsen.aview

import de.htwg.se.hornochsen.controler._
import scala.io.StdIn.readLine
import scala.util.{Try, Success, Failure}
import org.scalactic.Fail

private trait TuiState {
    def interpretLine(input: String): Unit
}

private case object TuiStatePlayCard extends TuiState {
    def interpretLine(input: String): Unit = {
        var canplay = controler.playCard(player, input.toInt)
        canplay match
        case Success(p) =>
            println(s"Player ${p.name} played card ${input.toInt}")
        case Failure(e) =>
            println(s"Player ${player.name} can't play card ${input.toInt}")
            TuiStatePlayCard()
    }
}

// Start of the Programm.
case class TUI(controler: Controler) extends UI {
    var state: TuiState = TuiStateCplayCard
    override def update(e:Event) = {
        e match
        case Event.Undo =>
        case Event.GameStart =>
            controller.gameState.board.toString()
            controller.gameState.players.mkString("\n")
            run
        case Event.PlayRound =>
        case Event.RoundFinished =>
        case Event.CardsSelected =>
        case Event.End =>
    }

    def run = {
        while(controler.gamestate.running)
            interpretLine(readLine)
    }

    def interpretLine(input: String): Unit = {
    }
}