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
import scalafx.scene.input.KeyCode.I
import javafx.scene.image.{Image, ImageView}


class GUI(controler: InterfaceControler) extends UI with JFXApp3{
    val windowWidth: Double = 720
    val windowHeight: Double = 640
    var darkmode: Boolean = true
    var state: String = "StatePlayCard"
    var rows: VBox = new VBox
    var ops: VBox = new VBox
    var active: HBox = new HBox
    var dark: Color = new Color(20,20,20)
    var bright: Color = new Color(100,100,100)
    
    override def start(): Unit = {
        state = "StatePlayCard"
        rows = reihen(windowHeight, windowWidth)
        ops = undoRedo(windowHeight, windowWidth)
        active = player(windowHeight, windowWidth, controler.gameState.playerActive)
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
                    active = player(windowHeight, windowWidth, controler.gameState.playerActive)
                case Event.nextPlayer =>
                    active = player(windowHeight, windowWidth, controler.gameState.playerActive)
                case Event.Undo =>
                    state = "StatePlayCard"
                    active = player(windowHeight, windowWidth, controler.gameState.playerActive)
                case Event.Redo =>
                    state = "StatePlayCard"
                    active = player(windowHeight, windowWidth, controler.gameState.playerActive)
                case Event.End =>
                    end
                rows = reihen(windowHeight, windowWidth)
                ops = undoRedo(windowHeight, windowWidth)
                stage = MainStage(windowHeight, windowWidth)
            )
    }

        def MainStage(windowHeight: Double = windowHeight,
        windowWidth: Double = windowWidth): JFXApp3.PrimaryStage = {
            new JFXApp3.PrimaryStage {
                title = "Hornochsen"
                scene = new Scene {
                    if darkmode then fill = dark
                    else fill = bright
                    content = new VBox {
                        children = Seq(
                            new HBox {
                                children = Seq(
                                    rows,
                                    ops
                                )
                            },
                            active
                            )
                    }
                }
            }
        }

        def reihen(windowHeight: Double, windowWidth: Double): VBox = {
            val playingCard = new ImageView(new Image("file:images/playingCards.png"))
            new VBox {
                prefHeight = (3.0/5)*windowHeight
                prefWidth = (2.0/3)*windowWidth
                children = (for (i <- 1 to controler.gameState.board.rows.size) yield
                    new HBox {
                        border = new Border(new BorderStroke(if darkmode then bright else dark, BorderStrokeStyle.Solid, CornerRadii.Empty, BorderWidths.Default))
                        alignment = Pos.CenterLeft
                        children = Seq(
                            new Text {
                                textAlignment = TextAlignment.Center
                                text = s"Row $i: "
                                style = "-fx-font-size: 20pt"
                                if darkmode then fill = White
                                else fill = dark
                                prefHeight = ((3.0/5)*windowHeight)/controler.gameState.board.rows.size
                                prefWidth = (2.0/3)*windowWidth
                            },
                            new Text {
                                textAlignment = TextAlignment.Center
                                text = controler.gameState.board.rows(i-1).cards.mkString(", ")
                                style = "-fx-font-size: 20pt"
                                if darkmode then fill = White
                                else fill = dark
                                prefHeight = ((3.0/5)*windowHeight)/controler.gameState.board.rows.size
                                prefWidth = (2.0/3)*windowWidth
                            }
                        )
                    }
                ).toList
            }
        }

        def undoRedo(windowHeight: Double, windowWidth: Double): VBox = {
            new VBox {
                prefHeight = (3.0/5)*windowHeight
                prefWidth = (1.0/3)*windowWidth
                border = new Border(new BorderStroke(if darkmode then bright else dark, BorderStrokeStyle.Solid, CornerRadii.Empty, BorderWidths.Default))
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
                    ,new Button {
                        alignment = Pos.Center
                        prefWidth = (1.5/5)*windowWidth
                        text = "Mode"
                        style = "-fx-font-size: 20pt"
                        onMouseClicked = (event) =>
                            darkmode = !darkmode
                            if darkmode then
                                bright = new Color(100,100,100)
                            else
                                bright = new Color(200,200,200)
                            rows = reihen(windowHeight, windowWidth)
                            ops = undoRedo(windowHeight, windowWidth)
                            active = player(windowHeight, windowWidth, controler.gameState.playerActive)
                            stage = MainStage()
                    }
                )
            }
        }

        def player(windowHeight: Double, windowWidth: Double, player: InterfacePlayer): HBox = {
            new HBox {
                border = new Border(new BorderStroke(if darkmode then bright else dark, BorderStrokeStyle.Solid, CornerRadii.Empty, BorderWidths.Default))
                alignment = Pos.Center
                children = Seq(
                    new Text {
                        alignment = Pos.Center
                        text = player.name
                        style = "-fx-font-size: 20pt"
                        if darkmode then fill = White
                        else fill = dark
                        prefWidth = (1.0/3)*windowWidth
                    },
                    new HBox {
                        alignment = Pos.Center
                        children = playerCards(windowHeight, windowWidth, player)
                    },
                    new Text {
                        alignment = Pos.Center
                        text = "Ochsen: " + player.ochsen
                        style = "-fx-font-size: 20pt"
                        if darkmode then fill = White
                        else fill = dark
                        prefWidth = (1.0/3)*windowWidth
                    }
                )
                prefHeight = (2.0/5)*windowHeight
                prefWidth = windowWidth
            }
        }

        def playerCards(windowHeight: Double, windowWidth: Double, player: InterfacePlayer): Pane = {
            val playingCard = new ImageView(new Image("file:images/playingCards.png"))
            if player.getCards.length < 8 then
                new HBox {
                    alignment = Pos.Center
                    children = (for (i <- 0 to player.getCards.length - 1) yield
                        new Button {
                            prefHeight = ((2.0/5)*windowHeight)/player.getCards.length
                            prefWidth = (1.0/3)*windowWidth/player.getCards.length
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
                                    prefHeight = ((2.0/5)*windowHeight)/4
                                    prefWidth = (1.0/3)*windowWidth/4
                                    alignment = Pos.Center
                                    graphic = playingCard
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
                                    prefHeight = ((2.0/5)*windowHeight)/4
                                    prefWidth = (1.0/3)*windowWidth/4
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
