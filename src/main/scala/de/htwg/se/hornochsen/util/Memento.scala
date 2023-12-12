package de.htwg.se.hornochsen.util

import de.htwg.se.hornochsen.model.InterfaceGameState
import scala.util.Try
import scala.util.Failure
import scala.util.Success

trait Memento {
    val originator: InterfaceGameState
    val stateName: String
    def restore(): InterfaceGameState 
}

class ConcreteMemento(origin: InterfaceGameState, state: String) extends Memento {
    val originator: InterfaceGameState = origin
    val stateName: String = state
    override def restore(): InterfaceGameState = {
        originator
    }
    override def toString(): String = {
        originator.toString()
    }
}

class History {
    val maxUndo = 10
    var mementos: List[Memento] = List()
    def save(newMemento: Memento): Unit = {
        if (mementos.length >= maxUndo)
        then
            mementos = mementos.take(maxUndo-1)
        mementos = newMemento :: mementos
    }

    def restore(): Try[Memento] = {
        val empty = mementos.isEmpty
        
        val memento: Try[Memento] = {
            if empty
            then
                Failure(new IllegalStateException("Nooop, no state here"))
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