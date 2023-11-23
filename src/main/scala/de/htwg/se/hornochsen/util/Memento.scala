package de.htwg.se.hornochsen.util

import de.htwg.se.hornochsen.model.GameState

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
    var mementos: List[Memento] = List()

    def save(memento: Memento): Unit = {
        mementos = memento :: mementos
    }

    def restore(): Memento = {
        val memento = mementos.head
        mementos = mementos.tail
        memento
    }
    override def toString(): String = {
        "History: \n" + mementos.map(m=> m.toString()).mkString(", ") + "\n"
    }
}