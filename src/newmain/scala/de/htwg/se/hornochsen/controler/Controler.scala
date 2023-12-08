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
                if gamestate.playersWaiting.isEmpty then
                    placeCards()
                 else 
                    controler.notifyObservers(Event.nextPlayer)
            true
        case false =>
            false
    }

    def placeCards(): Unit = {
        gamestate = gamestate.copy(
            playersWaiting = gamestate.playersActive.appended(gamestate.playersDone),
            playersDone = Vector.empty,
            board = gamestate.board.copy(playedCards=Vector.empty)
        )
        val sorted = gamestate.board.playedCards.sortBy((card: Int, player: Player) => card: Int)
        for (card, player) <- sorted do
            gameState = gameState.copy(
                playersActive = gameState.playersActive.updated(gameState.playersActive = gameState.playersWaiting.indexOf(player))
                playersWaiting = gameState.playersWaiting.updated(gameState.playersWaiting.filterNot(_ == player))
            )
            val index: Int = where(gamestate.board, card)
            index match 
            case -1 => 
                notifyObservers(Event.TakeRow)
            case _ =>
                if (canAdd(index)) then
                    addCard(card, index)
                else
                    takeRow(player, index)
    }

    def where(b: Board, card: Int): Int = {
        val lastElements: Vector[Int] = b.rows.map(row => row.cards(row.filled-1))
        val possibleRows = lastElements.filter(x => (card - x) > 0)
        if possibleRows.length == 0
        then
            return -1
        return (lastElements.indexOf(possibleRows.sortBy(x=>(card-x)).head))
    }

    def canAdd(index: Int): Boolean = {
        if index < 0 | index >= b.rows(0).cards.length then return false
        return b.rows(index).filled < b.rows(index).cards.length
    }

    def addCard(card: Int, index: Int): Unit = {
        gamestate = gamestate.copy(
            board = gamestate.board.addCard(card, index+1)
        )
        notifyObservers(Event.CardsSelected)
    }

    def takeRow(player: Player, row: Int): Boolean = {
        val canTake = player.canTake(row)
        canTake match
        case true =>
            p.takeRow(row)
            gamestate = gamestate.copy(
                )
            true
        case false =>
            false
    }
}