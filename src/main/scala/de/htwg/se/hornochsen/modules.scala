package de.htwg.se.hornochsen.modules

import de.htwg.se.hornochsen.controler.BaseControler._
import de.htwg.se.hornochsen.controler.InterfaceControler
import de.htwg.se.hornochsen.model._
import de.htwg.se.hornochsen.util.FileIO
import de.htwg.se.hornochsen.util.IO
import IO.JsonIO
import IO.XMLIO
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
  given FileIO: FileIO = new JsonIO()
}
