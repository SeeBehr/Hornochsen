package de.htwg.se.hornochsen.model

import de.htwg.se.hornochsen.model.BaseModel.Player
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import play.api.libs.json.*

class PlayerSpec extends AnyWordSpec {
  "Player" should {
        val player1 = Player(Name = "Sebastian", Cards = Vector(1, 2), Ochsen = 0)

        "have a playCard" in {
            player1.playCard(1).toString() should be("Sebastian:\n\tcards: 2\n\tOchsen: 0\n")
        }

        "have a addOchsen" in {
            player1.addOchsen(1).toString() should be("Sebastian:\n\tcards: 1, 2\n\tOchsen: 1\n")
        }

        "have a canPlay" in {
            player1.canPlay(1) should be(true)
            player1.canPlay(3) should be(false)
        }

        "have a getCards" in {
            player1.getCards should be(Vector(1, 2))
        }

        "have a name" in {
            player1.name should be("Sebastian")
        }

        "have a ochsen" in {
            player1.ochsen should be(0)
        }

        "have a saveToJson/loadFromJson" in {
            val js = player1.saveToJson
            val player2 = player1.loadFromJson(js)
            player2.toString() should be(player1.toString())
        }

        "have a saveToXML/loadFromXML" in {
            val xml = player1.saveToXML()
            val player2 = player1.loadFromXML(xml)
            player2.toString() should be(player1.toString())
        }
        
        "have a toString" in {
            player1.toString() should be("Sebastian:\n\tcards: 1, 2\n\tOchsen: 0\n")
        }

        "have a makePlayer" in {
            makePlayer("Sebastian", Vector(1, 2), 0).toString() should be("Sebastian:\n\tcards: 1, 2\n\tOchsen: 0\n")
        }
    }
}