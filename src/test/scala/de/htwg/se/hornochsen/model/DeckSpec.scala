package de.htwg.se.hornochsen.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import de.htwg.se.hornochsen.model.initDeck
import play.api.libs.json._

class Decktest extends AnyWordSpec {
    "Deck" should {
        val deck = initDeck(1)

        "have a scalable size" in {
            initDeck(1).toString() should be ("Deck: 1\n")
            initDeck(2).toString() should be ("Deck: 1, 2\n")
        }

        "have a makeDummyDeck" in {
            makeDummyDeck().toString() should be ("Deck: \n")
        }

        "be able to shuffle" in {
            initDeck(5).shuffle().toString() shouldNot be ("Deck: 1, 2, 3, 4, 5\n")
        }

        "be able to delete" in {
            deck.remcount(1).toString() should be ("Deck: \n")
        }

        "have a toString" in {
            deck.toString() should be ("Deck: 1\n")
        }

        "have a getCards" in {
            deck.getCards should be (Vector(1))
        }
        /*
        "have a toXml" in {
            deck.saveToXML() should be ("<deck><cards><card>1</card></cards></deck>")
        }
        */
        "have a toJson" in {
            deck.saveToJson.toString should be (Json.obj(
                "cards" -> Vector(1)
            ).toString)
        }

        "have a load" in {
            deck.loadFromJson(Json.obj(
                "cards" -> Vector(1)
            )).toString() should be ("Deck: 1\n")
        }
    }
}