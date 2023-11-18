package de.htwg.se.hornochsen.controler

enum baseEvent {
    case Init
    case Run
    case End
}

trait baseState {
    var state = baseEvent.Init
    def next: baseState
}

case class InitState() extends baseState {
  override def next: baseState = {
    // Initialize the deck, board, and players
    // Return the next state
    baseState(baseEvent.Run)
  }
}

case class RoundState() extends baseState {
  override def next: baseState = {
    // Update the game state for a round
    // Return the next state
    EndState()
  }
}

case class EndState() extends baseState {
  override def next: baseState = {
    // Check if the game should end or continue
    // Return the next state
  }
}