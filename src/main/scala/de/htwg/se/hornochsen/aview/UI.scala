package de.htwg.se.hornochsen.aview

import de.htwg.se.hornochsen.controler._
import de.htwg.se.hornochsen.model._
import de.htwg.se.hornochsen.util._

trait UI extends Observer {
    var state: UIState
    override def update(e: Event, name:String="0"): Unit
    def run: Unit
    def end: Unit
}
trait UIState {
    val name: String
    def interpretLine(controler: Controler, input: String): Unit
}