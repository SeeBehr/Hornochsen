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
import scalafx.scene.control.Alert.AlertType
import javafx.scene.control.Alert

class GUI(controler: Controler) extends UI {
    var GUIState: Event = Event.GameStart;
    object Play extends JFXApp3 {
        override def start(): Unit = {
            stage = mystage()
        }
        def mystage(darkmode: Boolean = true, windowHeight: Double = Screen.primary.bounds.height, windowWidth: Double = Screen.primary.bounds.width): JFXApp3.PrimaryStage = {
            new JFXApp3.PrimaryStage {
                title = "Hornochsen"
                scene = new Scene {
                    if darkmode then fill = Black
                    else fill = White
                    content = new VBox {
                        children = Seq(
                            new HBox {
                                children = Seq(
                                    reihen(darkmode, windowHeight, windowWidth),
                                    GUIState match
                                    case Event.GameStart =>	
                                        undoRedo(darkmode, windowHeight, windowWidth);
                                    case Event.PlayRound =>
                                        undoRedo(darkmode, windowHeight, windowWidth);
                                    case Event.CardsSelected =>
                                        selectedCards(darkmode, windowHeight, windowWidth);
                                    case Event.RoundFinished =>
                                        undoRedo(darkmode, windowHeight, windowWidth);
                                    case Event.End =>
                                        undoRedo(darkmode, windowHeight, windowWidth);
                                    case Event.Undo =>
                                        undoRedo(darkmode, windowHeight, windowWidth);
                                )
                            },
                            player(darkmode, windowHeight, windowWidth, controler.gameState.players(0)),
                        )
                    }
                }
                fullScreen = true
            }
        }

        def reihen(darkmode: Boolean, windowHeight: Double, windowWidth: Double): VBox = {
            new VBox {
                children = (for (i <- 1 to controler.gameState.board.rows.size) yield
                    new HBox {
                        border = new Border(new BorderStroke(if darkmode then White else Black, BorderStrokeStyle.Solid, CornerRadii.Empty, BorderWidths.Default))
                        alignment = Pos.CenterLeft
                        children = Seq(
                            new Button {
                                text = "Take"
                                style = "-fx-font-size: 10pt"
                                onAction = (event) => {
                                    print(f"Take Row $i\n")
                                }
                            },
                            new Text {
                                textAlignment = TextAlignment.Center
                                text = s"Row $i: "
                                style = "-fx-font-size: 30pt"
                                if darkmode then fill = White
                                else fill = Black
                                prefHeight = ((3.0/5)*windowHeight)/controler.gameState.board.rows.size
                                prefWidth = (2.0/3)*windowWidth
                            },
                            new Text {
                                textAlignment = TextAlignment.Center
                                text = controler.gameState.board.rows(i-1).cards.mkString(", ")
                                style = "-fx-font-size: 30pt"
                                if darkmode then fill = White
                                else fill = Black
                                prefHeight = ((3.0/5)*windowHeight)/controler.gameState.board.rows.size
                                prefWidth = (2.0/3)*windowWidth
                            }
                        )
                    }
                ).toList
                prefHeight = (3.0/5)*windowHeight
                prefWidth = (2.0/3)*windowWidth
            }
        }

        def undoRedo(darkmode: Boolean, windowHeight: Double, windowWidth: Double): VBox = {
            new VBox {
                prefHeight = (3.0/5)*windowHeight
                prefWidth = (1.0/3)*windowWidth
                border = new Border(new BorderStroke(if darkmode then White else Black, BorderStrokeStyle.Solid, CornerRadii.Empty, BorderWidths.Default))
                alignment = Pos.Center
                children = Seq(
                    new Button {
                        alignment = Pos.Center
                        prefWidth = (1.0/5)*windowWidth
                        text = "Undo"
                        style = "-fx-font-size: 30pt"
                        onAction = (event) => {
                            print("Undo\n")
                        }
                    },
                    new Button {
                        alignment = Pos.Center
                        prefWidth = (1.0/5)*windowWidth
                        text = "Redo"
                        style = "-fx-font-size: 30pt"
                        onAction = (event) => {
                            print("Redo\n")
                        }
                    },
                    new Button {
                        alignment = Pos.Center
                        prefWidth = (1.0/5)*windowWidth
                        text = "Mode"
                        style = "-fx-font-size: 30pt"
                        onAction = (event) =>
                            if darkmode then
                                stage = mystage(darkmode = false)
                            else
                                stage = mystage(darkmode = true)
                    }
                )
            }
        }

        def selectedCards(darkmode: Boolean, windowHeight: Double, windowWidth: Double): VBox = {
            new VBox {
                prefHeight = (3.0/5)*windowHeight
                prefWidth = (1.0/3)*windowWidth
                border = new Border(new BorderStroke(if darkmode then White else Black, BorderStrokeStyle.Solid, CornerRadii.Empty, BorderWidths.Default))
                alignment = Pos.Center
                children = (for (i <- controler.gameState.board.playedCards) yield
                        new Text {
                            alignment = Pos.Center
                            textAlignment = TextAlignment.Center
                            text = i.toString
                            style = "-fx-font-size: 30pt"
                            if darkmode then fill = White
                            else fill = Black
                            prefWidth = (1.0/3)*windowWidth
                            prefHeight = ((1.0/3)*windowHeight)/controler.gameState.players.size
                        }
                ).toList
            }
        }
        
        def player(darkmode: Boolean, windowHeight: Double, windowWidth: Double, player: Player): HBox = {
            new HBox {
                border = new Border(new BorderStroke(if darkmode then White else Black, BorderStrokeStyle.Solid, CornerRadii.Empty, BorderWidths.Default))
                alignment = Pos.Center
                children = Seq(
                    new Text {
                        alignment = Pos.Center
                        textAlignment = TextAlignment.Left
                        text = player.name
                        style = "-fx-font-size: 30pt"
                        if darkmode then fill = White
                        else fill = Black
                        prefWidth = windowWidth/3
                    },
                    new HBox {
                        alignment = Pos.Center
                        children = (for (i <- 0 to player.cards.length - 1) yield
                            new Button {
                                alignment = Pos.Center
                                text = player.cards(i).toString
                                style = "-fx-font-size: 30pt"
                                onAction = (event) => {
                                    print(s"Play Card ${player.cards(i)}\n")
                                }
                            }
                        ).toList
                    },
                    new Text {
                        alignment = Pos.Center
                        textAlignment = TextAlignment.Right
                        text = "Ochsen: " + controler.gameState.players(0).ochsen
                        style = "-fx-font-size: 30pt"
                        if darkmode then fill = White
                        else fill = Black
                        prefWidth = windowWidth/3
                    }
                )
                prefHeight = (2.0/5)*windowHeight
                prefWidth = windowWidth
            }
        }
    }

    

    override def end: Unit = ???

    override def run: Unit = controler.run(playCards, input, WhichRowTake, output)

    def input(): String = {
        " "
    }

    def output(s: String): Unit = {
        new Alert(AlertType.Information) {
            initOwner(Play.stage)
        }.showAndWait()
    }

    override def WhichRowTake(name: String, read: () => String): Int = ???

    override def playCards(players: Player, read: () => String): (Int, Player) = ???


    override def update(e: Event): Unit = {
        e match {
            case Event.Undo =>
                GUIState = Event.Undo
                Play.main(Array())
            case Event.PlayRound =>
                GUIState = Event.PlayRound
                run
            case Event.RoundFinished =>
                GUIState = Event.RoundFinished
                Play.main(Array())
            case Event.CardsSelected =>
                GUIState = Event.CardsSelected
                Play.main(Array())
            case Event.End =>
                GUIState = Event.End
                end
            case Event.GameStart =>
                GUIState = Event.GameStart
                Play.main(Array())
        }
    }
}