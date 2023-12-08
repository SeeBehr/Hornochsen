package de.htwg.se.hornochsen.aview

import de.htwg.se.hornochsen.controler._
import de.htwg.se.hornochsen.model._
import de.htwg.se.hornochsen.util._

trait UI extends Observer {
    override def update(e: Event): Unit
    def run: Unit
    def end: Unit
}