package de.htwg.se.hornochsen.util.IO

import de.htwg.se.hornochsen.util.FileIO
import de.htwg.se.hornochsen.model.InterfaceGameState

class XMLIO extends FileIO {

  override def load(state: InterfaceGameState): InterfaceGameState = state.loadFromXML("Save/gamestate.xml")

  override def save(state: InterfaceGameState): Unit = state.saveToXML("Save/gamestate.xml")

}   