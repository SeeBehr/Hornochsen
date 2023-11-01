package de.htwg.se.hornochsen

def whichCardToPut(cards: Vector[Int]): (Int, Vector[Int]) = {
    var min: Int = Int.MaxValue
    cards.foreach(f => if f < min then min = f)
    (min, Vector.tabulate(cards.length-1)(cards.filterNot(x => x == min)))
}

def whereToPut(b: Board, card: Int): (Int, Int) = {
    var dist: Int = Int.MaxValue
    var row: Int = -1;
    b.rows.foreach(f => if ((card - f.cards[f.filled]) > 0) && ((card - f.cards[f.filled]) < dist) then{
        dist = card - f.cards[f.filled]
        row = f.nummer;
    })
    (dist, row)
}

def where(b: Board, card: Int): Int = {
    val lastElements: Vector[Int] = b.rows.map(row => row[row.filled])
    val minRow = lastElements.min((x,y) => y-x)
    return lastElements.indexOf(minRow)
}