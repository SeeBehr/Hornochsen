package de.htwg.se.hornochsen

import de.htwg.se.hornochsen.aview._
import de.htwg.se.hornochsen.controler._
import de.htwg.se.hornochsen.util._
import scala.io.StdIn.readLine

@main
def start() = {
    val controler = Controler(initializeGame())
    var tui = new TUI(controler)
    controler.add(tui)
    
    controler.notifyObservers(Event.RoundFinished)
    for (i <- Seq(0,3);
         j <- Seq(0,6)) {
        
        controler.notifyObservers(Event.PlayRound)
        controler.gameState = controler.giveCards(6)
    }
    controler.notifyObservers(Event.End)
}