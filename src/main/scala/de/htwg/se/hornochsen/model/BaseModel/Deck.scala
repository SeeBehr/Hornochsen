package de.htwg.se.hornochsen.model.BaseModel

import de.htwg.se.hornochsen.model.InterfaceDeck
import scala.util.Random
import play.api.libs.json._

case class Deck(cards: Vector[Int]) extends InterfaceDeck {
    def shuffle(): Deck = {
        Deck(Random.shuffle(cards))
    }
    def remcount(count: Int): Deck = {
        Deck(this.cards.drop(count))
    }
    override def toString(): String = {
        "Deck: " + cards.mkString(", ") + "\n"
    }
    
    override def getCards: Vector[Int] = cards

    
    override def saveToJson: JsValue = {
        Json.obj(
            "cards" -> cards
        )
    }

    override def loadFromJson(json: JsValue): InterfaceDeck = {
        val cards = (json \ "cards").as[Vector[Int]]
        Deck(cards)
    }
    
    override def saveToXML(): xml.Elem = {
        val cardsXml = cards.map{card => <card>{card}</card>}
        
        <deck>
          <cards>{cardsXml}</cards>
        </deck>
    }

    override def loadFromXML(xml: scala.xml.Node): InterfaceDeck = {
        val cards = (xml \ "cards" \ "card").map(_.text.toInt).toVector
        Deck(cards)
    }
}
