package de.htwg.se.hornochsen.model

import de.htwg.se.hornochsen.model.BaseModel.Player
import play.api.libs.json._

trait InterfacePlayer {
    def playCard(cardNr: Int): InterfacePlayer
    def addOchsen(anzahlOchsen: Int): InterfacePlayer
    def drawCards(cards: Vector[Int]): InterfacePlayer
    def canPlay(card: Int): Boolean
    def getCards: Vector[Int]
    def name: String
    def ochsen: Int
    def saveToXML(): xml.Elem
    def loadFromXML(xml: scala.xml.Node): InterfacePlayer
    def saveToJson: JsValue
    def loadFromJson(json: JsValue): InterfacePlayer
}
def makePlayer(name: String = "", cards: Vector[Int] = Vector.empty[Int], ochsen: Int = 0): InterfacePlayer = {
    Player(name, cards.sorted, ochsen)
}