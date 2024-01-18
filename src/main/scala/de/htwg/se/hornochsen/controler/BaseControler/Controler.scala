package de.htwg.se.hornochsen.controler.BaseControler

import de.htwg.se.hornochsen.controler.InterfaceControler
import de.htwg.se.hornochsen.model.*
import de.htwg.se.hornochsen.modules.Default.{FileIO, defaultDeck}
import de.htwg.se.hornochsen.util.*

import scala.util.{Failure, Success, Try}


class Controler(var stateState: InterfaceGameState) extends Observable with InterfaceControler {
    var undoHistory = new History()
    var redoHistory = new History()
    var running = true
    val io = summon[FileIO]

    override def isrunning: Boolean = running

    override def setrunning(b: Boolean): Unit = running = b

    override def gameState: InterfaceGameState = stateState

    override def start(names: Vector[String]) = {
        stateState = initializeGame(defaultDeck, 4, 6, names.length, 12, names(_))
        notifyObservers(Event.nextPlayer)
    }

    override def restart = {
        notifyObservers(Event.Start)
    }

    override def doOp(input: String): Try[Boolean] = {
        input match
            case "undo" =>
                undo()
                Success(true)
            case "redo" =>
                redo()
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

    override def playCard(player: InterfacePlayer, card: Int): Boolean = {
        if redoHistory.mementos.length > 0 then redoHistory = new History()
        undoHistory.save(ConcreteMemento(stateState))
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
    
    /* 
     This Method is called from the method play card, after every player has played a card.
     First the played cards are sorted by the card value.
     Then the method checks for every card where and weather it can be placed on the board.
     If the card can't be placed at the intended row, the player ad to take this row.
     If there is no valid row to add the card, the player has to take the row with the least points.
     In the end the played cards, the active player and the players waiting are reset.
     */
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
    

    /*
     This method returns the row numbe, on which the card can be placed.
     If there is no valid row, the method returns -1.
     */
    def where(b: InterfaceBoard, card: Int): Int = {
        val lastElements: Vector[Int] = b.rows.map(row => row.cards(row.filled-1))
        val possibleRows = lastElements.filter(x => (card - x) > 0)
        if possibleRows.length == 0
        then
            return -1
        return (lastElements.indexOf(possibleRows.sortBy(x=>(card-x)).head))
    }

    // This method returns, weather the card can be placed in the row given by the where method.
    def canAdd(index: Int): Boolean = {
        if index < 0 | index >= stateState.board.rows(0).cards.length then return false
        return stateState.board.rows(index).filled < stateState.board.rows(index).cards.length
    }

    // This method is called when the player has to take a row.
    def takeRow(player: InterfacePlayer, card: Int, row: Int, statename: String = "0"): (InterfaceBoard, Int) = {
        if statename != "0" then undoHistory.save(ConcreteMemento(stateState))
        stateState.board.takeRow(card, row)
    }

    def undo()= {
        val memento = undoHistory.restore()
        memento match
        case Success(a) =>
            redoHistory.save(ConcreteMemento(stateState))
            stateState = a.restore()
            notifyObservers(Event.nextPlayer)
        case Failure(b) =>
            println(b)
    }

    def redo() = {
        val memento = redoHistory.restore()
        memento match
        case Success(a) =>
            undoHistory.save(ConcreteMemento(stateState))
            stateState = a.restore()
            notifyObservers(Event.nextPlayer)
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
