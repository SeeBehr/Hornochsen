package de.htwg.se.hornochsen.aview

import scala.io.StdIn.readLine
import de.htwg.se.hornochsen.controler._

// Start of the Programm.
@main
def start() = {
	println("Anzahl Karten pro Spieler:")
	val numHandCards: Int = readLine().toInt
	println("Anzahl Spieler: ")
	val numPlayer: Int = readLine().toInt

	println("Mit wie vielen Reihen wird gespielt: ")
	val numRows: Int = readLine().toInt
	println("Wie viele Karten pro Reihe: ")
	val numRowCards: Int = readLine().toInt
	var deck = initDeck(120).shuffle()
	val (board, playerdeck) =
		initBoard(numRows = numRows, numRowCards = numRowCards, deck = deck)
	val (allP, refilldeck) = initAllPlayers(
		numPlayer = numPlayer,
		numHandCards = numHandCards,
		input = readLine,
		deck = playerdeck
	)
	var gameState: GameState = GameState(allP, board, refilldeck)

	println("Spielbeginn: ")

	while true do {
		println(allP.mkString("\n") + "\n" + board.toString())

		gameState = updateGamestate(
		gameState,
		(gameState.players.map(p =>
			(readLine(s"Welche Karte soll ${p.name} legen? ").toInt, p)
		)),
		whichRowTake
		)
	}
}
def whichRowTake(name: String): Int = {
  	return readLine(s"Welche Reihe nimmt ${name}? ").toInt
}