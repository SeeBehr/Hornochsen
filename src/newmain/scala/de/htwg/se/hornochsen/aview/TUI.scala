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
        val intPut: Try[Int] = Try(input.toInt)
        intPut match
        case Success(i) =>
            var canplay = controler.playCard(controler.gameState.playerActive, input.toInt)
            canplay match
            case true =>
            case false =>
                println(s"Player ${player.name} can't play card ${input.toInt}")
                TuiStatePlayCard()
    }
}

private case object TuiStateTakeRow extends TuiState {
    def interpretLine(input: String): Unit = {
        println(s"Player ${p.name}select Row to take:")
        val intPut: Try[Int] = Try(input.toInt)
        intPut match
        case Success(i) =>
            var canplay = controler.takeRow(controler.gameState.playerActive, input.toInt)
            canplay match
            case true =>
            case false =>
                println(s"Player ${player.name} can't take Row ${input.toInt}")
                TuiStatePlayCard()
    }
}

// Start of the Programm.
case class TUI(controler: Controler) extends UI {
    var state: TuiState = TuiStatePlayCard
    override def update(e:Event) = {
        e match
        case Evet.Start =>
            println("Game started")
            println(controler.gameState.board.toString())
            println(controler.gameState.players.mkString("\n"))
            state = TuiStatePlayCard
            run
        case Event.nextPlayer =>
            println("Next Player")
            println(controler.gameState.board.toString())
            println(controler.gameState.playerActive.toString())
            state = TuiStatePlayCard
            run
        case Event.TakeRow =>
            println(controler.gameState.playerActive.name + "Take Row")
            println(controler.gameState.board.toString())
            state = TuiStateTakeRow
            run
        case Event.Undo =>
            println("Undo/Redo:\n new Gamestate:")
            println(controler.gameState.board.toString())
            println(controler.gameState.players.mkString("\n"))
            run
        case Event.Redo =>
            println("Undo/Redo:\n new Gamestate:")
            println(controler.gameState.board.toString())
            println(controler.gameState.players.mkString("\n"))
            run
        case Event.End =>
            end
    }

    def run = {
        while(controler.gamestate.running)
            state.interpretLine(readLine)
    }

    def end() = {

    }
}