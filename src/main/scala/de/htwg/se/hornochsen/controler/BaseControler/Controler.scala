package de.htwg.se.hornochsen.controler.BaseControler
import de.htwg.se.hornochsen.controler.InterfaceControler

import de.htwg.se.hornochsen.util._
import de.htwg.se.hornochsen.model._
import de.htwg.se.hornochsen.aview._

import scala.util.Try
import scala.util.Failure
import scala.util.Success
import de.htwg.se.hornochsen.model.{InterfacePlayer, InterfaceBoard, InterfaceDeck, InterfaceGameState}
import java.util.ResourceBundle.Control
import scalafx.scene.input.KeyCode.R



def initializeGame(
    shuffle: Boolean = true,
    sizeDeck: Int = 120,
    numRows: Int = 4,
    numRowCards: Int = 6,
    numPlayer: Int = 4,
    numHandCards: Int = 12,
    input: Int => String):
        InterfaceGameState = {
            val deck = if (shuffle) {
                initDeck(sizeDeck).shuffle()
            } else {
                initDeck(sizeDeck)
            }
            val (board, playerdeck) = initBoard(numRows, numRowCards, deck)
            val (allP, refilldeck) =
                PlayerFactory.getInstance(
                    playerCount = numPlayer,
                    numHandCards = numHandCards,
                    input = input,
                    deck = playerdeck
                )
            initGameState(allP, board, refilldeck)
}

class Controler(var stateState: InterfaceGameState) extends Observable with InterfaceControler {
    var undoHistory = new History()
    var redoHistory = new History()
    var running = true
    var Rownum = -1

    override def rownum(num: Int): Try[Int] = {
        if num < 1 || num > stateState.board.rows.length then
            Failure(new IllegalArgumentException("Row does not exist"))
        else
            Rownum = num
            Success(num)
    }
    override def isrunning: Boolean = running

    override def gameState: InterfaceGameState = stateState

    override def doOp(input: String, stateName: String): Try[Boolean] = {
        input match
            case "undo" =>
                undo(stateName)
                Success(true)
            case "redo" =>
                redo(stateName)
                Success(true)
            case "end" =>
                end
                Success(true)
            case _ =>
                Failure(new java.lang.IllegalArgumentException(s"Unknown Command: ${input}"))
    }

    override def playCard(player: InterfacePlayer, card: Int, stateName: String): Boolean = {
        undoHistory.save(ConcreteMemento(stateState, stateName))
        val canplay = player.canPlay(card)
        canplay match
        case true =>
            val newp = player.playCard(card)
            val newGameState = Try(
                    stateState.copy(
                    Board = stateState.board.copy(playedCards=stateState.board.playedCards.appended((card, newp))),
                    playersdone = stateState.playersDone.appended(newp),
                    playeractive = stateState.playersWaiting.head,
                    playerswaiting = stateState.playersWaiting.tail
                    )
                )
            newGameState match
            case Success(a) =>
                stateState = a
            case Failure(b) =>
                stateState = 
                    stateState.copy(
                    Board = stateState.board.copy(playedCards=stateState.board.playedCards.appended((card, newp))),
                    playersdone = stateState.playersDone.appended(newp),
                    playeractive = makePlayer(),
                    playerswaiting = Vector.empty
                )
            if stateState.playerActive == makePlayer() then
                if stateState.playersDone(0).getCards.length == 0 then
                    notifyObservers(Event.End)
                stateState = stateState.copy(
                    playeractive = makePlayer(),
                    playerswaiting = stateState.playersDone,
                    playersdone = Vector.empty
                    )
                placeCards()
                stateState.board.copy(myRows = stateState.board.rows, playedCards=Vector.empty)
                notifyObservers(Event.nextPlayer)
            else 
                notifyObservers(Event.nextPlayer)
            true
        case false =>
            false
    }

    def placeCards(): Unit = {
        stateState = stateState.copy(Board=stateState.board.copy(myRows=stateState.board.rows, playedCards=stateState.board.playedCards.sortBy((card: Int, player: InterfacePlayer) => card: Int)))
        stateState.board.playedCards.foreach{ p => val player = p._2; val card = p._1
            stateState = stateState.copy(
                playeractive = player,
                Board=stateState.board.copy(playedCards = stateState.board.playedCards.filter((c, p) => p != player))
            )
            if stateState.playerActive != makePlayer() then
                val index: Int = where(stateState.board, card)
                index match 
                case -1 =>
                    notifyObservers(Event.TakeRow)
                case _ =>
                    if (canAdd(index)) then
                        addCard(card, index)
                        player.playCard(card)
                    else
                        takeRow(player, index)
                stateState = stateState.copy(playersdone=stateState.playersDone.appended(player))
        }
        stateState = stateState.copy(
            playeractive = stateState.playersDone.head,
            playerswaiting = stateState.playersDone.tail,
            playersdone = Vector.empty
        )
    }

    def where(b: InterfaceBoard, card: Int): Int = {
        val lastElements: Vector[Int] = b.rows.map(row => row.cards(row.filled-1))
        val possibleRows = lastElements.filter(x => (card - x) > 0)
        if possibleRows.length == 0
        then
            return -1
        return (lastElements.indexOf(possibleRows.sortBy(x=>(card-x)).head))
    }

    def canAdd(index: Int): Boolean = {
        if index < 0 | index >= stateState.board.rows(0).cards.length then return false
        return stateState.board.rows(index).filled < stateState.board.rows(index).cards.length
    }

    def addCard(card: Int, index: Int): Unit = {
        stateState = stateState.copy(
            Board = stateState.board.addCard(card, index+1)
        )
    }

    def takeRow(player: InterfacePlayer, row: Int, statename: String = "0"): Try[Boolean] = {
        if statename != "0" then undoHistory.save(ConcreteMemento(stateState, statename))
        if (row < 0 | row > stateState.board.rows.length) then Failure(new java.lang.IllegalArgumentException(s"Player ${stateState.playerActive.name} can't take Row ${row}"))
        val (newBoard, moreOchsen) = stateState.board.takeRow(
            stateState.board.playedCards.filter((c, p) => p == player).head._1, row
            )
        stateState = stateState.copy(
            playeractive = player.addOchsen(moreOchsen),
            Board = newBoard
            )
        Success(true)
    }

    def undo(state: String)= {
        val memento = undoHistory.restore()
        memento match
        case Success(a) =>
            redoHistory.save(ConcreteMemento(stateState, state))
            stateState = a.restore()
            notifyObservers(Event.Undo, a.stateName)
        case Failure(b) =>
            println(b)
    }

    def redo(state: String) = {
        val memento = redoHistory.restore()
        memento match
        case Success(a) =>
            undoHistory.save(ConcreteMemento(stateState, state))
            stateState = a.restore()
            notifyObservers(Event.Redo, a.stateName)
        case Failure(b) =>
            println(b)
    }

    def end = notifyObservers(Event.End)
}

object PlayerFactory {
    def getInstance(playerCount: Int, numHandCards: Int, input: (index: Int) => String, deck: InterfaceDeck): 
        (Vector[InterfacePlayer], InterfaceDeck) = {
        (
            Vector.tabulate(playerCount)(index => {
            makePlayer(
                name = input(index + 1),
                cards = Vector.tabulate(numHandCards)(count =>
                deck.getCards(count + numHandCards * index)
                ),
                ochsen = 0
            )
            }),
            deck.remcount(numHandCards * playerCount)
        )
    }
}
