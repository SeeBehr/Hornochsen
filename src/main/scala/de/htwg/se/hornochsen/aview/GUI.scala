package de.htwg.se.hornochsen.aview
import de.htwg.se.hornochsen.util.Event
import de.htwg.se.hornochsen.model.Player
import de.htwg.se.hornochsen.controler.Controler

import scalafx.application.JFXApp3
import scalafx.scene._
import scalafx.scene.paint.Color._
import scalafx.scene.paint._
import scalafx.scene.layout._
import scalafx.geometry.Insets
import scalafx.scene.text.Text
import scalafx.geometry.Pos
import scalafx.scene.text.TextAlignment
import scalafx.Includes._

class GUI(controler: Controler, numRows: Int) extends UI{
    val windowWidth = 1920
    val windowHeight = 1200
    object Stage extends JFXApp3 {
        override def start(): Unit = {
            stage = new JFXApp3.PrimaryStage {
                title = "Hornochsen"
                scene = new Scene {
                    fill = Black
                    content = new VBox {
                        prefHeight = windowHeight
                        prefWidth = windowWidth
                        children = Seq(
                            new VBox {
                                children = (for (i <- 1 to numRows) yield
                                    new HBox {
                                        prefHeight = (3/4)*windowHeight/numRows
                                        prefWidth = windowWidth
                                        border = new Border(new BorderStroke(White, BorderStrokeStyle.Solid, CornerRadii.Empty, BorderWidths.Default))
                                        alignment = Pos.CenterLeft
                                        children = Seq(
                                            new Text {
                                                textAlignment = TextAlignment.Left
                                                text = s"Row $i: "
                                                style = "-fx-font-size: 30pt"
                                                fill = White
                                            },
                                            new Text {
                                                textAlignment = TextAlignment.Center
                                                text = "Cards"
                                                style = "-fx-font-size: 30pt"
                                                fill = White
                                            }
                                        )
                                    }).toList
                            },
                            new HBox {
                                prefHeight = (1/4)*windowHeight
                                prefWidth = windowWidth
                                border = new Border(new BorderStroke(White, BorderStrokeStyle.Solid, CornerRadii.Empty, BorderWidths.Default))
                                alignment = Pos.CenterLeft
                                children = Seq(
                                    new Text {
                                        textAlignment = TextAlignment.Left
                                        text = "Spielername:"
                                        style = "-fx-font-size: 30pt"
                                        fill = White
                                    },
                                    new Text {
                                        textAlignment = TextAlignment.Center
                                        text = "Handkarten"
                                        style = "-fx-font-size: 30pt"
                                        fill = White
                                    },
                                    new Text {
                                        textAlignment = TextAlignment.Right
                                        text = "Ochsen"
                                        style = "-fx-font-size: 30pt"
                                        fill = White
                                    }
                                )
                            },
                        )
                    }
                }
                fullScreen = true
            }
        }
    }
    
    override def end: Unit = ???

    override def run: Unit = ???

    override def WhichRowTake(name: String, read: () => String): Int = ???

    override def playCards(players: Vector[Player], read: () => String): Vector[(Int, Player)] = ???

    override def update(e: Event): Unit = {
        e match {
            case Event.Undo =>
            case Event.PlayRound => 
            case Event.RoundFinished =>
            case Event.CardsSelected =>
            case Event.End => end
            case Event.NextRound =>
            case Event.GameStart => Stage.main(Array())
        }
    }
}