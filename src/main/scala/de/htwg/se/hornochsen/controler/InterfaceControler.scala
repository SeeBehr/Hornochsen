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
    def doOp(input: String, stateName: String): Try[Boolean]
    def playCard(player: InterfacePlayer, card: Int, stateName: String): Boolean
    def save: Unit
    def load: Unit
}