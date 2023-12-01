package de.htwg.se.hornochsen.util

import de.htwg.se.hornochsen.controler.Controler
import de.htwg.se.hornochsen.model.GameState
import scala.util.Try

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
    override def UndoRound: Try[GameState] = {
        printf("Undo\n")
        val geht = !controller.undoHistory.mementos.isEmpty
        val undoneGamestate: Try[GameState] = Try {
            val undoGamestate: Try[Memento] = controller.undoHistory.restore()
            if undoGamestate.isFailure
            then
                throw new IllegalStateException("Hae?!")
            else
                controller.redoHistory.save(ConcreteMemento(controller.gameState))	
                undoGamestate.get.originator
        }
        /*
        memento match {
            case Some(value) => 
                controller.gameState = value.originator
            case None => 
                println("Undo geht nicht")
        }

        memento
        */
    }
}