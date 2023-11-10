package de.htwg.se.hornochsen.aview

import de.htwg.se.hornochsen.controler._
import de.htwg.se.hornochsen.model._
import de.htwg.se.hornochsen.util._
import scala.io.StdIn.readLine

// Start of the Programm.
case class TUI(controller: Controler) extends Observer{
    /*
    def init(): (Int, Int, Int, Int)= {
        println("Anzahl Karten pro Spieler:")
        val numHandCards: Int = readLine().toInt
        println("Anzahl Spieler: ")
        val numPlayer: Int = readLine().toInt

        println("Mit wie vielen Reihen wird gespielt: ")
        val numRows: Int = readLine().toInt
        println("Wie viele Karten pro Reihe: ")
        val numRowCards: Int = readLine().toInt
        (numHandCards, numPlayer, numRows, numRowCards)
    }*/
    override def update(e: Event) = {
        println(controller.gameState.board.toString())
        println(controller.gameState.players.toString())
    }
    
    def run = {
        update(Event.playRound)
        updateGamestate(controller.gameState, playCards(controller.gameState.players), WhichRowTake)
    }

    def playCards(players: Vector[Player]): Vector[(Int,Player)] = {
        players.map(p=>
            (readLine(s"Wslche Karte soll ${p.name} legen?: ").toInt, p)
            )
    }

    def WhichRowTake(name: String): Int = {
        return readLine(s"Welche Reihe nimmt ${name}? ").toInt
    }
}