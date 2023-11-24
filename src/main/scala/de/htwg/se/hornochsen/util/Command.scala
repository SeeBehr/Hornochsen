package de.htwg.se.hornochsen.util

import de.htwg.se.hornochsen.controler.Controler
import de.htwg.se.hornochsen.model.GameState

trait Command {
    def PlayRound: Unit
    def UndoRound: Unit
    def RedoRound: Unit
}

class SetCommand(controller: Controler) extends Command{
    override def PlayRound: Unit = {
        controller.undoHistory.save(ConcreteMemento(controller.gameState))
    }
    override def RedoRound: Unit = {
        val geht = controller.redoHistory.mementos.isEmpty
        val memento: Option[Memento] = if (geht)
            then
                val redoGamestate = controller.redoHistory.restore()
                redoGamestate
            else None
        memento match {
            case Some(value) =>
                controller.gameState = value.originator
        }
    }
    override def UndoRound: Unit = {
        val geht = controller.undoHistory.mementos.isEmpty
        val memento: Option[Memento] = if (geht)
            then
                val undoGamestate = controller.undoHistory.restore()
                undoGamestate
            else None
        memento match {
            case Some(value) => 
                controller.gameState = value.originator
        }
    }
}