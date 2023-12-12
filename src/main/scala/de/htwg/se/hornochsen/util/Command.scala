package de.htwg.se.hornochsen.util

import de.htwg.se.hornochsen.controler.BaseControler.Controler
import de.htwg.se.hornochsen.model.BaseModel.GameState
import scala.util.{Try, Success, Failure}

trait Command {
    def PlayCard: Unit
    def Undo: Try[GameState]
    def Redo: Try[GameState]
    def TakeRow: Unit
    def End: Unit
}