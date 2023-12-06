package de.htwg.se.hornochsen.util

import de.htwg.se.hornochsen.model.GameState
import scala.util.Try
import scala.util.Failure
import scala.util.Success

trait Memento {
    val originator: GameState
    def restore(): GameState 
}

class ConcreteMemento(origin: GameState) extends Memento {
    val originator: GameState = origin
    override def restore(): GameState = {
        originator
    }
    override def toString(): String = {
        originator.toString()
    }
}

class History {
    val maxUndo = 5
    var mementos: List[Memento] = List()
    def save(newMemento: Memento): Unit = {
        if (mementos.length >= maxUndo)
        then
            mementos = mementos.take(maxUndo-1)
        mementos = newMemento :: mementos
    }

    def restore(): Try[Memento] = {
        val geht = mementos.nonEmpty
        
        val memento: Try[Memento] = {
            if !geht
            then
                Failure(new IllegalStateException("Nooop, no redo here"))
            else
                val temp = mementos.head
                mementos = mementos.tail
                Success(temp)
        }
        
        memento
    }

    def clear() : Unit = {
        mementos = List()
    }
    override def toString(): String = {
        "History: \n" + mementos.map(m=> m.toString()).mkString(", ") + "\n"
    }
}