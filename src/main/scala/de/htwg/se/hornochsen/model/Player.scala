package de.htwg.se.hornochsen.model

case class Player(val name: String, val cards: Vector[Int], val ochsen: Int) {
    override def toString(): String = {
      	name + ":\n\tcards: " + cards.mkString(", ") + "\n\tOchsen: " + ochsen.toString() + "\n"
    }

    def playCard(cardNr: Int): Player = {
      	Player(name, cards.filterNot(_ == cardNr), ochsen)
    }
    def addOchsen(anzahlOchsen: Int): Player = {
      	Player(name = this.name, cards = this.cards,ochsen=ochsen + anzahlOchsen)
    }
}
