package de.htwg.se.hornochsen.controler

import de.htwg.se.hornochsen.model._
import scala.annotation.retains

def which(
    cards: Vector[(Int, Player)]
): ((Int, Player), Vector[(Int, Player)]) = {
    var min = cards.min((x, y) => x._1 - y._1)
    return (min, cards.filter(x => x._1 != min._1))
}

def where(b: Board, card: Int): Int = {
    val lastElements: Vector[Int] = b.rows.map(row => row.cards(row.filled-1))
    val possibleRows = lastElements.filter(x => (card - x) > 0)
    println("länge mögliche reihen: " + possibleRows.length)
    if possibleRows.length == 0
    then
        return -1
    println("index richtige reihe: " + lastElements.indexOf(possibleRows.sortBy(x=>x).head))
    return (lastElements.indexOf(possibleRows.sortBy(x=>x).head))
}

def canAdd(b: Board, index: Int): Boolean = {
    if index < 0 | index >= b.rows(0).cards.length then return false
    return b.rows(index).filled < b.rows(index).cards.length
}

def initDeck(number: Int): Deck = Deck(Vector.tabulate(number)(x => x))

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

def initAllPlayers(
    numPlayer: Int,
    numHandCards: Int,
    input: (index: Int) => String,
    deck: Deck
): (Vector[Player], Deck) = {
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

def updateGamestate(
    gameState: GameState,
    cardsToPlay: Vector[(Int, Player)],
    WhichRowTake: String => Int
): GameState = {
    //val cardsToPlay = playCards(gameState.players)
    print(cardsToPlay)
    val update: Vector[Player] =
        cardsToPlay.sortBy((card: Int, player: Player) => card: Int)

    .map[Player]((card, player) =>
        val index: Int = where(gameState.board, card)
        val update =(
            if index == -1
            then
                val nim = WhichRowTake(player.name)
                val (board, ochsen) = gameState.board.takeRow(card, nim)
                val updatedPlayer = player.playCard(card).addOchsen(ochsen)
                (board, updatedPlayer)
            else 
                if !canAdd(gameState.board, index)
                then
                    val (board, ochsen) = gameState.board.takeRow(card, index+1)
                    val updatedPlayer = player.playCard(card).addOchsen(ochsen)
                    (board, updatedPlayer)
                    else
                        val board = gameState.board.addCard(card, index+1)
                        val updatedPlayer = player.playCard(card)
                        (board, updatedPlayer))
            gameState.board = update._1
            update._2
    )
    GameState(players=update, board=gameState.board, remDeck=gameState.remDeck)
}