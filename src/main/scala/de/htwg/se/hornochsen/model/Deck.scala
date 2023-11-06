package de.htwg.se.hornochsen.model

import scala.util.Random

case class Deck(cards: Vector[Int]) {
	def shuffle(): Deck = {
		Deck(Random.shuffle(cards))
	}
	def remcount(count: Int): Deck = {
		Deck(this.cards.drop(count))
	}
	override def toString(): String = {
		"Deck: " + cards.mkString(", ") + "\n"
	}
}
