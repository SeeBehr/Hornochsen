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
        printf("Redo\n")
        val geht = !controller.redoHistory.mementos.isEmpty
        val memento: Option[Memento] = if (geht)
            then
                val redoGamestate = controller.redoHistory.restore()
                controller.undoHistory.save(ConcreteMemento(controller.gameState))
                redoGamestate
            else None
        memento match {
            case Some(value) =>
                controller.gameState = value.originator
            case None => 
                println("Redo geht nicht")
        }
    }
    override def UndoRound: Unit = {
        printf("Undo\n")
        val geht = !controller.undoHistory.mementos.isEmpty
        val memento: Option[Memento] = if (geht == true) {
            val undoGamestate = controller.undoHistory.restore()
            controller.redoHistory.save(ConcreteMemento(controller.gameState))	
            undoGamestate
        } else {
            None
        }
        memento match {
            case Some(value) => 
                controller.gameState = value.originator
            case None => 
                println("Undo geht nicht")
        }
    }
}