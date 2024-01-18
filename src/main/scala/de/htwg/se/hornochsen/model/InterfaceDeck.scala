package de.htwg.se.hornochsen.model

import de.htwg.se.hornochsen.model.BaseModel.Deck
import play.api.libs.json._

trait InterfaceDeck {
    def shuffle(): InterfaceDeck
    // removes count cards from the deck and returns them
    def remcount(count: Int): InterfaceDeck
    def getCards: Vector[Int]
    def saveToXML(): xml.Elem
    def loadFromXML(xml: scala.xml.Node): InterfaceDeck
    def saveToJson: JsValue
    def loadFromJson(json: JsValue): InterfaceDeck
}

def makeDummyDeck(): InterfaceDeck = Deck(Vector.empty)

def initDeck(number: Int): InterfaceDeck = Deck{Vector.tabulate(number)(x => x+1)}