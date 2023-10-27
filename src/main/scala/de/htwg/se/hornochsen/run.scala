package de.htwg.se.hornochsen

import scala.io.StdIn.readLine
// Start of the Programm.
@main
def start() =  {
    println("Anzahl Karten pro Spieler:")
    val numHandCards: Int = readLine().toInt
    println("Anzahl Spieler: ")
    val numPlayer: Int = readLine().toInt

    val allP: AllPlayers = initAllPlayers(numPlayer=numPlayer, numHandCards=numHandCards, input=readLine)
    
    println("Mit wie viele Reihen wird gespielt: ")
    val numRows: Int = readLine().toInt
    println("Wie viele Karten pro Reihe: ")
    val numRowCards: Int = readLine().toInt
    val board: Board = intiBoard(numRows=numRows, numRowCards=numRowCards)
    println(allP.toString() + "\n" + board.toString())
}