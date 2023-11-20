package de.htwg.se.hornochsen.util

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class FasadeSpec extends AnyWordSpec {
    "Fasade" should {
        "return a Gamestate" in {
            Fasade.initialiseGame(sizeDeck=2,numRows=1, numRowCards=1, numPlayer=1, numHandCards=1,input = Int => "A")._1.toString() should be ("GameState(Vector(A:\n\tcards: 2\n\tOchsen: 0\n),Board:\n\tRow 1: 2 filled: 1\n\nPlayed cards: \n,Deck: 1\n)")
        }
    }
}