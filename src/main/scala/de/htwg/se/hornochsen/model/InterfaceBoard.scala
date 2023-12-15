package de.htwg.se.hornochsen.model

import de.htwg.se.hornochsen.model.BaseModel._
import de.htwg.se.hornochsen.model.InterfaceDeck

trait InterfaceRow {
    def nummer: Int
    def cards: Vector[Int]
    def filled: Int
    def value: Int
    def copy(nummer: Int = nummer, cards: Vector[Int] = cards, Filled: Int = 1): InterfaceRow
}

trait InterfaceBoard {
    def rows: Vector[InterfaceRow]
    def playedCards: Vector[(Int, InterfacePlayer)]
    def addCard(playedCard: Int, num: Int): InterfaceBoard
    def takeRow(card: Int, nummer: Int): (InterfaceBoard, Int)
    def copy(
        myRows: Vector[InterfaceRow] = rows,
        playedCards: Vector[(Int, InterfacePlayer)] = playedCards
    ): InterfaceBoard
    def playedCardsToString: String
}

def initBoard(numRows: Int, numRowCards: Int, deck: InterfaceDeck): (InterfaceBoard, InterfaceDeck) = {
    (
        Board(
        rows = Vector.tabulate(numRows)(index => {
            Row(
                Nummer = index + 1,
                myCards = Vector.tabulate(numRowCards)(count =>
                    if count == 0 then deck.getCards(index) else 0
                )
            )
        }),
        playedCards = Vector.empty
        ),
        deck.remcount(numRows)
    )
}