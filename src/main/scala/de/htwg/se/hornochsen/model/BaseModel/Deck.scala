package de.htwg.se.hornochsen.model.BaseModel

import de.htwg.se.hornochsen.model.InterfaceDeck
import scala.util.Random

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
}
