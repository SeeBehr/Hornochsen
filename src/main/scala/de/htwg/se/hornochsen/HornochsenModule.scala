package de.htwg.se.hornochsen

import com.google.inject.AbstractModule
import de.htwg.se.hornochsen.controler.BaseControler.{Controler, initializeGame}
import de.htwg.se.hornochsen.controler.InterfaceControler
import de.htwg.se.hornochsen.model.{InterfaceDeck, InterfacePlayer, initDeck, makePlayer}
import de.htwg.se.hornochsen.model.InterfaceGameState

class HornochsenModule() extends AbstractModule {

  override def configure(): Unit = {
    bind(classOf[InterfaceDeck]).toInstance(initDeck(120).shuffle())
    bind(classOf[InterfacePlayer]).toInstance(makePlayer())
    bind(classOf[InterfaceControler]).toInstance(new Controler(initializeGame(deck=initDeck(120).shuffle(), input = (i: Int) => i.toString)))
    bind(classOf[InterfaceGameState]).toInstance(
      initializeGame(
        deck = initDeck(120).shuffle(),
        numRows = 4,
        numRowCards = 6,
        numPlayer = 4,
        numHandCards = 12,
        input = (i: Int) => i.toString
      ))
  }

}