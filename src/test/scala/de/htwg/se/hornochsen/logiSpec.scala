package de.htwg.se.hornochsen
import de.htwg.se.hornochsen._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class logispec extends AnyWordSpec {
  "The logic" should {
        val player1 = Player(name = "Sebastian", cards = Vector(1, 2), ochsen = 0)
        val player2 = Player(name = "Nicht Sebastian", cards = Vector(3, 4), ochsen = 0)
        "select the lowest drawn card number" in {
            val cards = Vector((1, player1), (3, player2))
            val (card, cards2) = which(cards)
            card should be (1, player1)
            (cards2.toString()) should be (Vector((3, player2)).toString())
        }
        "have all Player's" in {
            initAllPlayers(1, 1,()=>"Patrick").toString() should be("Patrick:\n\tcards: 0\n\tOchsen: 0\n")
            initAllPlayers(2,2,()=>"Patrick").toString() should be("Patrick:\n\tcards: 0, 1\n\tOchsen: 0\n\nPatrick:\n\tcards: 0, 1\n\tOchsen: 0\n")
        }
    }
}