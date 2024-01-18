package de.htwg.se.hornochsen.model

import de.htwg.se.hornochsen.model.BaseModel.*
import play.api.libs.json.*

trait InterfaceRow {
    def nummer: Int
    def cards: Vector[Int]
    def filled: Int
    /*
     retuns the Ochsen value of the row
     if card is 0, it is not counted
     if card % 10 == 0 then 10 else if card % 5 == 0 then 5 else 1
     */
    def value: Int
    def copy(nummer: Int = nummer, cards: Vector[Int] = cards, Filled: Int = 1): InterfaceRow
    def saveToXML(): xml.Elem
    def loadFromXML(xml: scala.xml.Node): InterfaceRow
    def saveToJson(): JsValue
    def loadFromJson(json: JsValue): InterfaceRow
}

trait InterfaceBoard {
    def rows: Vector[InterfaceRow]
    // Returns Touples of (playedCard, player)
    def playedCards: Vector[(Int, InterfacePlayer)]
    // adds a card to the row
    def addCard(playedCard: Int, num: Int): InterfaceBoard
    // returns the row number, on which the card can be placed and the placed card is added to teh Row
    def takeRow(card: Int, nummer: Int): (InterfaceBoard, Int)
    def copy(
        myRows: Vector[InterfaceRow] = rows,
        playedCards: Vector[(Int, InterfacePlayer)] = playedCards
    ): InterfaceBoard
    def playedCardsToString: String
    def saveToXML(): xml.Elem
    def loadFromXML(xml: scala.xml.Node): InterfaceBoard
    def saveToJson: JsValue
    def loadFromJson(json: JsValue): InterfaceBoard
}

def makeDummyBoard(): InterfaceBoard = {
    Board(rows = Vector.empty, playedCards = Vector.empty)
}

def makeDummyRow(): InterfaceRow = {
    Row(Nummer = 0, myCards = Vector.empty, Filled = 0)
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