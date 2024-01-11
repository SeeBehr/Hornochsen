package de.htwg.se.hornochsen.aview

import de.htwg.se.hornochsen.model._
import de.htwg.se.hornochsen.util._
import scala.io.StdIn.readLine
import scala.util.{Try, Success, Failure}
import org.scalactic.Fail
import scalafx.scene.input.KeyCode.T
import java.awt.Taskbar.State
import de.htwg.se.hornochsen.controler.InterfaceControler
import scala.compiletime.ops.int

trait TUIState {
    var state: TUIState
    def interpretLine(controler:InterfaceControler, input: String): Unit
}

private case object StateInit extends UIState with TUIState {
    var state: TUIState = StateInit
    val name: String = "StateInit"
    var players: Vector[String] = Vector.empty
    def interpretLine(controler:InterfaceControler, input: String): Unit = {
        input match 
            case "start" =>
                if players.length < 2 then
                    println("Not enough players")
                    interpretLine(controler, readLine)
                else
                    state = StatePlayCard
                    controler.start(players)
            case _ =>
                players = players.appended(input)
                interpretLine(controler, readLine)
    }
}

private case object StatePlayCard extends UIState with TUIState {
    var state: TUIState = StatePlayCard
    val name: String = "StatePlayCard"
    def interpretLine(controler:InterfaceControler, input: String): Unit = {
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

// Start of the Programm.
case class TUI(controler:InterfaceControler) extends UI with TUIState{
    var state: TUIState = StatePlayCard
    val name: String = "TUI"
    override def update(e:Event, name:String="0") = {
        e match
            case Event.First =>
            case Event.Start =>
                state = StateInit
                run
            case Event.nextPlayer =>
                println("Next Player")
                println(controler.gameState.board.toString())
                println(controler.gameState.playerActive.toString())
                println(s"Select Card to play:")
                state = StatePlayCard
            case Event.Undo =>
                println("Undo:\n new Gamestate:")
                println("Next Player")
                println(controler.gameState.board.toString())
                println(controler.gameState.playerActive.toString())
                println(s"Select Card to play:")
                state = StatePlayCard
            case Event.Redo =>
                println("Redo:\n new Gamestate:")
                println("Next Player")
                println(controler.gameState.board.toString)
                println(controler.gameState.board.playedCardsToString)
                println(s"Select card to play:")
                state = StatePlayCard
            case Event.End =>
                println("End. ")
    }

    override def run = {
        while controler.isrunning do
            interpretLine(controler, readLine)
    }

    override def end = {
        println("Game ended")
        println("Winner: " + controler.gameState.players.maxBy(p => p.ochsen).name)
        println("Score: ")
        println(controler.gameState.players.sortBy(p=>p.ochsen).mkString("\n"))
    }

    override def interpretLine(controler:InterfaceControler, input: String): Unit = {
        state.interpretLine(controler, input)
    }
}

def TUIplayerNames(a: Int): String = {
    println(s"Spielername $a")
    readLine
}