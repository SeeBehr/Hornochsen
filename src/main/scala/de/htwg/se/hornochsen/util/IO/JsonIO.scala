package de.htwg.se.hornochsen.util.IO

import de.htwg.se.hornochsen.util.FileIO
import de.htwg.se.hornochsen.model.InterfaceGameState

class JsonIO extends FileIO {

  override def load(state: InterfaceGameState): InterfaceGameState = state.loadFromJson(file = "Save/gamestate.json")

  override def save(state: InterfaceGameState): Unit = state.saveToJson(file = "Save/gamestate.json")

    
}