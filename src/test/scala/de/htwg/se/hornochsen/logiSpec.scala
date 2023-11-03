package de.htwg.se.hornochsen
import de.htwg.se.hornochsen._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class logispec extends AnyWordSpec {
    "The logic" should {
        val board1 = Board(rows=Vector[Row](Row(nummer=1, cards=Vector[Int](3,0,0,0,0,0),filled=1)))
        val player1 = Player(name = "Sebastian", cards = Vector(1, 2), ochsen = 0)
        val player2 = Player(name = "Nicht Sebastian", cards = Vector(3, 4), ochsen = 0)
        val allP = AllPlayers(player=Vector(player1, player2))
        val p1 = player1.playCard(2)
        val p2 = player2.playCard(3)
        val boardselect = Board(rows=board1.rows,playedCards=Vector((2,p1),(3,p2)))
        val (card, cardsrem) = which(boardselect.playedCards)
        
        "select the lowest drawn card number" should {
            "Played card + player" in {
                card should be (2, p1)
            }

            "Jet to be Played cards + Players" in {
                (cardsrem.toString()) should be(Vector((3, p2)).toString())
            }
        }

        "show where to put the selected card" in {
            where(boardselect,card._1) should be(-1)
        }
        "able to put card" in {
            canAdd(boardselect,-1) should be (false)
            canAdd(boardselect, 0) should be (true)
        }
    }
}