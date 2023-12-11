package de.htwg.se.hornochsen.aview

import de.htwg.se.hornochsen.controler._
import de.htwg.se.hornochsen.model._
import de.htwg.se.hornochsen.util._
import scala.io.StdIn.readLine
import scala.util.{Try, Success, Failure}
import org.scalactic.Fail
import scalafx.scene.input.KeyCode.T
import java.awt.Taskbar.State

trait TUIState {
    var state: TUIState
    def interpretLine(controler: Controler, input: String): Unit
}

private case object StatePlayCard extends UIState with TUIState {
    var state: TUIState = StatePlayCard
    val name: String = "StatePlayCard"
    def interpretLine(controler: Controler, input: String): Unit = {
        val intPut: Try[Int] = Try(input.toInt)
        intPut match
        case Success(i) =>
            var canplay = controler.playCard(controler.gameState.playerActive, input.toInt, name)
            canplay match
            case true =>
            case false =>
                println(s"Player ${controler.gameState.playerActive.name} can't play card ${input.toInt}")
                interpretLine(controler, readLine)
        case Failure(exception) =>
            val op = controler.doOp(input, name)
            op match
            case Success(a) =>

            case Failure(b) =>
                println(b)
                interpretLine(controler, readLine)
    }
}

private case object StateTakeRow extends UIState with TUIState {
    var state: TUIState = StateTakeRow
    val name: String = "StateTakeRow"
    def interpretLine(controler: Controler, input: String): Unit = {
        val intPut: Try[Int] = Try(input.toInt)
        intPut match
        case Success(i) =>
            var cantake = controler.takeRow(controler.gameState.playerActive, input.toInt, name)
            cantake match
            case Success(a) =>
            case Failure(b) =>
                println(b)
                interpretLine(controler, readLine)
        case Failure(exception) =>
            val op = controler.doOp(input, name)
            op match
            case Success(a) =>
                println(exception)
            case Failure(b) =>
                println(b)
                interpretLine(controler, readLine)
    }
}

// Start of the Programm.
case class TUI(controler: Controler) extends UI with TUIState{
    var state: TUIState = StatePlayCard
    val name: String = "TUI"
    override def update(e:Event, name:String="0") = {
        e match
        case Event.Start =>
            println("Game started")
            println(controler.gameState.board.toString())
            println(controler.gameState.players().mkString("\n"))
            state = StatePlayCard
            run
        case Event.nextPlayer =>
            println("Next Player")
            println(controler.gameState.board.toString())
            println(controler.gameState.playerActive.toString())
            state = StatePlayCard
            run
        case Event.TakeRow =>
            println(controler.gameState.board.toString())
            println(controler.gameState.board.playedCardsToString)
            println(controler.gameState.playerActive.name + " Take Row")
            StateTakeRow.interpretLine(controler, readLine)
        case Event.Undo =>
            println("Undo:\n new Gamestate:")
            println(controler.gameState.board.toString())
            println(controler.gameState.playerActive.toString())
            name match
            case "StatePlayCard" =>
                state = StatePlayCard
            case "StateTakeRow" =>
                state = StateTakeRow
            run
        case Event.Redo =>
            println("Redo:\n new Gamestate:")
            println(controler.gameState.board.toString())
            println(controler.gameState.playerActive.toString())
            name match
            case "StatePlayCard" =>
                state = StatePlayCard
            case "StateTakeRow" =>
                state = StateTakeRow
            run
        case Event.End =>
            end
    }

    override def run = {
        while(controler.running)
            println("New Round")
            state match
            case StatePlayCard =>
                println("Next Player")
                println(controler.gameState.board.toString())
                println(controler.gameState.playerActive.toString())
                println(s"Player ${controler.gameState.playerActive.name} select Card to play:")
            case StateTakeRow =>
                println("Next Player")
                println(controler.gameState.board.toString)
                println(controler.gameState.board.playedCardsToString)
                println(s"Player ${controler.gameState.playerActive.name} select Row to take:")
            interpretLine(controler, readLine)
    }

    override def end = {
        println("Game ended")
        println("Winner: " + controler.gameState.players().maxBy(p => p.ochsen).name)
        println("Score: ")
        println(controler.gameState.players().sortBy(p=>p.ochsen).mkString("\n"))
    }

    override def interpretLine(controler: Controler, input: String): Unit = {
        state.interpretLine(controler, input)
    }
}

def TUIplayerNames(a: Int): String = {
    println(s"Spielername $a")
    readLine
}