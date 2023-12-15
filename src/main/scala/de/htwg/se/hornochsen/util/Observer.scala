package de.htwg.se.hornochsen.util

trait Observer:
    def update(e: Event, name:String): Unit

trait Observable:
    var subscribers: Vector[Observer] = Vector()
    def add(s: Observer) = subscribers = subscribers :+ s
    def remove(s: Observer) = subscribers = subscribers.filterNot(o=>o==s)
    def notifyObservers(e: Event, name: String="0") = subscribers.foreach(o => o.update(e, name))

enum Event:
    case Start
    case First
    case nextPlayer
    case Undo
    case Redo
    case End
