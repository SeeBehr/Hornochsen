package de.htwg.se.hornochsen.model

import de.htwg.se.hornochsen.model.BaseModel.Deck
import play.api.libs.json._

trait InterfaceDeck {
    def shuffle(): InterfaceDeck
    def remcount(count: Int): InterfaceDeck
    def getCards: Vector[Int]
    def saveToXML(): String
    def loadFromXML(xml: scala.xml.Node): InterfaceDeck
    def saveToJson: JsValue
    def loadFromJson(json: JsValue): InterfaceDeck
}

def makeDummyDeck(): InterfaceDeck = Deck(Vector.empty)

def initDeck(number: Int): InterfaceDeck = Deck{Vector.tabulate(number)(x => x+1)}