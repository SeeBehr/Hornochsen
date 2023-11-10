package de.htwg.se.hornochsen.model

case class Row(val nummer: Int, val cards: Vector[Int], val filled: Int = 1) {
	override def toString(): String = {
		"Row " + nummer + ": " + cards.mkString(", ") + " filled: " + filled
		.toString() + "\n"
	}
}

case class Board(
    val rows: Vector[Row],
    var playedCards: Vector[(Int, Player)] = Vector.empty
) {
	override def toString(): String = {
		"Board:\n\t" + rows.mkString("\n\t") + "\nPlayed cards: \n" + playedCards.map(p=>s"${p._1}, von ${p._2.name}\n").mkString("")
	}
	
	def addCard(playedCard: Int, num: Int): Board = {
        val indexR: Int = num-1
        val indexC: Int = rows(indexR).filled
        println("index:" + indexC)
        Board(rows = rows.updated((num - 1),
            Row(nummer = num, rows(indexR).cards.updated(indexC, playedCard),
            filled = rows((num - 1)).filled + 1)),
            playedCards = playedCards.filter(x => x._1 != playedCard)
            )
    }

	def takeRow(card: Int, nummer: Int): (Board, Int) = {
        val returnRow = this.rows(nummer-1)
        (
		Board(
			rows = rows.updated(
			nummer-1,
			Row(
				nummer = nummer,
				cards = rows(nummer-1).cards.zipWithIndex.map(f =>
				if f._2 == 0 then card else 0
				),
				filled = 1
			)
			),
			playedCards = this.playedCards
		),
		returnRow.cards
			.map(f => if f % 10 == 0 then 10 else if f % 5 == 0 then 5 else 1).sum
		)
    }
}
