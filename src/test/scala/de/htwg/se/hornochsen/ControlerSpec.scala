package de.htwg.se.hornochsen
import de.htwg.se.hornochsen._
import model._
import controler._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class Controlerspec extends AnyWordSpec {
    "The logic" should {
        val board1 = Board(rows=Vector[Row](Row(nummer=1, cards=Vector[Int](1,0,0,0,0,0),filled=1)))
        val player1 = Player(name = "Sebastian", cards = Vector(2, 5), ochsen = 0)
        val player2 = Player(name = "Nicht Sebastian", cards = Vector(1, 3), ochsen = 0)
        val p1 = player1.playCard(2)
        val p2 = player2.playCard(3)
        val boardselect = Board(rows=board1.rows,playedCards=Vector((2,p1),(3,p2)))
        val gameState = GameState(players=Vector(player1, player2), board=boardselect, remDeck=initDeck(1))
        var controler = Controler(gameState)
        val (card, cardsrem) = controler.which(boardselect.playedCards)
        
        "select the lowest drawn card number" should {
            "Played card + player" in {
                card should be (2, p1)
            }

            "Jet to be Played cards + Players" in {
                (cardsrem.toString()) should be(Vector((3, p2)).toString())
            }
        }

        "select which card to put" in {
            controler.which(boardselect.playedCards)._1.toString() should be ("(2,Sebastian:\n\tcards: 5\n\tOchsen: 0\n)")
            controler.which(boardselect.playedCards)._2.toString() should be ("Vector((3,Nicht Sebastian:\n\tcards: 1\n\tOchsen: 0\n))")
        }

        "show where to put the selected card" in {
            controler.where(boardselect,card._1) should be(0)
        }

        "be able to put card" in {
            controler.canAdd(boardselect,-1) should be (false)
            controler.canAdd(boardselect, 0) should be (true)
        }

        "have all Player's" in {
            initAllPlayers(1, 1,(Int)=>"Patrick", initDeck(1)).toString should be ((Vector(Player("Patrick", Vector(1), 0)),Deck(cards=Vector.empty)).toString)
            initAllPlayers(2, 2,(Int)=>"Patrick", initDeck(4)).toString should be((Vector(Player("Patrick", Vector(1, 2), 0), Player("Patrick", Vector(3, 4), 0)),Deck(cards=Vector.empty)).toString)
        }

        "give the players cards from the deck" in {
            val deckSP = initDeck(50)
            val (boardSP, playerdeckSP) = initBoard(numRows = 1, numRowCards = 1, deck = deckSP)
            val (playerSP, refilldeckSP) = initAllPlayers(numPlayer = 1, numHandCards = 1, input = (a)=>"Seebastian", deck = playerdeckSP)
            val gameStateSP = GameState(playerSP, boardSP, refilldeckSP)
            val controlerSP = Controler(gameStateSP)
            
            val gameStateWithExtraCards = controlerSP.giveCards(1)
            val newPlayer = gameStateWithExtraCards._1(0)
            newPlayer.toString() should be("Seebastian:\n\tcards: 2, 3\n	Ochsen: 0\n")
        }
    }
}