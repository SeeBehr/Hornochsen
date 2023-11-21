package de.htwg.se.hornochsen.util

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class FasadeSpec extends AnyWordSpec {
    "Fasade" should {
        "return a Gamestate" in {
            Facade.initializeGame(shuffle=false, sizeDeck=2, numRows=1, numRowCards=1, numPlayer=1, numHandCards=1, input = Int => "A")._1.toString() should be ("Vector(A:\n\tcards: 2\n\tOchsen: 0\n)")
            Facade.initializeGame(shuffle=false, sizeDeck=2, numRows=1, numRowCards=1, numPlayer=1, numHandCards=1, input = Int => "A")._2.toString() should be ("Board:\n\tRow 1: 1 filled: 1\n\nPlayed cards: \n")
            Facade.initializeGame(shuffle=false, sizeDeck=2, numRows=1, numRowCards=1, numPlayer=1, numHandCards=1, input = Int => "A")._3.toString() should be ("Deck: \n")
        }
    }
}