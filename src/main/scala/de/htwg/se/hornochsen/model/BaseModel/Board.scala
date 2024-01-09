package de.htwg.se.hornochsen.model.BaseModel

import scala.util.{Try, Success, Failure}
import de.htwg.se.hornochsen.model.{InterfacePlayer, InterfaceDeck, InterfaceBoard, InterfaceRow, makePlayer, makeDummyRow}
import play.api.libs.json._

case class Row(val Nummer: Int, val myCards: Vector[Int], val Filled: Int = 1) extends InterfaceRow {
    var pvalue: Int = myCards.map(f => if f == 0 then 0 else if f % 10 == 0 then 10 else if f % 5 == 0 then 5 else 1).sum

    override def value: Int = pvalue
    
    override def toString(): String = {
        "Row " + Nummer + ": " + myCards.mkString(", ") + " filled: " + filled.toString() + "\n"
    }

    override def nummer: Int = Nummer

    override def copy(nummer: Int = Nummer, Cards: Vector[Int] = myCards, filled: Int = Filled): InterfaceRow = {
        Row(nummer, Cards, filled)
    }

    override def filled: Int = Filled

    override def cards: Vector[Int] = myCards

    override def toJSON: JsValue = {
        Json.obj(
            "nummer" -> nummer,
            "cards" -> cards,
            "filled" -> filled,
            "value" -> value
        )
    }

    override def load(json: JsValue): InterfaceRow = {
        val nummer = (json \ "nummer").as[Int]
        val cards = (json \ "cards").as[Vector[Int]]
        val filled = (json \ "filled").as[Int]
        val value = (json \ "value").as[Int]
        Row(nummer, cards, filled)
    }
}

case class Board(val rows: Vector[InterfaceRow], var playedCards: Vector[(Int, InterfacePlayer)] = Vector.empty) extends InterfaceBoard {
    override def toString(): String = {
        ("Board:\n\t" + rows.mkString("\n\t") + "\n")
    }

    override def copy(
        myRows: Vector[InterfaceRow] = rows,
        playedCards: Vector[(Int, InterfacePlayer)] = playedCards
    ): InterfaceBoard = {
        Board(myRows, playedCards)
    }

    override def playedCardsToString: String = {
        ("\nPlayed cards: \n"+ playedCards.map(p=>s"${p._1}, von ${p._2.name}\n").mkString(""))
    }

    def addCard(playedCard: Int, num: Int): Board = {
        val indexR: Int = num-1
        val indexC: Int = rows(indexR).filled
        Board(
            rows = rows.updated((num - 1),
            Row(Nummer = num, rows(indexR).cards.updated(indexC, playedCard),
            Filled = rows((num - 1)).filled + 1)),
            playedCards = playedCards.filter(x => x._1 != playedCard)
        )
    }

    def takeRow(card: Int, nummer: Int): (Board, Int) = {
        val returnRow = this.rows(nummer)
        (Board(
            rows = rows.updated(
                nummer-1,
                Row(
                    Nummer = nummer,
                    myCards = rows(nummer).cards.zipWithIndex.map(f => if f._2 == 0 then card else 0),
                    Filled = 1
                )
            ),
            playedCards = Try(playedCards.tail) match {
                case Success(value) => value
                case Failure(exception) => Vector.empty
            }
        ),
        returnRow.cards.map(f => if f == 0 then 0 else if f % 10 == 0 then 10 else if f % 5 == 0 then 5 else 1).sum
        )
    }

    override def toJSON: JsValue = {
        Json.obj(
            "rows" -> rows.map(_.toJSON),
            "playedCards" -> playedCards.map(p => Json.obj(
                "card" -> p._1,
                "player" -> p._2.toJSON
            )) 
        )
    }

    override def load(json: JsValue): InterfaceBoard = {
        val r = makeDummyRow()
        val p = makePlayer()
        val jsonRows = (json \ "rows").as[JsArray]
        val rows = (for (i <- jsonRows.value) yield r.load(i)).toVector
        val jsonPlayedCards = (json \ "playedCards").as[JsArray]
        val playedCards = (for (i <- jsonPlayedCards.value) yield ((i \ "card").as[Int], p.load((i \ "player").as[JsValue]))).toVector
        Board(rows, playedCards)
    }
}

