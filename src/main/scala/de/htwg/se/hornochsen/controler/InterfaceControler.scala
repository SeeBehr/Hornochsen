package de.htwg.se.hornochsen.controler

import de.htwg.se.hornochsen.model.*
import de.htwg.se.hornochsen.util.Observable

import scala.util.Try

trait InterfaceControler extends Observable {
    def start(players: Vector[String]): Unit
    def restart: Unit
    def setrunning(running: Boolean): Unit
    def isrunning: Boolean
    def gameState: InterfaceGameState
    
    // Interprets the input, if it is not a number (Card you want to play)
    def doOp(input: String): Try[Boolean]
    
    /* 
     This Method is called from the TUI or GUI when the active player wants to play a card.
     The Mehtod checks if it is a valid card. This is only needed For the TUI because the GUI only shows valid cards.
      If the card is valid the playedCards Vector gets an new Touple(card, player).The active player is moved to the playersDone.
      If there is a player waiting he becomes the new active player. Else the cards are placed on the board and an new Round starts.
     */
    def playCard(player: InterfacePlayer, card: Int): Boolean
    def save: Unit
    def load: Unit
}