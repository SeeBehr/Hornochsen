package de.htwg.se.hornochsen.controler

import de.htwg.se.hornochsen.util.Observable
import de.htwg.se.hornochsen.model._
import scala.util.Try
import de.htwg.se.hornochsen.model.BaseModel.GameState
import de.htwg.se.hornochsen.model.BaseModel.Player

trait InterfaceControler extends Observable {
    def isrunning: Boolean
    def gameState: InterfaceGameState
    def rownum(num: Int): Try[Int]
    def doOp(input: String, stateName: String): Try[Boolean]
    def playCard(player: InterfacePlayer, card: Int, stateName: String): Boolean
    def takeRow(player: InterfacePlayer, row: Int, stateName: String): Try[Boolean]
    def placeCards(): Unit
}