package de.htwg.se.hornochsen

import de.htwg.se.hornochsen.aview.*
import de.htwg.se.hornochsen.modules.Default.{InterfaceControler, InterfaceGameState}
import de.htwg.se.hornochsen.util.*

import scala.concurrent.{Await, Future}

@main
def start() = {
    val controler = summon[de.htwg.se.hornochsen.controler.InterfaceControler]
    var tui = new TUI(controler)
    var gui = new GUI(controler)
    controler.add(gui)
    controler.add(tui)
    
    implicit val context = scala.concurrent.ExecutionContext.global
    val f = Future {
        println("Starting GUI")
        gui.main(Array())
    }
    controler.notifyObservers(Event.Start)
    tui.run

    Await.ready(f, scala.concurrent.duration.Duration.Inf)
}