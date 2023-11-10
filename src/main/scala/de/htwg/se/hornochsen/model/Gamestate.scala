package de.htwg.se.hornochsen.model

case class GameState(
    var players: Vector[Player],
    var board: Board,
    var remDeck: Deck
) {}