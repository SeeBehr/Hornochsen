package de.htwg.se.hornochsen

import de.htwg.se.hornochsen.aview._
import de.htwg.se.hornochsen.controler._
import de.htwg.se.hornochsen.model._
import de.htwg.se.hornochsen.util._
import scala.io.StdIn.readLine

@main
def start() = {
    val deck = initDeck(120).shuffle()
    val (board, playerdeck) = initBoard(numRows = 4, numRowCards = 6, deck = deck)
    val (allP, refilldeck) = initAllPlayers(numPlayer = 4, numHandCards = 6, input = (a)=>{println(s"Spielername $a"); readLine}, deck = playerdeck)
    val gameState: GameState = GameState(allP, board, refilldeck)
    val controler = Controler(gameState)
    var tui = new TUI(controler)
    controler.add(tui)
    
    tui.update(Event.RoundFinished)
    for (i <- Seq(0,3);
         j <- Seq(0,6)) {
         
        tui.run
        controler.gameState = controler.giveCards(6)
    }
}