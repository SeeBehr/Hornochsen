package de.htwg.se.hornochsen.util

trait Observer:
    def update(e: Event): Unit

trait Observable:
    var subscribers: Vector[Observer] = Vector()
    def add(s: Observer) = subscribers = subscribers :+ s
    def remove(s: Observer) = subscribers = subscribers.filterNot(o=>o==s)
    def notifyObservers(e: Event, name: String="0") = subscribers.foreach(o => o.update(e))

enum Event:
    case Start
    case nextPlayer
    case End
