package de.htwg.se.hornochsen.controler

import de.htwg.se.hornochsen.util._
import de.htwg.se.hornochsen.model._
import de.htwg.se.hornochsen.controler._

enum ActionEvent {
    case default
    case takeRow
    case addCard
    case selectRow
}

object ActionState {
    var state = default
    def handle(e: ActionEvent) = {
        e match {
            case ActionEvent.default  => state = default
            case ActionEvent.addCard => state = addCard
            case ActionEvent.takeRow => state = takeRow
            case ActionEvent.selectRow => state = selectRow
        }
        state
    }
    
    def default(tempboard: Board, card: Int, index: Int, player: Player, WhichRowTake: (String, () => String) => Int, read: () => String) = {
        println("default")
        (tempboard: Board, player: Player)
    }
    
    def addCard(tempboard: Board, card: Int, index: Int, player: Player, WhichRowTake: (String, () => String) => Int, read: () => String) = {
        val board: Board = tempboard.addCard(card, index+1)
        val updatedPlayer: Player = player.playCard(card)
        (board: Board, updatedPlayer: Player)
    }
    
    def takeRow(tempboard: Board, card: Int, index: Int, player: Player, WhichRowTake: (String, () => String) => Int, read: () => String) = {
        val (board: Board, ochsen: Int) = tempboard.takeRow(card, index+1)
        val updatedPlayer: Player = player.playCard(card).addOchsen(ochsen)
        (board: Board, updatedPlayer: Player)
    }
    
    def selectRow(tempboard: Board, card: Int, index: Int, player: Player, WhichRowTake: (String, () => String) => Int, read: () => String) = {
        val nim: Int = WhichRowTake(player.name, read)
        val (board: Board, ochsen: Int) = tempboard.takeRow(card, nim)
        val updatedPlayer: Player = player.playCard(card).addOchsen(ochsen)
        (board: Board, updatedPlayer: Player)
    }
}