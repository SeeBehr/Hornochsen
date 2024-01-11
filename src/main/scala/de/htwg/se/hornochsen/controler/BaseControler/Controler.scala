package de.htwg.se.hornochsen.controler.BaseControler

import de.htwg.se.hornochsen.controler.InterfaceControler
import de.htwg.se.hornochsen.util._
import de.htwg.se.hornochsen.model._
import de.htwg.se.hornochsen.aview._

import scala.util.{Try,Success,Failure}
import play.api.libs.json._
import de.htwg.se.hornochsen.modules.Default.FileIO
import de.htwg.se.hornochsen.modules.Default.defaultDeck


class Controler(var stateState: InterfaceGameState) extends Observable with InterfaceControler {
    var undoHistory = new History()
    var redoHistory = new History()
    var running = true
    val io = summon[FileIO]

    override def isrunning: Boolean = running

    override def gameState: InterfaceGameState = stateState

    override def start(names: Vector[String]) = {
        stateState = initializeGame(defaultDeck, 4, 6, names.length, 12, names(_))
        notifyObservers(Event.nextPlayer)
    }
    override def doOp(input: String, stateName: String): Try[Boolean] = {
        input match
            case "undo" =>
                undo(stateName)
                Success(true)
            case "redo" =>
                redo(stateName)
                Success(true)
            case "save" =>
                save
                Success(true)
            case "load" =>
                load
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
                ) match
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
                stateState = stateState.copy(
                    playeractive = stateState.playersDone.head,
                    playerswaiting = stateState.playersDone.tail,
                    playersdone = Vector.empty
                    )
                placeCards()
                if stateState.playerActive.getCards.length == 0 then
                    notifyObservers(Event.End)
                else
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
                playeractive = player
            )
            val index: Int = where(stateState.board, card)

            val (b: InterfaceBoard, o: Int) =
                if (index == -1) {
                    val take: Int = gameState.board.rows.map((num: InterfaceRow) => (num.value, num.nummer)).min((a,b) => a._1 - b._1)._2
                    takeRow(player, card, take)
                } else {
                    if (canAdd(index)) {
                        (stateState.board.addCard(card, index+1), 0)
                    } else {
                        takeRow(player, card, index+1)
                    }
                }
            stateState = stateState.copy(
                Board = b,
                playersdone = stateState.playersDone.appended(player.addOchsen(o))
            )
        }
        stateState = stateState.copy(
            Board = stateState.board.copy(playedCards = Vector.empty),
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

    def takeRow(player: InterfacePlayer, card: Int, row: Int, statename: String = "0"): (InterfaceBoard, Int) = {
        if statename != "0" then undoHistory.save(ConcreteMemento(stateState, statename))
        stateState.board.takeRow(card, row)
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

    override def save: Unit = {
        io.save(stateState)
    }

    override def load: Unit = {
        stateState = io.load(stateState)
        notifyObservers(Event.nextPlayer)
    }
}

def initializeGame(
    deck: InterfaceDeck,
    numRows: Int = 4,
    numRowCards: Int = 6,
    numPlayer: Int = 4,
    numHandCards: Int = 12,
    input: Int => String):
        InterfaceGameState = {
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

object PlayerFactory {
    def getInstance(playerCount: Int, numHandCards: Int, input: (index: Int) => String, deck: InterfaceDeck): 
        (Vector[InterfacePlayer], InterfaceDeck) = {
        (
            Vector.tabulate(playerCount)(index => {
            makePlayer(
                name = input(index),
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
