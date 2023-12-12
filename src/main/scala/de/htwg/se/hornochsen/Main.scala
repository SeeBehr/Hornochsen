package de.htwg.se.hornochsen

import de.htwg.se.hornochsen.aview._
import de.htwg.se.hornochsen.controler._
import de.htwg.se.hornochsen.util._
import scala.io.StdIn.readLine

import scala.concurrent.{Await, Future}

@main
def start() = {
    val controler = Controler(initializeGame(input=TUIplayerNames))
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