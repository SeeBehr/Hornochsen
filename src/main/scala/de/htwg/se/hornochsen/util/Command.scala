package de.htwg.se.hornochsen.util

import de.htwg.se.hornochsen.model.BaseModel.GameState

import scala.util.Try

trait Command {
    def PlayCard: Unit
    def Undo: Try[GameState]
    def Redo: Try[GameState]
    def End: Unit
}