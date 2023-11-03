package de.htwg.se.hornochsen.controler
import de.htwg.se.hornochsen.model.Board
import de.htwg.se.hornochsen.model.Player

def which(cards: Vector[(Int, Player)]): ((Int, Player), Vector[(Int, Player)]) = {
    var min = cards.min((x,y)=>x._1-y._1)
    return (min, cards.filter(x => x._1 != min._1))
}


def where(b: Board, card: Int): Int = {
    val lastElements: Vector[Int] = b.rows.map(row => row.cards(row.filled))
    val minRow = lastElements.map(x => card-x).filter(x=>x>0).min((x,y) => y-x)
    return lastElements.indexOf(minRow)
}

def canAdd(b: Board, index: Int): Boolean = {
    if index < 0 | index > b.rows(0).cards.length then
    return false
    return b.rows(index).filled < b.rows(index).cards.length
}