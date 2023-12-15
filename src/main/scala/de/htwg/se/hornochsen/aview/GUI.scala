package de.htwg.se.hornochsen.aview

import de.htwg.se.hornochsen.{util, model, controler}
import util.Event
import model.InterfacePlayer
import controler.InterfaceControler

import scalafx.application.{JFXApp3, Platform}
import scalafx.stage.Screen
import scalafx.scene.{paint, Scene, layout, text, control, shape}
import paint._
import Color._
import layout._
import text.{Text, TextAlignment}
import control.{Button, TextField, Alert}
import Alert.AlertType
import shape.Box
import scalafx.geometry.{Insets, Pos}
import scalafx.Includes._
import scalafx.event.{EventHandler, ActionEvent}
import de.htwg.se.hornochsen.controler.BaseControler.Controler
import de.htwg.se.hornochsen.model.BaseModel.Player


case class GUI(controler: Controler) extends UI with JFXApp3{
    val width: Double = 720
    val height: Double = 640
    var darkmode: Boolean = true
    var state: String = "StatePlayCard"
    var rows: VBox = new VBox
    var ops: VBox = new VBox
    var change: HBox = new HBox
    
    override def start(): Unit = {
        state = "StatePlayCard"
        rows = reihen(darkmode, height, width)
        ops = undoRedo(darkmode, height, width)
        change = player(darkmode, height, width, controler.gameState.playerActive)
        stage = MainStage()
    }

    override def update(e:Event, name:String="0") = {
        if e == Event.Start then
            state = "StatePlayCard"
        else
            Platform.runLater(() =>
                e match
                case Event.Start =>
                case Event.First =>
                    change = player(darkmode, height, width, controler.gameState.playerActive)
                case Event.nextPlayer =>
                    change = player(darkmode, height, width, controler.gameState.playerActive)
                case Event.TakeRow =>
                    state = "StateTakeRow"
                    change = selectedCards(darkmode, height, width)
                case Event.Undo =>
                    name match
                    case "StatePlayCard" =>
                        state = "StatePlayCard"
                        change = player(darkmode, height, width, controler.gameState.playerActive)
                    case "StateTakeRow" =>
                        state = "StateTakeRow"
                        change = selectedCards(darkmode, height, width)
                case Event.Redo =>
                    name match
                    case "StatePlayCard" =>
                        state = "StatePlayCard"
                        change = player(darkmode, height, width, controler.gameState.playerActive)
                    case "StateTakeRow" =>
                        state = "StateTakeRow"
                        change = selectedCards(darkmode, height, width)
                case Event.End =>
                    end
                rows = reihen(darkmode, height, width)
                ops = undoRedo(darkmode, height, width)
                stage = MainStage(darkmode, height, width)
            )
    }

        def MainStage(darkmode: Boolean = true,
        windowHeight: Double = height,
        windowWidth: Double = width): JFXApp3.PrimaryStage = {
            new JFXApp3.PrimaryStage {
                title = "Hornochsen"
                scene = new Scene {
                    if darkmode then fill = Black
                    else fill = White
                    content = new VBox {
                        children = Seq(
                            new HBox {
                                children = Seq(
                                    rows,
                                    ops
                                )
                            },
                            change
                            )
                    }
                }
            }
        }

        def reihen(darkmode: Boolean, windowHeight: Double, windowWidth: Double): VBox = {
            new VBox {
                prefHeight = (3.0/5)*windowHeight
                prefWidth = (2.0/3)*windowWidth
                children = (for (i <- 1 to controler.gameState.board.rows.size) yield
                    new HBox {
                        border = new Border(new BorderStroke(if darkmode then White else Black, BorderStrokeStyle.Solid, CornerRadii.Empty, BorderWidths.Default))
                        alignment = Pos.CenterLeft
                        children = Seq(
                            new Button {
                                text = "Take"
                                style = "-fx-font-size: 10pt"
                                val row = i
                                onMouseClicked = (event) => {
                                    if state == "StateTakeRow" then
                                        controler.takeRow(controler.gameState.playerActive, row+1, "StateTakeRow")
                                }
                            },
                            new Text {
                                textAlignment = TextAlignment.Center
                                text = s"Row $i: "
                                style = "-fx-font-size: 20pt"
                                if darkmode then fill = White
                                else fill = Black
                                prefHeight = ((3.0/5)*windowHeight)/controler.gameState.board.rows.size
                                prefWidth = (2.0/3)*windowWidth
                            },
                            new Text {
                                textAlignment = TextAlignment.Center
                                text = controler.gameState.board.rows(i-1).cards.mkString(", ")
                                style = "-fx-font-size: 20pt"
                                if darkmode then fill = White
                                else fill = Black
                                prefHeight = ((3.0/5)*windowHeight)/controler.gameState.board.rows.size
                                prefWidth = (2.0/3)*windowWidth
                            }
                        )
                    }
                ).toList
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
                        prefWidth = (1.5/5)*windowWidth
                        text = "Undo"
                        style = "-fx-font-size: 20pt"
                        onMouseClicked = (event) => {
                            if state == "StatePlayCard" then
                                controler.doOp("undo", "StatePlayCard")
                        }
                    },
                    new Button {
                        alignment = Pos.Center
                        prefWidth = (1.5/5)*windowWidth
                        text = "Redo"
                        style = "-fx-font-size: 20pt"
                        onMouseClicked = (event) => {
                            if state == "StatePlayCard" then
                                controler.doOp("redo", "StatePlayCard")
                        }
                    }
                    /*,new Button {
                        alignment = Pos.Center
                        prefWidth = (1.5/5)*windowWidth
                        text = "Mode"
                        style = "-fx-font-size: 20pt"
                        onMouseClicked = (event) =>
                            if darkmode then
                                stage = MainStage(darkmode = false)
                            else
                                stage = MainStage(darkmode = true)
                    }*/
                )
            }
        }

        def selectedCards(darkmode: Boolean, windowHeight: Double, windowWidth: Double): HBox = {
            new HBox {
                prefHeight = (3.0/5)*windowHeight
                prefWidth = (1.0/3)*windowWidth
                border = new Border(new BorderStroke(if darkmode then White else Black, BorderStrokeStyle.Solid, CornerRadii.Empty, BorderWidths.Default))
                alignment = Pos.Center
                children = (for (i <- controler.gameState.board.playedCards) yield
                        new Text {
                            alignment = Pos.Center
                            textAlignment = TextAlignment.Center
                            text = i._1.toString + i._2.name
                            style = "-fx-font-size: 20pt"
                            if darkmode then fill = White
                            else fill = Black
                            prefWidth = (1.0/3)*windowWidth
                            prefHeight = ((1.0/3)*windowHeight)/controler.gameState.players.size
                        }
                ).toList
            }
        }

        def player(darkmode: Boolean, windowHeight: Double, windowWidth: Double, player: InterfacePlayer): HBox = {
            new HBox {
                border = new Border(new BorderStroke(if darkmode then White else Black, BorderStrokeStyle.Solid, CornerRadii.Empty, BorderWidths.Default))
                alignment = Pos.Center
                children = Seq(
                    new Text {
                        alignment = Pos.Center
                        textAlignment = TextAlignment.Left
                        text = player.name
                        style = "-fx-font-size: 20pt"
                        if darkmode then fill = White
                        else fill = Black
                        prefWidth = (1.0/3)*windowWidth
                    },
                    new HBox {
                        alignment = Pos.Center
                        children = playerCards(darkmode, windowHeight, windowWidth, player)
                    },
                    new Text {
                        alignment = Pos.Center
                        textAlignment = TextAlignment.Right
                        text = "Ochsen: " + player.ochsen
                        style = "-fx-font-size: 20pt"
                        if darkmode then fill = White
                        else fill = Black
                        prefWidth = (1.0/3)*windowWidth
                    }
                )
                prefHeight = (2.0/5)*windowHeight
                prefWidth = windowWidth
            }
        }

        def playerCards(darkmode: Boolean, windowHeight: Double, windowWidth: Double, player: InterfacePlayer): Pane = {
            if player.getCards.length < 8 then
                new HBox {
                    alignment = Pos.Center
                    children = (for (i <- 0 to player.getCards.length - 1) yield
                        new Button {
                            alignment = Pos.Center
                            val card = player.getCards(i)
                            val p = player
                            style = "-fx-font-size: 10pt"
                            onMouseClicked = (event) => {
                                if state == "StatePlayCard" then
                                    controler.playCard(p, card, "StatePlayCard")
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
                            children = (for (i <- 0 to player.getCards.length/2 - 1) yield
                                new Button {
                                    alignment = Pos.Center
                                    text = player.getCards(i).toString
                                    val card = player.getCards(i)
                                    val p = player
                                    style = "-fx-font-size: 10pt"
                                    onMouseClicked = (event) => {
                                        controler.playCard(p, card, "StatePlayCard")
                                    }
                                }
                            )
                        },
                        new HBox {
                            alignment = Pos.Center
                            children = (for (i <- player.getCards.length/2 to player.getCards.length - 1) yield
                                new Button {
                                    alignment = Pos.Center
                                    text = player.getCards(i).toString
                                    style = "-fx-font-size: 10pt"
                                    val card = player.getCards(i)
                                    val p = player
                                    onMouseClicked = (event) => {
                                        if state == "StatePlayCard" then
                                            controler.playCard(p, card, "StatePlayCard")
                                    }
                                }
                            )
                        }
                    )
                }
                
        }

    def run: Unit = {
        stage = MainStage()
    }

    def end: Unit = {
    }
}
