package de.htwg.se.hornochsen.model

case class Player(val name: String, val cards: Vector[Int], val ochsen: Int = 0) {
    override def toString(): String = {
        name + ":\n\tcards: " + cards.mkString(", ") + "\n\tOchsen: " + ochsen.toString() + "\n"
    }

    def playCard(cardNr: Int): Player = {
        Player(name=name, cards=cards.filterNot(_ == cardNr), ochsen=ochsen)
    }
    def addOchsen(anzahlOchsen: Int): Player = {
      	Player(name = this.name, cards = this.cards,ochsen=ochsen + anzahlOchsen)
    }

    def drawCards(cards: Vector[Int]): Player = {
        Player(name, cards.appendedAll(cards), ochsen)
    }
}
