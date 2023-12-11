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
import javafx.fxml.FXML
import javafx.scene.image.ImageView
import scalafx.event.{EventHandler, ActionEvent}
import scalafx.scene.control.Alert.AlertType
import javafx.scene.control.Alert
import scalafx.scene.input.KeyCode.S
import java.awt.Taskbar.State
import scalafx.scene.shape.Box


class GUI(controler: Controler) extends UI {
    var state: String = "0"

    override def update(e:Event, name:String="0") = {
        e match
        case Event.Start =>
            state = "StatePlayCard"
            run
        case Event.nextPlayer =>
            run
        case Event.TakeRow =>
            state = "StateTakeRow"
            run
        case Event.Undo =>
            name match
            case "StatePlayCard" =>
                state = "StatePlayCard"
            case "StateTakeRow" =>
                state = "StateTakeRow"
            run
        case Event.Redo =>
            name match
            case "StatePlayCard" =>
                state = "StatePlayCard"
            case "StateTakeRow" =>
                state = "StateTakeRow"
            run
        case Event.End =>
            end
    }

    object MainWindow extends JFXApp3 {
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
                                    undoRedo(darkmode, windowHeight, windowWidth)
                                )
                            },
                            if state == "StatePlayCard" then
                                player(darkmode, windowHeight, windowWidth, controler.gameState.playerActive)
                            else
                                selectedCards(darkmode, windowHeight, windowWidth)
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
                            prefHeight = ((1.0/3)*windowHeight)/controler.gameState.players().size
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
                        children = playerCards(darkmode, windowHeight, windowWidth, player)
                    },
                    new Text {
                        alignment = Pos.Center
                        textAlignment = TextAlignment.Right
                        text = "Ochsen: " + player.ochsen
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

        def playerCards(darkmode: Boolean, windowHeight: Double, windowWidth: Double, player: Player): Pane = {
            println("PlayerCards")
            if player.cards.length < 8 then
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
                }
            else
                new VBox {
                    alignment = Pos.Center
                    children = Seq(
                        new HBox {
                            alignment = Pos.Center
                            children = (for (i <- 0 to player.cards.length/2 - 1) yield
                                new Button {
                                    alignment = Pos.Center
                                    text = player.cards(i).toString
                                    style = "-fx-font-size: 30pt"
                                    onAction = (event) => {
                                        print(s"Play Card ${player.cards(i)}\n")
                                    }
                                }
                            )
                        },
                        new HBox {
                            alignment = Pos.Center
                            children = (for (i <- player.cards.length/2 to player.cards.length - 1) yield
                                new Button {
                                    alignment = Pos.Center
                                    text = player.cards(i).toString
                                    style = "-fx-font-size: 30pt"
                                    onAction = (event) => {
                                        print(s"Play Card ${player.cards(i)}\n")
                                    }
                                }
                            )
                        }
                    )
                }
                
        }
    }

    object EndWindow extends JFXApp3 {
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
                            new Text {
                                textAlignment = TextAlignment.Center
                                text = "Game Over"
                                style = "-fx-font-size: 30pt"
                                if darkmode then fill = White
                                else fill = Black
                                prefHeight = (1.0/5) * windowHeight
                                prefWidth = windowWidth
                            },
                            new HBox {
                                children = (for (i <- controler.gameState.players().sortBy(p => p.ochsen).reverse) yield
                                    new Text {
                                        textAlignment = TextAlignment.Center
                                        text = i.name + ": " + i.ochsen
                                        style = "-fx-font-size: 30pt"
                                        if darkmode then fill = White
                                        else fill = Black
                                        prefHeight = (1.0/5) * windowHeight
                                        prefWidth = windowWidth
                                    }
                                ).toList
                            }
                        )
                    }
                }
            }
        }
    }

    def interpretLine(controler: Controler, input: String): Unit = ???

    def run: Unit = {
        MainWindow.main(Array())
    }

    def end: Unit = {
        EndWindow.main(Array())
    }
}
