package de.htwg.se.hornochsen.model.BaseModel

import de.htwg.se.hornochsen.model
import model.{InterfaceBoard, InterfaceRow, InterfacePlayer, InterfaceDeck, InterfaceGameState}
import play.api.libs.json._
import java.io._
import de.htwg.se.hornochsen.model.*
import java.net.InterfaceAddress


case class GameState (
    var playerswaiting: Vector[InterfacePlayer],
    var playeractive: InterfacePlayer,
    var playersdone: Vector[InterfacePlayer],
    var myBoard: InterfaceBoard,
    var RemDeck: InterfaceDeck
) extends InterfaceGameState {
    override def toString(): String = {
        "Gamestate: \n\n" + "Players: \n" + players.mkString("\n") + "\n" + board.toString() + remDeck.toString() + "\n"
    }

    override def playersDone: Vector[InterfacePlayer] = playersdone
    
    override def playerActive: InterfacePlayer = playeractive

    override def playersWaiting: Vector[InterfacePlayer] = playerswaiting
    
    override def players: Vector[InterfacePlayer] = {
        if playerActive != makePlayer() then
            playersDone.appended(playerActive).appendedAll(playersWaiting)
        else 
            playersDone.appendedAll(playersWaiting)
    }

    override def copy(
        playerswaiting: Vector[InterfacePlayer] = playersWaiting,
        playeractive: InterfacePlayer = playerActive,
        playersdone: Vector[InterfacePlayer] = playersDone,
        Board: InterfaceBoard = board,
        remDeck: InterfaceDeck = remDeck
    ): InterfaceGameState = {
        GameState(playerswaiting, playeractive, playersdone, Board, remDeck)
    }

    override def board: InterfaceBoard = myBoard

    override def remDeck: InterfaceDeck = RemDeck

    override def save: Unit = {
        val pw = new PrintWriter(new File("Save/gamestate.json"))
        pw.write(this.toJSON.toString)
        pw.close()
    }

    def toJSON: play.api.libs.json.JsValue = {
        play.api.libs.json.Json.obj(
            "playerswaiting" -> playersWaiting.map(_.toJSON),
            "playeractive" -> playerActive.toJSON,
            "playersdone" -> playersDone.map(_.toJSON),
            "board" -> board.toJSON,
            "remDeck" -> remDeck.toJSON
        )
    }

    override def load(file: String): InterfaceGameState = {
        val p = makePlayer()
        val b = makeDummyBoard()
        val d = makeDummyDeck()
        val json = Json.parse(file)
        val jsonPlayersWaiting = (json \ "playerswaiting").as[JsArray]
        val playersWaiting = (for (i <- jsonPlayersWaiting.value) yield p.load(i)).toVector
        val jsonPlayerActive = (json \ "playeractive").get
        val playerActive = p.load(jsonPlayerActive)
        val jsonPlayersDone = (json \ "playersdone").as[JsArray]
        val playersDone = (for (i <- jsonPlayersDone.value) yield p.load(i)).toVector
        val jsonBoard = (json \ "board").get
        val board = b.load(jsonBoard)
        val jsonRemDeck = (json \ "remDeck").get
        val remDeck = d.load(jsonRemDeck)
        return GameState(playersWaiting, playerActive, playersDone, board, remDeck)
    }
}