package de.htwg.se.hornochsen
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import de.htwg.se.hornochsen.model._

class PlayerSpec extends AnyWordSpec {
  "Player" should {
        val player1 = Player(name = "Sebastian", cards = Vector(1, 2), ochsen = 0)
        "have a Player as String of form\n'name:\n\tcards: x, y\n\tOchsen: n\n'" in {
            player1.toString() should be("Sebastian:\n\tcards: 1, 2\n\tOchsen: 0\n")
        }
    }
}