package de.htwg.se.hornochsen

import de.htwg.se.hornochsen.aview._
import de.htwg.se.hornochsen.controler._
import de.htwg.se.hornochsen.util._
import scala.io.StdIn.readLine

@main
def start() = {
    val controler = Controler(initializeGame())
    var tui = new TUI(controler)
    var gui = new GUI(controler)
    controler.add(tui)
    controler.add(gui)
    
    controler.notifyObservers(Event.GameStart)
}