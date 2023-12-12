package de.htwg.se.hornochsen.model

import de.htwg.se.hornochsen.model.BaseModel._
import de.htwg.se.hornochsen.model.InterfaceDeck

trait InterfaceBoard {
    def rows: Vector[Row]
    def playedCards: Vector[(Int, InterfacePlayer)]
    def addCard(playedCard: Int, num: Int): InterfaceBoard
    def takeRow(card: Int, nummer: Int): (InterfaceBoard, Int)
    def copy(
        myRows: Vector[Row] = rows,
        playedCards: Vector[(Int, InterfacePlayer)] = playedCards
    ): InterfaceBoard
    def playedCardsToString: String
}

def initBoard(numRows: Int, numRowCards: Int, deck: InterfaceDeck): (InterfaceBoard, InterfaceDeck) = {
    (
        Board(
        rows = Vector.tabulate(numRows)(index => {
            Row(
                nummer = index + 1,
                cards = Vector.tabulate(numRowCards)(count =>
                    if count == 0 then deck.getCards(index) else 0
                )
            )
        }),
        playedCards = Vector.empty
        ),
        deck.remcount(numRows)
    )
}