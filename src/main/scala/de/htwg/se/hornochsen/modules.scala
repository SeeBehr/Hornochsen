package de.htwg.se.hornochsen.modules

import de.htwg.se.hornochsen.controler.BaseControler.Controler
import de.htwg.se.hornochsen.controler.InterfaceControler
import de.htwg.se.hornochsen.model.InterfaceGameState
import de.htwg.se.hornochsen.controler.BaseControler.initializeGame
import de.htwg.se.hornochsen.model.initDeck
import de.htwg.se.hornochsen.model.InterfaceDeck
import de.htwg.se.hornochsen.model.InterfacePlayer
import de.htwg.se.hornochsen.model.makePlayer

object Default {
  val defaultDeck: InterfaceDeck = initDeck(120).shuffle()
  
  given InterfaceDeck: InterfaceDeck = defaultDeck
  given InterfaceGameState: InterfaceGameState = 
    initializeGame(
        deck = defaultDeck,
        numRows = 4,
        numRowCards = 6,
        numPlayer = 4,
        numHandCards = 12,
        input = (i: Int) => i.toString
      )
  given InterfaceControler(using state: InterfaceGameState): InterfaceControler = new Controler(state)
  given InterfacePlayer: InterfacePlayer = makePlayer()
}
