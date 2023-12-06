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
import scalafx.scene.control.Button
import scalafx.event.{EventHandler, ActionEvent}

class GUI(controler: Controler) extends UI{
    object InitStage extends JFXApp3 {
        override def start(): Unit = {
            val windowHeight: Double = Screen.primary.bounds.height
            val windowWidth: Double = Screen.primary.bounds.width
            stage = new JFXApp3.PrimaryStage {
                title = "Hornochsen"
                scene = new Scene {
                    fill = Black
                    content = new VBox {
                        children = Seq(
                            new HBox {
                                children = Seq(
                                    new VBox {
                                        children = (for (i <- 1 to controler.gameState.board.rows.size) yield
                                            new HBox {
                                                border = new Border(new BorderStroke(White, BorderStrokeStyle.Solid, CornerRadii.Empty, BorderWidths.Default))
                                                alignment = Pos.CenterLeft
                                                children = Seq(
                                                    new Button {
                                                        text = "Take"
                                                        style = "-fx-font-size: 10pt"
                                                        onAction = (event) => {
                                                            print("ButtonWorks!\n")
                                                        }
                                                    },
                                                    new Text {
                                                        textAlignment = TextAlignment.Center
                                                        text = s"Row $i: "
                                                        style = "-fx-font-size: 30pt"
                                                        fill = White
                                                        prefHeight = ((3.0/5)*windowHeight)/controler.gameState.board.rows.size
                                                        prefWidth = (2.0/3)*windowWidth
                                                    },
                                                    new Text {
                                                        textAlignment = TextAlignment.Center
                                                        text = controler.gameState.board.rows(i-1).cards.mkString(", ")
                                                        style = "-fx-font-size: 30pt"
                                                        fill = White
                                                        prefHeight = ((3.0/5)*windowHeight)/controler.gameState.board.rows.size
                                                        prefWidth = (2.0/3)*windowWidth
                                                    }
                                                )
                                            }
                                        ).toList
                                        prefHeight = (3.0/5)*windowHeight
                                        prefWidth = (2.0/3)*windowWidth
                                    },
                                    new VBox {
                                        border = new Border(new BorderStroke(White, BorderStrokeStyle.Solid, CornerRadii.Empty, BorderWidths.Default))
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
                                            }
                                        )
                                    }
                                )
                            },
                            new HBox {
                                border = new Border(new BorderStroke(White, BorderStrokeStyle.Solid, CornerRadii.Empty, BorderWidths.Default))
                                alignment = Pos.Center
                                children = Seq(
                                    new Text {
                                        alignment = Pos.Center
                                        textAlignment = TextAlignment.Left
                                        text = controler.gameState.players(0).name
                                        style = "-fx-font-size: 30pt"
                                        fill = White
                                        prefWidth = windowWidth/3
                                    },
                                    new HBox {
                                        alignment = Pos.Center
                                        children = (for (i <- 0 to controler.gameState.players(0).cards.length - 1) yield
                                            new Button {
                                                alignment = Pos.Center
                                                text = controler.gameState.players(0).cards(i).toString
                                                style = "-fx-font-size: 30pt"
                                                onAction = (event) => {
                                                    print(s"Play Card ${controler.gameState.players(0).cards(i)}")
                                                }
                                            }
                                        ).toList
                                    },
                                    new Text {
                                        alignment = Pos.Center
                                        textAlignment = TextAlignment.Right
                                        text = "Ochsen: " + controler.gameState.players(0).ochsen
                                        style = "-fx-font-size: 30pt"
                                        fill = White
                                        prefWidth = windowWidth/3
                                    }
                                )
                                prefHeight = (2.0/5)*windowHeight
                                prefWidth = windowWidth
                            }
                        )
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

    override def playCards(players: Vector[Player], read: () => String): Vector[(Int, Player)] = {
        Event match {

        }
    }

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