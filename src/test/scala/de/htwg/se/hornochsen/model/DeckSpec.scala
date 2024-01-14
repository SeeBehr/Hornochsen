package de.htwg.se.hornochsen.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import de.htwg.se.hornochsen.model.initDeck
import play.api.libs.json._

class Decktest extends AnyWordSpec {
    "Deck" should {
        val deck = initDeck(1)

        "be able to shuffle" in {
            initDeck(5).shuffle().toString() shouldNot be ("Deck: 1, 2, 3, 4, 5\n")
        }

        "remove Cards" in {
            initDeck(5).remcount(2).toString() should be ("Deck: 3, 4, 5\n")
        }

        "have a getCards" in {
            deck.getCards should be (Vector(1))
        }

        "have a saveToXML/loadFromXML" in {
            val xml = deck.saveToXML()
            val deck2 = deck.loadFromXML(xml)
            deck2.toString() should be (deck.toString())
        }

        "have a saveTo/loadFromJson" in {
            val js = deck.saveToJson
            val deck2 = deck.loadFromJson(js)
            deck2.toString() should be (deck.toString())
        }

        "have a makeDummyDeck" in {
            makeDummyDeck().toString() should be ("Deck: \n")
        }

        "have a initDeck" in {
            initDeck(5).toString() should be ("Deck: 1, 2, 3, 4, 5\n")
        }

        "have a toString" in {
            deck.toString() should be ("Deck: 1\n")
        }
    }
}