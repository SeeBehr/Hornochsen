package de.htwg.se.hornochsen.aview

import de.htwg.se.hornochsen.util.*

trait UI extends Observer {
    override def update(e: Event): Unit
    def run: Unit
    def end: Unit
}
trait UIState {
}