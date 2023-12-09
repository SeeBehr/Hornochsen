package de.htwg.se.hornochsen.controler

import de.htwg.se.hornochsen.util._
import de.htwg.se.hornochsen.model._
import de.htwg.se.hornochsen.aview._

import scala.util.Try
import scala.util.Failure
import scala.util.Success



def initializeGame(
    shuffle: Boolean = true,
    sizeDeck: Int = 120,
    numRows: Int = 4,
    numRowCards: Int = 6,
    numPlayer: Int = 4,
    numHandCards: Int = 12,
    input: Int => String):
        GameState = {
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



class Controler(var gameState: GameState) extends Observable {
    var undoHistory = new History()
    var redoHistory = new History()
    var running = true

    def doOp(input: String, stateName: String): Try[Boolean] = {
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

    def playCard(player: Player, card: Int, stateName: String): Boolean = {
        undoHistory.save(ConcreteMemento(gameState, stateName))
        val canplay = player.canPlay(card)
        canplay match
        case true =>
            val newp = player.playCard(card)
            val newGameState = Try(
                    gameState.copy(
                    board = gameState.board.copy(playedCards=gameState.board.playedCards.appended((card, newp))),
                    playersDone = gameState.playersDone.appended(newp),
                    playerActive = gameState.playersWaiting.head,
                    playersWaiting = gameState.playersWaiting.tail
                    )
                )
            newGameState match
            case Success(a) =>
                gameState = a
            case Failure(b) =>
                gameState = 
                    gameState.copy(
                    board = gameState.board.copy(playedCards=gameState.board.playedCards.appended((card, newp))),
                    playersDone = gameState.playersDone.appended(newp),
                    playerActive = Player(),
                    playersWaiting = Vector.empty
                )
                println("Played Cards: " + gameState.board.playedCards.toString()) 
            if gameState.playerActive == Player() then
                if gameState.playersDone(0).cards.length == 0 then
                    notifyObservers(Event.End)
                gameState = gameState.copy(
                    playerActive = gameState.playersDone.head,
                    playersWaiting = gameState.playersDone.tail,
                    playersDone = Vector.empty
                    )
                placeCards()
                gameState.board.playedCards = Vector.empty
            else 
                notifyObservers(Event.nextPlayer)
            true
        case false =>
            false
    }

    def placeCards(): Unit = {
        println("Place Cards")
        gameState.board.playedCards = gameState.board.playedCards.sortBy((card: Int, player: Player) => card: Int)
        gameState.board.playedCards.foreach{ p => val player = p._2; val card = p._1
            gameState = gameState.copy(
                playerActive = Try(gameState.board.playedCards.head._2) match {
                    case Success(a) => gameState.board.playedCards.head._2
                    case Failure(b) => Player()
                },
            )
            if gameState.playerActive != Player() then
                val index: Int = where(gameState.board, card)
                index match 
                case -1 =>
                    notifyObservers(Event.TakeRow)
                case _ =>
                    if (canAdd(index)) then
                        addCard(card, index)
                        player.playCard(card)
                    else
                        takeRow(player, index)
                gameState.playersDone = gameState.playersDone.appended(gameState.playerActive)
        }
        gameState = gameState.copy(
            playerActive = gameState.playersDone.head,
            playersWaiting = gameState.playersDone.tail,
            playersDone = Vector.empty
        )
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
        if index < 0 | index >= gameState.board.rows(0).cards.length then return false
        return gameState.board.rows(index).filled < gameState.board.rows(index).cards.length
    }

    def addCard(card: Int, index: Int): Unit = {
        gameState = gameState.copy(
            board = gameState.board.addCard(card, index+1)
        )
    }

    def takeRow(player: Player, row: Int, statename: String = "0"): Try[Boolean] = {
        if statename != "0" then undoHistory.save(ConcreteMemento(gameState, statename))
        if (row < 0 | row > gameState.board.rows.length) then Failure(new java.lang.IllegalArgumentException(s"Player ${gameState.playerActive.name} can't take Row ${row}"))
        val (newBoard, moreOchsen) = gameState.board.takeRow(
            gameState.board.playedCards.filter((c, p) => p == player).head._1, row
            )
        println("Ochsen: " + moreOchsen.toString)
        gameState = gameState.copy(
            playerActive = player.addOchsen(moreOchsen),
            board = newBoard
            )
        Success(true)
    }

    def undo(state: String)= {
        val memento = undoHistory.restore()
        memento match
        case Success(a) =>
            redoHistory.save(ConcreteMemento(gameState, state))
            gameState = a.restore()
            notifyObservers(Event.Undo, a.stateName)
        case Failure(b) =>
            println(b)
    }

    def redo(state: String) = {
        val memento = redoHistory.restore()
        memento match
        case Success(a) =>
            undoHistory.save(ConcreteMemento(gameState, state))
            gameState = a.restore()
            notifyObservers(Event.Redo, a.stateName)
        case Failure(b) =>
            println(b)
    }

    def end = notifyObservers(Event.End)
}
def initDeck(number: Int): Deck = Deck(Vector.tabulate(number)(x => x+1))

def initBoard(numRows: Int, numRowCards: Int, deck: Deck): (Board, Deck) = {
    (
        Board(
        rows = Vector.tabulate(numRows)(index => {
            Row(
            nummer = index + 1,
            cards = Vector.tabulate(numRowCards)(count =>
                if count == 0 then deck.cards(index) else 0
            )
            )
        }),
        playedCards = Vector.empty
        ),
        deck.remcount(numRows)
    )
}

object PlayerFactory {
    def getInstance(playerCount: Int, numHandCards: Int, input: (index: Int) => String, deck: Deck): 
        (Vector[Player], Deck) = {
        (
            Vector.tabulate(playerCount)(index => {
            Player(
                name = input(index + 1),
                cards = Vector.tabulate(numHandCards)(count =>
                deck.cards(count + numHandCards * index)
                ),
                ochsen = 0
            )
            }),
            deck.remcount(numHandCards * playerCount)
        )
    }
}

def initGameState(allP: Vector[Player], board: Board, deck: Deck): GameState = {
    GameState(
        playersWaiting = allP.tail,
        playerActive = allP.head,
        playersDone = Vector.empty,
        board = board,
        remDeck = deck
    )

}