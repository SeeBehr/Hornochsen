package de.htwg.se.hornochsen
import de.htwg.se.hornochsen._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import model.{initAllPlayers, Player}

class PlayerSpec extends AnyWordSpec {
  "Player" should {
        val player1 = Player(name = "Sebastian", cards = Vector(1, 2), ochsen = 0)
        "have a Player as String of form\n'name:\n\tcards: x, y\n\tOchsen: n\n'" in {
            player1.toString() should be("Sebastian:\n\tcards: 1, 2\n\tOchsen: 0\n")
        }
        "have all Player's" in {
            initAllPlayers(1, 1,()=>"Patrick").toString() should be("Patrick:\n\tcards: 0\n\tOchsen: 0\n")
            initAllPlayers(2,2,()=>"Patrick").toString() should be("Patrick:\n\tcards: 0, 1\n\tOchsen: 0\n\nPatrick:\n\tcards: 0, 1\n\tOchsen: 0\n")
        }
    }
}