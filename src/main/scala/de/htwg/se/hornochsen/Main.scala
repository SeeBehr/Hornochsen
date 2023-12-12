package de.htwg.se.hornochsen

import de.htwg.se.hornochsen.aview._
import de.htwg.se.hornochsen.controler._
import de.htwg.se.hornochsen.util._
import scala.io.StdIn.readLine

import scala.concurrent.Await
import scala.concurrent.Future

@main
def start() = {
    val controler = Controler(initializeGame(input=TUIplayerNames))
    var tui = new TUI(controler)
    var gui = new GUI(controler)
    controler.add(tui)
    controler.add(gui)
    
    //controler.notifyObservers(Event.Start)
    implicit val context = scala.concurrent.ExecutionContext.global
    val f = Future {
        gui.update(Event.Start)
    }
    
    tui.update(Event.Start)
    Await.ready(f, scala.concurrent.duration.Duration.Inf)
}