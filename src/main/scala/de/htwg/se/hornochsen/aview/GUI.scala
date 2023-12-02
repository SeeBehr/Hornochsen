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
import scalafx.stage.Screen

class GUI(controler: Controler, numRows: Int) extends UI{
    object InitStage extends JFXApp3 {
        override def start(): Unit = {
            val windowHeight: Double = Screen.primary.bounds.height
            val windowWidth: Double = Screen.primary.bounds.width
            stage = new JFXApp3.PrimaryStage {
                println("window height: " + windowHeight)
                println("window width: " + windowWidth)
                title = "Hornochsen"
                scene = new Scene {
                    fill = Black
                    content = new VBox {
                        children = Seq(
                        new HBox {
                            children = Seq(
                            new VBox {
                                children = (for (i <- 1 to numRows) yield
                                new HBox {
                                    border = new Border(new BorderStroke(White, BorderStrokeStyle.Solid, CornerRadii.Empty, BorderWidths.Default))
                                    alignment = Pos.CenterLeft
                                    children = Seq(
                                    new Text {
                                        textAlignment = TextAlignment.Center
                                        text = "Button Take "
                                        style = "-fx-font-size: 10pt"
                                        fill = White
                                        prefHeight = ((3.0/5)*windowHeight)/numRows
                                        prefWidth = (2.0/3)*windowWidth
                                    },
                                    new Text {
                                        textAlignment = TextAlignment.Center
                                        text = s"Row $i: "
                                        style = "-fx-font-size: 30pt"
                                        fill = White
                                        prefHeight = ((3.0/5)*windowHeight)/numRows
                                        prefWidth = (2.0/3)*windowWidth
                                    },
                                    new Text {
                                        textAlignment = TextAlignment.Center
                                        text = controler.gameState.board.rows(i-1).cards.mkString(", ")
                                        style = "-fx-font-size: 30pt"
                                        fill = White
                                        prefHeight = ((3.0/5)*windowHeight)/numRows
                                        prefWidth = (2.0/3)*windowWidth
                                    })
                                }).toList
                                println("top height: " + ((3.0/5)*windowHeight)/numRows)
                                prefHeight = (3.0/5)*windowHeight
                                prefWidth = (2.0/3)*windowWidth
                            },
                            new VBox {
                                alignment = Pos.Center
                                children = Seq(
                                new Text {
                                    textAlignment = TextAlignment.Center
                                    text = "Undo"
                                    style = "-fx-font-size: 30pt"
                                    fill = White
                                    prefHeight = ((3.0/5)*windowHeight)/2
                                    prefWidth = (1.0/3)*windowWidth
                                },
                                new Text {
                                    textAlignment = TextAlignment.Center
                                    text = "Redo"
                                    style = "-fx-font-size: 30pt"
                                    fill = White
                                    prefHeight = ((3.0/5)*windowHeight)/2
                                    prefWidth = (1.0/3)*windowWidth
                                })
                            })
                        },
                        new HBox {
                            border = new Border(new BorderStroke(White, BorderStrokeStyle.Solid, CornerRadii.Empty, BorderWidths.Default))
                            alignment = Pos.Center
                            padding = Insets(10)
                            children = Seq(
                            new Text {
                                textAlignment = TextAlignment.Left
                                text = "Spielername:" + controler.gameState.players(0).name
                                style = "-fx-font-size: 30pt"
                                fill = White
                            },
                            new Text {
                                textAlignment = TextAlignment.Center
                                text = "Handkarten: " + controler.gameState.players(0).cards.mkString(", ")
                                style = "-fx-font-size: 30pt"
                                fill = White
                            },
                            new Text {
                                textAlignment = TextAlignment.Right
                                text = "Ochsen: " + controler.gameState.players(0).ochsen
                                style = "-fx-font-size: 30pt"
                                fill = White
                            })
                            println("bottom height: " + (2.0/5)*windowHeight)
                            prefHeight = (2.0/5)*windowHeight
                            prefWidth = windowWidth
                        })
                    }
                }
                fullScreen = true
            }
        }
    }
    
    override def end: Unit = ???

    override def run: Unit = controler.run(playCards(controler.gameState.players, input), input, WhichRowTake, output)

    def input(): String = {
        " "
    }

    def output(s: String): Unit = {

    }

    override def WhichRowTake(name: String, read: () => String): Int = ???

    override def playCards(players: Vector[Player], read: () => String): Vector[(Int, Player)] = ???

    def undo(): Unit = {

    }

    override def update(e: Event): Unit = {
        e match {
            case Event.Undo => undo()
            case Event.PlayRound => run
            case Event.RoundFinished =>
            case Event.CardsSelected =>
            case Event.End => end
            case Event.GameStart => InitStage.main(Array())
        }
    }
}