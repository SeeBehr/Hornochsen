package de.htwg.se.hornochsen.aview

import scala.io.StdIn.readLine
import de.htwg.se.hornochsen.controler.which
import de.htwg.se.hornochsen.model.{Board, initBoard, Player, initAllPlayers, AllPlayers, Deck, initDeck}

case class GameState(players: AllPlayers, board: Board, remDeck: Deck) {

}

// Start of the Programm.
@main
def start() =  {
    println("Anzahl Karten pro Spieler:")
    val numHandCards: Int = readLine().toInt
    println("Anzahl Spieler: ")
    val numPlayer: Int = readLine().toInt
    
    println("Mit wie vielen Reihen wird gespielt: ")
    val numRows: Int = readLine().toInt
    println("Wie viele Karten pro Reihe: ")
    val numRowCards: Int = readLine().toInt
    var deck = initDeck(120).shuffle()
    val (board, playerdeck) = initBoard(numRows=numRows, numRowCards=numRowCards, deck=deck)
    val (allP, refilldeck) = initAllPlayers(numPlayer=numPlayer, numHandCards=numHandCards, input=readLine, deck = playerdeck)
    var gameState: GameState = GameState(allP, board, refilldeck)

    println("Spielbeginn: ")
    

    while true do {
        println(allP.toString() + "\n" + board.toString())
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