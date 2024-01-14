package de.htwg.se.hornochsen.aview

import de.htwg.se.hornochsen.controler.InterfaceControler
import de.htwg.se.hornochsen.util.*

import scala.io.StdIn.readLine
import scala.util.{Failure, Success, Try}

trait TUIState {
    var state: TUIState
    def interpretLine(controler:InterfaceControler, input: String): Unit
}

private case object StateInit extends UIState with TUIState {
    var state: TUIState = StateInit
    val name: String = "StateInit"
    var players: Vector[String] = Vector.empty
    def interpretLine(controler:InterfaceControler, input: String): Unit = {
        val splitted = input.split(" ")
        splitted(0) match 
            case "start" =>
                if players.length < 2 then
                    println("Not enough players")
                else
                    state = StatePlayCard
                    controler.start(players)
            case "players" =>
                println(players.mkString("\n"))
            case _ =>
                splitted(1) match
                    case "add" =>
                        players = players.appended(splitted(0))
                    case "remove" =>
                        players = players.filterNot(p => p == splitted(0))
                    case _ =>
                        println("Unknown Command")
    }
}

private case object StateEnd extends UIState with TUIState {
    var state: TUIState = StateEnd
    def interpretLine(controler:InterfaceControler, input: String): Unit = {
        input match
            case "restart" =>
                controler.restart
            case _ =>
                println("Unknown Command")
                interpretLine(controler, readLine)
    }
}

private case object StatePlayCard extends UIState with TUIState {
    var state: TUIState = StatePlayCard
    def interpretLine(controler:InterfaceControler, input: String): Unit = {
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

// Start of the Programm.
case class TUI(controler:InterfaceControler) extends UI with TUIState{
    var state: TUIState = StateInit
    override def update(e:Event) = {
        e match
            case Event.Start =>
                println("Start")
                println("Enter (name add/remove\nor players to list all):")
                state = StateInit
            case Event.nextPlayer =>
                println("Next Player")
                println(controler.gameState.board.toString())
                println(controler.gameState.playerActive.toString())
                println(s"Select Card to play:")
                state = StatePlayCard
            case Event.End =>
                end
                state = StateEnd
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