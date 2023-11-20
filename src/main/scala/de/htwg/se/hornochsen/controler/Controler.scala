package de.htwg.se.hornochsen.controler

import de.htwg.se.hornochsen.util._
import de.htwg.se.hornochsen.model._
import de.htwg.se.hornochsen.controler._
import scala.io.StdIn.readLine

class Controler(var gameState: GameState) extends Observable{
    def which(cards: Vector[(Int, Player)]):
        ((Int, Player), Vector[(Int, Player)]) = {
        var min = cards.min((x, y) => x._1 - y._1)
        return (min, cards.filter(x => x._1 != min._1))
    }

    def where(b: Board, card: Int): Int = {
        val lastElements: Vector[Int] = b.rows.map(row => row.cards(row.filled-1))
        val possibleRows = lastElements.filter(x => (card - x) > 0)
        if possibleRows.length == 0
        then
            return -1
        return (lastElements.indexOf(possibleRows.sortBy(x=>(card-x)).head))
    }

    def canAdd(b: Board, index: Int): Boolean = {
        if index < 0 | index >= b.rows(0).cards.length then return false
        return b.rows(index).filled < b.rows(index).cards.length
    }

    def updatePlayedCards(cardsToPlay: Vector[(Int, Player)]): GameState = {
        println("Ausgewählte karten: " + cardsToPlay.toString())
        val sorted = cardsToPlay.sortBy((card: Int, player: Player) => card: Int)
        return GameState(players=gameState.players, board=gameState.board.copy(playedCards=sorted), remDeck=gameState.remDeck)
    }

    def updateGamestate(read: () => String, WhichRowTake: (String, () => String) => Int): GameState = {
        var tempboard = gameState.board
        val update: Vector[Player] =
            gameState.board.playedCards
        .map[Player]((card, player) =>
            val state = ActionState
            val index: Int = where(tempboard, card)
            val execute = (
                if index == -1
                then
                    state.handle(ActionEvent.selectRow)
                else 
                    if !canAdd(tempboard, index)
                    then
                        state.handle(ActionEvent.takeRow)
                    else
                        state.handle(ActionEvent.addCard))
            val update = execute(tempboard, card, index, player, WhichRowTake, read)                

            tempboard = update._1
            update._2
        )
        val newGameState = GameState(players=update, board=tempboard, remDeck=gameState.remDeck)
        newGameState
    }

    def giveCards(cardCount: Int = 6): GameState = {
        val toBeDropped = this.gameState.players.length * cardCount

        val drawnCards = this.gameState.remDeck.cards.take(toBeDropped)
        val remDeck = this.gameState.remDeck.remcount(toBeDropped)

        val newPlayers = this.gameState.players.zipWithIndex.map((indexAndPlayer) => {
            indexAndPlayer._1.drawCards(drawnCards.slice(indexAndPlayer._2 * cardCount, (indexAndPlayer._2 + 1) * cardCount))
        })
        GameState(players = newPlayers, board = this.gameState.board, remDeck = remDeck)
    }
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