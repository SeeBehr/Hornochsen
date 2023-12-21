package de.htwg.se.hornochsen

import com.google.inject.AbstractModule
import de.htwg.se.hornochsen.controler.BaseControler.{Controler, initializeGame}
import de.htwg.se.hornochsen.controler.InterfaceControler
import de.htwg.se.hornochsen.model.{InterfaceDeck, initDeck}


class HornochsenTestModule() extends AbstractModule {

  override def configure(): Unit = {
    bind(classOf[InterfaceControler]).toInstance(new Controler(initializeGame(input = (i: Int) => i.toString)))
    bind(classOf[InterfaceDeck]).toInstance(initDeck(120))
  }

}