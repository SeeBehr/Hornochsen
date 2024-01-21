package de.htwg.se.hornochsen.model.BaseModel

import de.htwg.se.hornochsen.model
import de.htwg.se.hornochsen.model.*
import play.api.libs.json.*
import java.io.*
import scala.xml.XML


case class GameState (
    //Players that are waiting for their turn
    var playerswaiting: Vector[InterfacePlayer],
    //Player that is currently playing
    var playeractive: InterfacePlayer,
    //Players that are done playing
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
    
    /* 
     Returns a Vector of all Players in the Gamestate
     */
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

    override def saveToXML(file: String): Unit = {
        val pw = new PrintWriter(new File(file))
        pw.write(
          "<gamestate>" +
            "<playerswaiting>" + playersWaiting.map(_.saveToXML()).mkString + "</playerswaiting>" +
            "<playeractive>" + playerActive.saveToXML() + "</playeractive>" +
            "<playersdone>" + playersDone.map(_.saveToXML()).mkString + "</playersdone>" +
            board.saveToXML() +
            remDeck.saveToXML() +
            "</gamestate>"
        )
        pw.close()
    }

    override def loadFromXML(file: String): InterfaceGameState = {
        val p = makePlayer()
        val b = makeDummyBoard()
        val d = makeDummyDeck()

        val xml = XML.loadFile(file).head
        val playersWaiting = (xml \ "playerswaiting" \ "player").map(p.loadFromXML).toVector

        val playerActive = p.loadFromXML((xml \ "playeractive" \ "player").head)
        val playersDone = (xml \ "playersdone" \ "player").map(p.loadFromXML).toVector
        val board = b.loadFromXML((xml \ "board").head)
        val remDeck = d.loadFromXML((xml \ "deck").head)

        GameState(playersWaiting, playerActive, playersDone, board, remDeck)
    }
}