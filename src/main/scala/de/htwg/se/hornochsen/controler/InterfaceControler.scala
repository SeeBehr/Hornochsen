package de.htwg.se.hornochsen.controler

import de.htwg.se.hornochsen.util.Observable
import de.htwg.se.hornochsen.model._
import scala.util.Try
import de.htwg.se.hornochsen.model.BaseModel.GameState
import de.htwg.se.hornochsen.model.BaseModel.Player
import de.htwg.se.hornochsen.controler.BaseControler._

trait InterfaceControler extends Observable {
    def isrunning: Boolean
    def gameState: InterfaceGameState
    def doOp(input: String, stateName: String): Try[Boolean]
    def playCard(player: InterfacePlayer, card: Int, stateName: String): Boolean
    //def takeRow(player: InterfacePlayer, row: Int, statename: String = "0"): InterfacePlayer
}