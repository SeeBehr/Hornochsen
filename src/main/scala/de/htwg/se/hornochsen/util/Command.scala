package de.htwg.se.hornochsen.util

import de.htwg.se.hornochsen.controler.Controler
import de.htwg.se.hornochsen.model.GameState
import scala.util.{Try, Success, Failure}

trait Command {
    def PlayRound: Unit
    def UndoRound: Try[(GameState, ConcreteMemento)]
    def RedoRound: Try[GameState]
}

class SetCommand(controller: Controler) extends Command{
    override def PlayRound: Unit = {
        controller.undoHistory.save(ConcreteMemento(controller.gameState))
    }
    
    override def RedoRound: Try[GameState] = {
        printf("Redo\n")
        val returnValue: Try[GameState] = 
            val redoGamestate = controller.redoHistory.restore()

            redoGamestate match
                case Success(redoGamestate0) =>
                    Try(
                        redoGamestate0.originator
                    )
                    Success(redoGamestate0.originator)
                case Failure(f0) =>
                    Failure(f0)
        returnValue
    }

    override def UndoRound: Try[(GameState, ConcreteMemento)]= {
        printf("Undo\n")
        val returnValue: Try[(GameState, ConcreteMemento)] = 
            val undoGamestate: Try[Memento] = controller.undoHistory.restore()

            undoGamestate match
                case Success(undoGamestate0) =>
                    Try(
                        (undoGamestate0.originator, ConcreteMemento(controller.gameState))
                    )
                case Failure(f0) =>
                    throw f0
        returnValue
    }
}