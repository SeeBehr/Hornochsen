package de.htwg.se.hornochsen.controler

import de.htwg.se.hornochsen.util._
import de.htwg.se.hornochsen.model._
import de.htwg.se.hornochsen.aview._

import scala.util.Try
import scala.util.Failure
import scala.util.Success

class Controler(var gameState: GameState) extends Observable {
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
            input.toInt
        )
        action match
        case Success(a) =>

        
    }

    def playCard(player: Player, card: Int): Boolean = {
        val canplay = player.canPlay(card)
        canplay match
        case true =>
            player.playCard(card)
            gameState = gameState.copy(
                playersDone = gameState.playersDone.appended(player),
                playerActive = gameState.playersWaiting.head,
                playersWaiting = gameState.playersWaiting.tail,
                board = gameState.board.copy(playedCards=gameState.board.playedCards.appended((card, gameState.playerActive)))
                )
                if gameState.playersWaiting.isEmpty then
                    placeCards()
                else 
                    notifyObservers(Event.nextPlayer)
            true
        case false =>
            false
    }

    def placeCards(): Unit = {
        gameState = gameState.copy(
            playersWaiting = gameState.playersDone,
            playersDone = Vector.empty,
            board = gameState.board.copy(playedCards=Vector.empty)
        )
        gameState.board.playedCards = gameState.board.playedCards.sortBy((card: Int, player: Player) => card: Int)
        for (card, player) <- gameState.board.playedCards do
            val index: Int = where(gameState.board, card)
            index match 
            case -1 =>
                notifyObservers(Event.TakeRow)
            case _ =>
                if (canAdd(index)) then
                    addCard(card, index)
                else
                    takeRow(player, index)
            gameState = gameState.copy(
                playersDone = gameState.playersDone.appended(player),
                playerActive = player,
                playersWaiting = gameState.playersWaiting.filterNot(_ == player)
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

    def takeRow(player: Player, row: Int): Try[Boolean] = {
        if (row < 0 | row > gameState.board.rows.length) then Failure(new java.lang.IllegalArgumentException(s"Player ${gameState.playerActive.name} can't take Row ${row}"))
        val (board, ochsen) = gameState.board.takeRow(
            gameState.board.playedCards.filter((c, p) => p == player).head._1, row
            )
            gameState.playerActive = player.addOchsen(ochsen)
        gameState = gameState.copy(
            playersDone = gameState.playersWaiting.appended(gameState.playerActive),
            playerActive = gameState.playersWaiting.head,
            playersWaiting = gameState.playersWaiting.tail,
            board = board
            )
        Success(true)
    }

    def undo = ???

    def redo = ???

    def end = ???
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

def initializeGame(shuffle: Boolean = true, sizeDeck: Int = 120, numRows: Int = 4, numRowCards: Int = 6, numPlayer: Int = 4, numHandCards: Int = 6, input: Int => String): GameState = {
    val deck = if (shuffle) {
        initDeck(sizeDeck).shuffle()
    } else {
        initDeck(sizeDeck)
    }
    val (board, playerdeck) = initBoard(numRows, numRowCards, deck)
    val (allP, refilldeck) = PlayerFactory.getInstance(playerCount = numPlayer, numHandCards = numHandCards, input = input, deck = playerdeck)
    initGameState(allP, board, refilldeck)
}