package de.htwg.se.hornochsen.model

import de.htwg.se.hornochsen.model.BaseModel.{GameState, Player}
import com.google.inject.Injector
import com.google.inject.Guice
import de.htwg.se.hornochsen.HornochsenModule
import com.google.inject.assistedinject.Assisted

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

trait GameStateFactory {
  def create(
      @Assisted("playerswaiting") playerswaiting: Vector[InterfacePlayer],
      @Assisted("playeractive") playeractive: InterfacePlayer,
      @Assisted("playersdone") playersdone: Vector[InterfacePlayer],
      @Assisted("myBoard") myBoard: InterfaceBoard,
      @Assisted("RemDeck") RemDeck: InterfaceDeck
  ): InterfaceGameState
}

def initGameState(allP: Vector[InterfacePlayer], board: InterfaceBoard, deck: InterfaceDeck): InterfaceGameState = {


    val injector: Injector = Guice.createInjector(new HornochsenModule)
    val gameStateFactory = injector.getInstance(classOf[GameStateFactory])
    
    gameStateFactory.create(
        playerswaiting = allP.tail,
        playeractive = allP.head,
        playersdone = Vector.empty,
        myBoard = board,
        RemDeck = deck
    )
}