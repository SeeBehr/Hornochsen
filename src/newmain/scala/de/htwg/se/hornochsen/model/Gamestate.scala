package de.htwg.se.hornochsen.model

case class GameState(
    var playersWaiting: Vector[Player],
    var playerActive: Player,
    var playersDone: Vector[Player],
    var board: Board,
    var remDeck: Deck
) {
    override def toString(): String = {
        "Gamestate: \n\n" + "Players: \n" + players.toString() + "\n" + board.toString() + remDeck.toString() + "\n"
    }
}