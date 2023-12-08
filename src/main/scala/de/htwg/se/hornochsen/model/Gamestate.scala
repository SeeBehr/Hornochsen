package de.htwg.se.hornochsen.model

case class GameState(
    var playersWaiting: Vector[Player],
    var playerActive: Player,
    var playersDone: Vector[Player],
    var board: Board,
    var remDeck: Deck
) {
    override def toString(): String = {
        "Gamestate: \n\n" + "Players: \n" + players().mkString("\n") + "\n" + board.toString() + remDeck.toString() + "\n"
    }
    def players(): Vector[Player] = {
        playersDone.appended(playerActive).appendedAll(playersWaiting)
    }
}