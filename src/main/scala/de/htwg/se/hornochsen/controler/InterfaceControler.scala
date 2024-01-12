package de.htwg.se.hornochsen.controler

import de.htwg.se.hornochsen.util.Observable
import de.htwg.se.hornochsen.model._
import scala.util.Try
import de.htwg.se.hornochsen.model.BaseModel.GameState
import de.htwg.se.hornochsen.model.BaseModel.Player
import de.htwg.se.hornochsen.controler.BaseControler._

trait InterfaceControler extends Observable {
    def start(players: Vector[String]): Unit
    def restart: Unit
    def setrunning(running: Boolean): Unit
    def isrunning: Boolean
    def gameState: InterfaceGameState
    def doOp(input: String): Try[Boolean]
    def playCard(player: InterfacePlayer, card: Int): Boolean
    def save: Unit
    def load: Unit
}