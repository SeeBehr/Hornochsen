package de.htwg.se.hornochsen
// add case in front so you don't have to write new
case class Row(val nummer: Int, val cards: Vector[Int]) {
    override def toString(): String = {
        "Row " + nummer + ": " + cards.mkString(", ")
    }
}

// add case in front so you don't have to write new
case class Board(val rows: Vector[Row]){
    override def toString(): String = { 
        "Board:\n\t" + rows.mkString("\n\t") + "\n"
    }
}