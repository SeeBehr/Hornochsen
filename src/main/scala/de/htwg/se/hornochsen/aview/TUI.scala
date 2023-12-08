package de.htwg.se.hornochsen.aview

import de.htwg.se.hornochsen.controler._
import de.htwg.se.hornochsen.model._
import de.htwg.se.hornochsen.util._
import scala.io.StdIn.readLine
import scala.util.{Try, Success, Failure}
import org.scalactic.Fail

private trait TuiState {
    def interpretLine(controler: Controler, input: String): Unit
}

private case object TuiStatePlayCard extends TuiState {
    def interpretLine(controler: Controler, input: String): Unit = {
        val intPut: Try[Int] = Try(input.toInt)
        intPut match
        case Success(i) =>
            var canplay = controler.playCard(controler.gameState.playerActive, input.toInt)
            canplay match
            case true =>
            case false =>
                println(s"Player ${controler.gameState.playerActive.name} can't play card ${input.toInt}")
                interpretLine(controler, readLine)
        case Failure(exception) =>
            val op = controler.doOp(input)
            op match
            case Success(a) =>

            case Failure(b) =>
                println(b)
                interpretLine(controler, readLine)
    }
}

private case object TuiStateTakeRow extends TuiState {
    def interpretLine(controler: Controler, input: String): Unit = {
        val intPut: Try[Int] = Try(input.toInt)
        intPut match
        case Success(i) =>
            var canplay = controler.takeRow(controler.gameState.playerActive, input.toInt)
            canplay match
            case Success(a) =>
            case Failure(b) =>
                println(b)
                interpretLine(controler, readLine)
        case Failure(exception) => 
            println(exception)
            interpretLine(controler, readLine)
    }
}

// Start of the Programm.
case class TUI(controler: Controler) extends UI {
    var state: TuiState = TuiStatePlayCard
    override def update(e:Event) = {
        e match
        case Event.Start =>
            println("Game started")
            println(controler.gameState.board.toString())
            println(controler.gameState.players().mkString("\n"))
            
            println("Next Player")
            println(controler.gameState.board.toString())
            println(controler.gameState.playerActive.toString())
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
            println(controler.gameState.playerActive.toString())
            run
        case Event.Redo =>
            println("Undo/Redo:\n new Gamestate:")
            println(controler.gameState.board.toString())
            println(controler.gameState.playerActive.toString())
            run
        case Event.End =>
            end
    }

    def run = {
        while(controler.running)
            state match
            case TuiStatePlayCard =>
                println(s"Player ${controler.gameState.playerActive.name}select Card to play:")
            case TuiStateTakeRow =>
                println(s"Player ${controler.gameState.playerActive.name}select Row to take:")
            state.interpretLine(controler, readLine)
    }

    def end = {

    }
}

def TUIplayerNames(a: Int): String = {
    println(s"Spielername $a")
    readLine
}