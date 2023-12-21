package de.htwg.se.hornochsen.model.BaseModel

import de.htwg.se.hornochsen.model
import model.{InterfaceBoard, InterfacePlayer, InterfaceDeck}
import model.makePlayer
import de.htwg.se.hornochsen.model.InterfaceGameState
import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted


case class GameState @Inject() (
    @Assisted("playerswaiting") var playerswaiting: Vector[InterfacePlayer],
    @Assisted("playeractive") var playeractive: InterfacePlayer,
    @Assisted("playersdone") var playersdone: Vector[InterfacePlayer],
    @Assisted("myBoard") var myBoard: InterfaceBoard,
    @Assisted("RemDeck") var RemDeck: InterfaceDeck
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
