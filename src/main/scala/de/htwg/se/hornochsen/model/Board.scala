package de.htwg.se.hornochsen.model

case class Row(val nummer: Int, val cards: Vector[Int], val filled: Int = 1) {
  override def toString(): String = {
    "Row " + nummer + ": " + cards.mkString(", ") + " filled: " + filled
      .toString() + "\n"
  }
}

case class Board(
    val rows: Vector[Row],
    val playedCards: Vector[(Int, Player)] = Vector.empty
) {
  override def toString(): String = {
    "Board:\n\t" + rows.mkString("\n\t") + "\nPlayed cards: " + playedCards
      .mkString(", ") + "\n"
  }

  def addCard(playedCard: Int, num: Int): Board = {
    Board(
      rows = rows.updated(
        (num - 1),
        Row(
          nummer = num,
          rows((num - 1)).cards
            .updated(rows(num - 1).cards(rows(num - 1).filled), playedCard),
          filled = rows((num - 1)).filled + 1
        )
      ),
      playedCards = playedCards.filter(x => x._1 != playedCard)
    )
  }

  def takeRow(card: Int, index: Int): (Board, Int) = {
    (
      Board(
        rows = rows.updated(
          index,
          Row(
            nummer = index + 1,
            cards = rows(index).cards.zipWithIndex.map(f =>
              if f._2 == 0 then card else 0
            ),
            filled = 1
          )
        ),
        playedCards = this.playedCards
      ),
      this
        .rows(index)
        .cards
        .map(f => if f % 10 == 0 then 10 else if f % 5 == 0 then 5 else 1)
        .sum
    )
  }
}
