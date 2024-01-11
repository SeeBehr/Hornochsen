package de.htwg.se.hornochsen.model.BaseModel

import de.htwg.se.hornochsen.model
import model.{InterfaceBoard, InterfaceRow, InterfacePlayer, InterfaceDeck, InterfaceGameState}
import play.api.libs.json._
import java.io._
import de.htwg.se.hornochsen.model.*
import java.net.InterfaceAddress
import scalafx.scene.input.KeyCode.G


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

    override def saveToJson(file: String): Unit = {
        val pw = new PrintWriter(new File(file))
        pw.write(
            play.api.libs.json.Json.obj(
                "playerswaiting" -> playersWaiting.map(_.saveToJson),
                "playeractive" -> playerActive.saveToJson,
                "playersdone" -> playersDone.map(_.saveToJson),
                "board" -> board.saveToJson,
                "remDeck" -> remDeck.saveToJson
            ).toString
        )
        pw.close()
    }

    override def loadFromJson(file: String): InterfaceGameState = {
        val p = makePlayer()
        val b = makeDummyBoard()
        val d = makeDummyDeck()
        val json = Json.parse(file)
        val jsonPlayersWaiting = (json \ "playerswaiting").as[JsArray]
        val playersWaiting = (for (i <- jsonPlayersWaiting.value) yield p.loadFromJson(i)).toVector
        val jsonPlayerActive = (json \ "playeractive").get
        val playerActive = p.loadFromJson(jsonPlayerActive)
        val jsonPlayersDone = (json \ "playersdone").as[JsArray]
        val playersDone = (for (i <- jsonPlayersDone.value) yield p.loadFromJson(i)).toVector
        val jsonBoard = (json \ "board").get
        val board = b.loadFromJson(jsonBoard)
        val jsonRemDeck = (json \ "remDeck").get
        val remDeck = d.loadFromJson(jsonRemDeck)
        return GameState(playersWaiting, playerActive, playersDone, board, remDeck)
    }

    override def saveToXML(file: String): String = {
        return " "
    }

    override def loadFromXML(file: String): InterfaceGameState = {
        return GameState(playersWaiting, playerActive, playersDone, board, remDeck)
    }
}