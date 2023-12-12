package de.htwg.se.hornochsen.model

import de.htwg.se.hornochsen.model.BaseModel.Deck

trait InterfaceDeck {
    def shuffle(): InterfaceDeck
    def remcount(count: Int): InterfaceDeck
    def getCards: Vector[Int]
}

def initDeck(number: Int): InterfaceDeck = Deck{Vector.tabulate(number)(x => x+1)}