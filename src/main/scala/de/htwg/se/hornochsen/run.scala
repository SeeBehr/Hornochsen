package de.htwg.se.hornochsen

import scala.io.StdIn.readLine

case class GameState(players: AllPlayers, board: Board) {

}

// Start of the Programm.
@main
def start() =  {
    println("Anzahl Karten pro Spieler:")
    val numHandCards: Int = readLine().toInt
    println("Anzahl Spieler: ")
    val numPlayer: Int = readLine().toInt

    val allP: AllPlayers = initAllPlayers(numPlayer=numPlayer, numHandCards=numHandCards, input=readLine)
    
    println("Mit wie vielen Reihen wird gespielt: ")
    val numRows: Int = readLine().toInt
    println("Wie viele Karten pro Reihe: ")
    val numRowCards: Int = readLine().toInt

    val board: Board = intiBoard(numRows=numRows, numRowCards=numRowCards)

    var gameState: GameState = GameState(allP, board)

    println("Spielbeginn: ")
    

    while true do {
        var playersWithPlayedCards: Vector[(Int, Player)] = gameState.players.player.collect[(Int, Player)](
            (player) => ({
                val playedCard = readLine(s"Welche karte soll gelegt werden, ${player.name}? ").toInt
                (playedCard, player.playCard(playedCard))
            })
        )

        while (playersWithPlayedCards.length != 0) do {
            val ((whichCard, player), playersWithPlayedCards1) = which(playersWithPlayedCards)
            println(s"Spieler ${player.name} legt Karte $whichCard. ")
            playersWithPlayedCards = playersWithPlayedCards1
        }
    }

}