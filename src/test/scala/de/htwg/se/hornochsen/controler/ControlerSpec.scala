package de.htwg.se.hornochsen.controler
import de.htwg.se.hornochsen._
import model._
import controler._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import de.htwg.se.hornochsen.util.ConcreteMemento

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
            // Test with one player
            val (players, remainingDeck) = PlayerFactory.getInstance(
                playerCount = 1, 
                numHandCards = 1, 
                input = (a)=>"Seebastian", 
                deck = initDeck(2)
            )
            players.toString() should be (
                Vector(Player(name = "Seebastian", cards = Vector(1), ochsen = 0)).toString
            )
            remainingDeck.toString() should be("Deck: 2\n")

            // Test with two players
            val (players_2, remainingDeck_2) = PlayerFactory.getInstance(
                playerCount = 2, 
                numHandCards = 2, 
                input = (a)=>"Seebastiaan", 
                deck = initDeck(5)
            )
            players_2.toString() should be (
                Vector(
                    Player(name = "Seebastiaan", cards = Vector(1, 2), ochsen = 0), 
                    Player(name = "Seebastiaan", cards = Vector(3, 4), ochsen = 0), 
                ).toString
            )
            remainingDeck_2.toString() should be("Deck: 5\n")
        }

        "give the players cards from the deck" in {
            val deckSP = initDeck(50)
            val (boardSP, playerdeckSP) = initBoard(numRows = 1, numRowCards = 1, deck = deckSP)
            val (playerSP, refilldeckSP) = PlayerFactory.getInstance(playerCount = 1, numHandCards = 1, input = (a)=>"Seebastian", deck = playerdeckSP)
            val gameStateSP = GameState(playerSP, boardSP, refilldeckSP)
            val controlerSP = Controler(gameStateSP)
            
            val gameStateWithExtraCards = controlerSP.giveCards(1)
            val newPlayer = gameStateWithExtraCards._1(0)
            newPlayer.toString() should be("Seebastian:\n\tcards: 2, 3\n	Ochsen: 0\n")
        }

        "return a Gamestate" in {
            initializeGame(shuffle=false, sizeDeck=2, numRows=1, numRowCards=1, numPlayer=1, numHandCards=1, input = Int => "A")._1.toString() should be ("Vector(A:\n\tcards: 2\n\tOchsen: 0\n)")
            initializeGame(shuffle=false, sizeDeck=2, numRows=1, numRowCards=1, numPlayer=1, numHandCards=1, input = Int => "A")._2.toString() should be ("Board:\n\tRow 1: 1 filled: 1\n\nPlayed cards: \n")
            initializeGame(shuffle=false, sizeDeck=2, numRows=1, numRowCards=1, numPlayer=1, numHandCards=1, input = Int => "A")._3.toString() should be ("Deck: \n")
        }

        "have a history" in {
            controler.history.toString() should be("History: \n\n")
            controler.history.save(ConcreteMemento(controler.gameState))
            controler.history.toString() should be("History: \nGamestate: \n\nPlayers: \nVector(Sebastian:\n\tcards: 2, 5\n\tOchsen: 0\n, Nicht Sebastian:\n\tcards: 1, 3\n\tOchsen: 0\n)\nBoard:\n\tRow 1: 1, 0, 0, 0, 0, 0 filled: 1\n\nPlayed cards: \n2, von Sebastian\n3, von Nicht Sebastian\nDeck: 1\n\n\n")
        }

        "history schould work tike this" in {
            val vergleich = controler.gameState
            controler.history.save(ConcreteMemento(controler.gameState))
            controler.gameState = controler.updateGamestate(()=>"1", (String, input) => 1)
            controler.beginNextRound((String) => (), () => "Next") should be(true)
            controler.beginNextRound((String) => (), () => "Undo") should be(false)
            vergleich == controler.gameState should be (true)
        }
    }
}