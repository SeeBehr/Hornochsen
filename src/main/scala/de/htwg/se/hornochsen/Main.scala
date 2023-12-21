package de.htwg.se.hornochsen

import com.google.inject.{Guice, Injector}
import de.htwg.se.hornochsen.aview.*
import de.htwg.se.hornochsen.controler.BaseControler.initializeGame
import de.htwg.se.hornochsen.controler.{InterfaceControler, makeControler}
import de.htwg.se.hornochsen.util.*

import scala.concurrent.{Await, Future}

@main
def start() = {
    val injector: Injector = Guice.createInjector(new HornochsenModule())
    val controler = injector.getInstance(classOf[InterfaceControler])

    val tui = new TUI(controler)
    val gui = new GUI(controler)
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