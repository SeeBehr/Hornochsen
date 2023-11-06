package de.htwg.se.hornochsen

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import de.htwg.se.hornochsen.controler.initDeck
class Decktest extends AnyWordSpec {
    "Deck" should {
        "have a scalable size" in {
            initDeck(1).toString() should be ("Deck: 0\n")
            initDeck(2).toString() should be ("Deck: 0, 1\n")
        }
        "be able to delete" in {
            initDeck(5).remcount(2).toString() should be ("Deck: 2, 3, 4\n")
        }
        "be able to shuffle" in {
            initDeck(5).shuffle().toString() shouldNot be ("Deck: 0, 1, 2, 3, 4\n")
        }
    }
}