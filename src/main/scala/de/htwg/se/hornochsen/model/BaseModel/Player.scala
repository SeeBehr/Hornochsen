package de.htwg.se.hornochsen.model.BaseModel

import de.htwg.se.hornochsen.model.InterfacePlayer
import play.api.libs.json._
import de.htwg.se.hornochsen.model.makePlayer

case class Player(val Name: String = "", val Cards: Vector[Int] = Vector.empty[Int], val Ochsen: Int = 0) extends InterfacePlayer {
    override def toString(): String = {
        name + ":\n\tcards: " + Cards.mkString(", ") + "\n\tOchsen: " + ochsen.toString() + "\n"
    }

    override def getCards: Vector[Int] = Cards

    override def playCard(cardNr: Int): Player = {
        Player(Name=this.Name, Cards=Cards.filterNot(_ == cardNr), Ochsen=Ochsen)
    }

    override def addOchsen(anzahlOchsen: Int): Player = {
      	Player(Name = this.Name, Cards = this.Cards,Ochsen=Ochsen + anzahlOchsen)
    }

    override def drawCards(cards: Vector[Int]): Player = {
        Player(name, this.Cards.appendedAll(cards), ochsen)
    }

    override def canPlay(card: Int): Boolean = {
        Cards.contains(card)
    }

    override def name = Name

    override def ochsen = Ochsen

    override def saveToJson: JsValue = {
        Json.obj(
            "name" -> name,
            "cards" -> Cards,
            "ochsen" -> ochsen
        )
    }

    override def loadFromJson(json:JsValue): InterfacePlayer = {
        val name = (json \ "name").as[String]
        val cards = (json \ "cards").as[Vector[Int]]
        val ochsen = (json \ "ochsen").as[Int]
        Player(name, cards, ochsen)
    }

    override def saveToXML(): String = {
        val cardXml = Cards.map{card => s"<card>${card}</card>" }.mkString;
        
        s"<person>" +
          s"<name>${name}</name>" +
          s"<cards>${cardXml}</cards>" +
          s"<ochsen>${ochsen}</ochsen>" +
        s"</person>"
    }

    override def loadFromXML(xml: scala.xml.Node): InterfacePlayer = {
        return makePlayer()
    }
}