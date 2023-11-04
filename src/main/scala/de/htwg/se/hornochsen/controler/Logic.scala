package de.htwg.se.hornochsen.controler

import de.htwg.se.hornochsen.model._

case class GameState(
    players: Vector[Player],
    board: Board,
    remDeck: Deck,
    playersWithPlayedCards: Vector[(Int, Player)]
) {}

def which(
    cards: Vector[(Int, Player)]
): ((Int, Player), Vector[(Int, Player)]) = {
  var min = cards.min((x, y) => x._1 - y._1)
  return (min, cards.filter(x => x._1 != min._1))
}

def where(b: Board, card: Int): Int = {
  val lastElements: Vector[Int] = b.rows.map(row => row.cards(row.filled))
  val minRow =
    lastElements.map(x => card - x).filter(x => x > 0).min((x, y) => y - x)
  return lastElements.indexOf(minRow)
}

def canAdd(b: Board, index: Int): Boolean = {
  if index < 0 | index > b.rows(0).cards.length then return false
  return b.rows(index).filled < b.rows(index).cards.length
}

def initDeck(number: Int): Deck = Deck(Vector.tabulate(number)(x => x))

def initBoard(numRows: Int, numRowCards: Int, deck: Deck): (Board, Deck) = {
  (
    Board(
      rows = Vector.tabulate(numRows)(index => {
        Row(
          nummer = index + 1,
          cards = Vector.tabulate(numRowCards)(count =>
            if count == 0 then deck.cards(index) else 0
          )
        )
      }),
      playedCards = Vector.empty
    ),
    deck.remcount(numRows)
  )
}

def initAllPlayers(
    numPlayer: Int,
    numHandCards: Int,
    input: () => String,
    deck: Deck
): (Vector[Player], Deck) = {
  (
    Vector.tabulate(numPlayer)(index => {
      println(s"Spielername${index + 1}: ")
      Player(
        name = input(),
        cards = Vector.tabulate(numHandCards)(count =>
          deck.cards(count + numHandCards * index)
        ),
        ochsen = 0
      )
    }),
    deck.remcount(numHandCards * numPlayer)
  )
}

def updateGamestate(
    gameState: GameState,
    cardToPlay: Vector[(Int, Player)]
): GameState = {
  gameState.copy(
    playersWithPlayedCards = for {
      pl <- gameState.players
      card <- cardToPlay.filter(f => f._2 == pl).head._1
      updatedPlayer = pl.playCard(card)
    } yield (card, updatedPlayer),
    players = gameState.players.map { pl =>
      gameState.playersWithPlayedCards
        .collectFirst {
          case (card, updatedPlayer) if updatedPlayer.name == pl.name =>
            updatedPlayer
        }
        .getOrElse(pl)
    }
  )
}
