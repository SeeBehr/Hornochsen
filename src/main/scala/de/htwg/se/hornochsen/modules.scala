package de.htwg.se.hornochsen.modules

import de.htwg.se.hornochsen.controler.BaseControler.Controler
import de.htwg.se.hornochsen.controler.InterfaceControler
import de.htwg.se.hornochsen.model.InterfaceGameState
import de.htwg.se.hornochsen.controler.BaseControler.initializeGame
import de.htwg.se.hornochsen.model.initDeck
import de.htwg.se.hornochsen.model.InterfaceDeck
import de.htwg.se.hornochsen.model.InterfacePlayer
import de.htwg.se.hornochsen.model.makePlayer
import de.htwg.se.hornochsen.aview.TUIplayerNames

object Default {
  val defaultDeck: InterfaceDeck = initDeck(104).shuffle()
  
  given InterfaceDeck: InterfaceDeck = defaultDeck
  given InterfaceGameState: InterfaceGameState = 
    initializeGame(
        deck = defaultDeck,
        numRows = 4,
        numRowCards = 6,
        numPlayer = 4,
        numHandCards = 10,
        input = Int => " "
      )
  given InterfaceControler(using state: InterfaceGameState): InterfaceControler = new Controler(state)
  given InterfacePlayer: InterfacePlayer = makePlayer()
}
