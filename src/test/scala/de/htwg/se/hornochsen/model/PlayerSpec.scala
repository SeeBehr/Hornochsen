package de.htwg.se.hornochsen.model
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import de.htwg.se.hornochsen.model._
import BaseModel.Player

class PlayerSpec extends AnyWordSpec {
  "Player" should {
        val player1 = Player(Name = "Sebastian", Cards = Vector(1, 2), Ochsen = 0)
        "have a Player as String of form\n'name:\n\tcards: x, y\n\tOchsen: n\n'" in {
            player1.toString() should be("Sebastian:\n\tcards: 1, 2\n\tOchsen: 0\n")
        }
    }
}