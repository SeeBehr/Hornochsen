package de.htwg.se.hornochsen.util

import de.htwg.se.hornochsen.controler.Controler

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
        if controller.redoHistory.mementos.nonEmpty then
            val redoGamestate = controller.redoHistory.restore()
            controller.undoHistory.save(ConcreteMemento(controller.gameState))
            controller.gameState = redoGamestate.originator
    }
    override def UndoRound: Unit = {
        if controller.undoHistory.mementos.nonEmpty then
            val undoGamestate = controller.undoHistory.restore()
            controller.redoHistory.save(ConcreteMemento(controller.gameState))
            controller.gameState = undoGamestate.originator
    }
}