package de.htwg.se.hornochsen
import de.htwg.se.hornochsen._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import controler._
import model._

class logispec extends AnyWordSpec {
    "The logic" should {
        val board1 = Board(rows=Vector[Row](Row(nummer=1, cards=Vector[Int](1,0,0,0,0,0),filled=1)))
        val player1 = Player(name = "Sebastian", cards = Vector(2, 5), ochsen = 0)
        val player2 = Player(name = "Nicht Sebastian", cards = Vector(1, 3), ochsen = 0)
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

        "select which card to put" in {
            which(boardselect.playedCards)._1.toString() should be ("(2,Sebastian:\n\tcards: 5\n\tOchsen: 0\n)")
            which(boardselect.playedCards)._2.toString() should be ("Vector((3,Nicht Sebastian:\n\tcards: 1\n\tOchsen: 0\n))")
        }

        "show where to put the selected card" in {
            where(boardselect,card._1) should be(0)
        }

        "be able to put card" in {
            canAdd(boardselect,-1) should be (false)
            canAdd(boardselect, 0) should be (true)
        }

        "have all Player's" in {
            initAllPlayers(1, 1,()=>"Patrick", initDeck(1)).toString should be ((Vector(Player("Patrick", Vector(0), 0)),Deck(cards=Vector.empty)).toString)
            initAllPlayers(2,2,()=>"Patrick", initDeck(4)).toString should be((Vector(Player("Patrick", Vector(0, 1), 0), Player("Patrick", Vector(2, 3), 0)),Deck(cards=Vector.empty)).toString)
        }
    }
}