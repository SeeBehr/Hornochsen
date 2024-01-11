package de.htwg.se.hornochsen.util.IO

import de.htwg.se.hornochsen.util.FileIO
import de.htwg.se.hornochsen.model.InterfaceGameState
import scala.io.Source

class JsonIO extends FileIO {

  override def load(state: InterfaceGameState): InterfaceGameState = state.loadFromJson(file = Source.fromFile("Save/gamestate.json").getLines.mkString)

  override def save(state: InterfaceGameState): Unit = state.saveToJson(file = Source.fromFile("Save/gamestate.json").getLines.mkString)

    
}