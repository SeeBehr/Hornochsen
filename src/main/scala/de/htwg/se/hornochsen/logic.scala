package de.htwg.se.hornochsen

def which(cards: Vector[(Int, Player)]): ((Int, Player), Vector[(Int, Player)]) = {
    var min = cards.min((x,y)=>y._1-x._1)
    return (min, cards.filter(x => x._1 != min._1))
}


def where(b: Board, card: Int): Int = {
    val lastElements: Vector[Int] = b.rows.map(row => row.cards(row.filled))
    val minRow = lastElements.min((x,y) => y-x)
    return lastElements.indexOf(minRow)
}

def canAdd(b: Board, index: Int): Boolean = {
    return b.rows(index).filled < 6
}