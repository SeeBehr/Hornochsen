package de.htwg.se.hornochsen

case class Row(val nummer: Int, val cards: Vector[Int], val filled: Int = 0) {
    override def toString(): String = {
        "Row " + nummer + ": " + cards.mkString(", ")
    }

    def addCard(playedCard: Int): Row = {
        Row(nummer, cards.appended(playedCard), filled + 1)
    }
}

case class Board(val rows: Vector[Row], val playedCards: Vector[(Int, Player)]){
    override def toString(): String = { 
        "Board:\n\t" + rows.mkString("\n\t") + "\n"
    }

    def addCard(playedCard: Int, num: Int): Board = {
        Board(rows=rows.updated(num, Row(num, rows(num).cards.updated(rows(num).cards(rows(num).filled), playedCard)))
        ,playedCards=playedCards.filter(x=>x._1!=playedCard))
    }
}

def intiBoard(numRows: Int, numRowCards: Int): Board = {
    Board(rows=Vector.tabulate(numRows)(index =>{
        Row(nummer=index+1, cards=Vector.tabulate(numRowCards)(count => 0))
    }), playedCards=Vector.empty)
}