package de.htwg.se.hornochsen.model
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import de.htwg.se.hornochsen.model._
import BaseModel.Player
import play.api.libs.json._

class PlayerSpec extends AnyWordSpec {
  "Player" should {
        val player1 = Player(Name = "Sebastian", Cards = Vector(1, 2), Ochsen = 0)
        
        "have a toString" in {
            player1.toString() should be("Sebastian:\n\tcards: 1, 2\n\tOchsen: 0\n")
        }

        "have a getCards" in {
            player1.getCards should be(Vector(1, 2))
        }

        "have a playCard" in {
            player1.playCard(1).toString() should be("Sebastian:\n\tcards: 2\n\tOchsen: 0\n")
        }

        "have a addOchsen" in {
            player1.addOchsen(1).toString() should be("Sebastian:\n\tcards: 1, 2\n\tOchsen: 1\n")
        }

        "have a drawCards" in {
            player1.drawCards(Vector(3, 4)).toString() should be("Sebastian:\n\tcards: 1, 2, 3, 4\n\tOchsen: 0\n")
        }

        "have a canPlay" in {
            player1.canPlay(1) should be(true)
            player1.canPlay(3) should be(false)
        }
        /*
        "have a toXml" in {
            player1.saveToXML() should be("<person><name>Sebastian</name><cards><card>1</card><card>2</card></cards><ochsen>0</ochsen></person>")
        }
        */
        "have a name" in {
            player1.name should be("Sebastian")
        }

        "have a ochsen" in {
            player1.ochsen should be(0)
        }

        "have a toJson" in {
            player1.saveToJson.toString should be (Json.obj(
                "name" -> "Sebastian",
                "cards" -> Vector(1, 2),
                "ochsen" -> 0
            ).toString)
        }

        "have a load" in {
            player1.loadFromJson(Json.obj(
                "name" -> "Sebastian",
                "cards" -> Vector(1, 2),
                "ochsen" -> 0
            )).toString() should be("Sebastian:\n\tcards: 1, 2\n\tOchsen: 0\n")
        }
    }
}