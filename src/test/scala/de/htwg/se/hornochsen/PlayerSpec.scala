package de.htwg.se.hornochsen
import de.htwg.se.hornochsen._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class PlayerSpec extends AnyWordSpec {
  "Player" should {
        val player1 = Player(name = "Sebastian", cards = Vector(1, 2), ochsen = 0)
        val player2 = Player(name = "Patrick", cards = Vector(11, 25, 17), ochsen = 1)
        val allplayers = AllPlayers(player = Vector(player1, player2))
        "have a Player as String of form\n'name:\n\tcards: x, y\n\tOchsen: n\n'" in {
            player1.toString() should be("Sebastian:\n\tcards: 1, 2\n\tOchsen: 0\n")
            player2.toString() should be("Patrick:\n\tcards: 11, 25, 17\n\tOchsen: 1\n")
        }
        "have all Player's" in {
            allplayers.toString() should be("Sebastian:\n\tcards: 1, 2\n\tOchsen: 0\n\nPatrick:\n\tcards: 11, 25, 17\n\tOchsen: 1\n")
        }
    }
}
