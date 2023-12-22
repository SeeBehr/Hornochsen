package de.htwg.se.hornochsen

import com.google.inject.AbstractModule
import de.htwg.se.hornochsen.controler.BaseControler.{Controler, initializeGame}
import de.htwg.se.hornochsen.controler.InterfaceControler
import de.htwg.se.hornochsen.model.{InterfaceDeck, initDeck}
import de.htwg.se.hornochsen.model.InterfaceGameState


class HornochsenTestModule(decksize: Int = 120) extends AbstractModule {

  override def configure(): Unit = {
    bind(classOf[InterfaceControler]).toInstance(new Controler(initializeGame(initDeck(120), input = (i: Int) => i.toString)))
    bind(classOf[InterfaceDeck]).toInstance(initDeck(120))    
    bind(classOf[InterfaceGameState]).toInstance(
      initializeGame(
        deck = initDeck(120),
        numRows = 4,
        numRowCards = 6,
        numPlayer = 4,
        numHandCards = 12,
        input = (i: Int) => i.toString
      ))
  }

}