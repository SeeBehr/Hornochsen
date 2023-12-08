import de.htwg.se.hornochsen.util._
import de.htwg.se.hornochsen.model._
import de.htwg.se.hornochsen.controler._
import de.htwg.se.hornochsen.aview._
import scala.io.StdIn.readLine

import scala.util.Try
import scala.util.Failure
import scala.util.Success

class Controler(var gamestate: GameState) extends Observable {
    var undoHistory = new History()
    var redoHistory = new History()
    var running = true

    def doAction(input: String): Unit = {
        input match
            case "undo" => undo
            case "redo" => redo
            case "end" => end
            case _ => move(input)
    }

    def move(input: String): Unit = {
        val action:Try[Int] = Try(
            action = input.toInt
        )
        action match
        case Success(a) =>

        
    }

    def playCard(player: PLayer, card: Int): Boolean = {
        val canplay = player.canPlay(card)
        canplay match
        case true =>
            p.playCard(card)
            gamestate = gamestate.copy(
                playersDone = gamestate.playersDone.appended(p),
                playerActive = gamestate.playersWaiting.head,
                playersWaiting = gamestate.playersWaiting.tail,
                board = gamestate.board.copy(playedCards=gamestate.board.playedCards.append((card, p)))
                )
                controler.notifyObservers(Event.nextPlayer)
            true
        case false =>
            false
    }
}