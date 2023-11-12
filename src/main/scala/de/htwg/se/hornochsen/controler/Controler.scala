package de.htwg.se.hornochsen.controler

import de.htwg.se.hornochsen.util._
import de.htwg.se.hornochsen.model._
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

    def updateGamestate(WhichRowTake: (String, () => String) => Int): GameState = {
        var tempboard = gameState.board
        val update: Vector[Player] =
            gameState.board.playedCards
        .map[Player]((card, player) =>
            val index: Int = where(tempboard, card)
            val update =(
                if index == -1
                then
                    val nim: Int = WhichRowTake(player.name, readLine)
                    val (board: Board, ochsen: Int) = tempboard.takeRow(card, nim)
                    val updatedPlayer: Player = player.playCard(card).addOchsen(ochsen)
                    (board: Board, updatedPlayer: Player)
                else 
                    if !canAdd(tempboard, index)
                    then
                        val (board: Board, ochsen: Int) = tempboard.takeRow(card, index+1)
                        val updatedPlayer: Player = player.playCard(card).addOchsen(ochsen)
                        (board: Board, updatedPlayer: Player)
                    else
                        val board: Board = tempboard.addCard(card, index+1)
                        val updatedPlayer: Player = player.playCard(card)
                        (board: Board, updatedPlayer: Player))
            tempboard = update._1
            update._2
        )
        val newGameState = GameState(players=update, board=tempboard, remDeck=gameState.remDeck)
        newGameState
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

def initAllPlayers(numPlayer: Int, numHandCards: Int, input: (index: Int) => String, deck: Deck):
    (Vector[Player], Deck) = {
    (
        Vector.tabulate(numPlayer)(index => {
        Player(
            name = input(index + 1),
            cards = Vector.tabulate(numHandCards)(count =>
            deck.cards(count + numHandCards * index)
            ),
            ochsen = 0
        )
        }),
        deck.remcount(numHandCards * numPlayer)
    )
}