package de.htwg.se.hornochsen.model

import de.htwg.se.hornochsen.model.BaseModel.{GameState, Player}

trait InterfaceGameState {
    def players: Vector[InterfacePlayer]
    def playersDone: Vector[InterfacePlayer]
    def playerActive: InterfacePlayer
    def playersWaiting: Vector[InterfacePlayer]
    def copy(
        playerswaiting: Vector[InterfacePlayer] = playersWaiting,
        playeractive: InterfacePlayer = playerActive,
        playersdone: Vector[InterfacePlayer] = playersDone,
        Board: InterfaceBoard = board,
        remDeck: InterfaceDeck = remDeck
    ): InterfaceGameState
    def board: InterfaceBoard
    def remDeck: InterfaceDeck
}

def initGameState(allP: Vector[InterfacePlayer], board: InterfaceBoard, deck: InterfaceDeck): InterfaceGameState = {
    GameState(
        playerswaiting = allP.tail,
        playeractive = allP.head,
        playersdone = Vector.empty,
        myBoard = board,
        RemDeck = deck
    )

}