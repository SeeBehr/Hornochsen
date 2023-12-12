package de.htwg.se.hornochsen.model.BaseModel

import de.htwg.se.hornochsen.model
import model.{InterfaceBoard, InterfacePlayer, InterfaceDeck}
import model.makePlayer
import de.htwg.se.hornochsen.model.InterfaceGameState


case class GameState (
    var playerswaiting: Vector[InterfacePlayer],
    var playeractive: InterfacePlayer,
    var playersdone: Vector[InterfacePlayer],
    var myBoard: InterfaceBoard,
    var RemDeck: InterfaceDeck
) extends InterfaceGameState {
    override def toString(): String = {
        "Gamestate: \n\n" + "Players: \n" + players.mkString("\n") + "\n" + board.toString() + remDeck.toString() + "\n"
    }

    override def playersDone: Vector[InterfacePlayer] = playersdone
    
    override def playerActive: InterfacePlayer = playeractive

    override def playersWaiting: Vector[InterfacePlayer] = playerswaiting
    
    override def players: Vector[InterfacePlayer] = {
        if playerActive != makePlayer() then
            playersDone.appended(playerActive).appendedAll(playersWaiting)
        else 
            playersDone.appendedAll(playersWaiting)
    }

    override def copy(
        playerswaiting: Vector[InterfacePlayer] = playersWaiting,
        playeractive: InterfacePlayer = playerActive,
        playersdone: Vector[InterfacePlayer] = playersDone,
        Board: InterfaceBoard = board,
        remDeck: InterfaceDeck = remDeck
    ): InterfaceGameState = {
        GameState(playerswaiting, playeractive, playersdone, Board, remDeck)
    }

    override def board: InterfaceBoard = myBoard

    override def remDeck: InterfaceDeck = RemDeck
}
