package de.htwg.se.hornochsen.model

import de.htwg.se.hornochsen.model.BaseModel.Deck
import play.api.libs.json._

trait InterfaceDeck {
    def shuffle(): InterfaceDeck
    def remcount(count: Int): InterfaceDeck
    def getCards: Vector[Int]
    def toXml(): String
    def toJSON: JsValue
}

def initDeck(number: Int): InterfaceDeck = Deck{Vector.tabulate(number)(x => x+1)}