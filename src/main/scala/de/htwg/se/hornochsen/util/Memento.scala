package de.htwg.se.hornochsen.util

import de.htwg.se.hornochsen.model.InterfaceGameState
import scala.util.Try
import scala.util.Failure
import scala.util.Success

trait Memento {
    val originator: InterfaceGameState
    def restore(): InterfaceGameState 
}

class ConcreteMemento(origin: InterfaceGameState) extends Memento {
    val originator: InterfaceGameState = origin
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
        if mementos.isEmpty then
            Failure(new IllegalStateException("Nooop, no state here"))
        else
            val memento: Try[Memento] = {
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