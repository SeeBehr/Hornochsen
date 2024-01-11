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
import scalafx.scene.image.{Image, ImageView}
import scala.annotation.meta.setter


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
    var player: Vector[String] = Vector.empty;
    
    override def start(): Unit = {
        stage = InitStage(50,100)
    }

    override def update(e:Event, name:String="0") = {
        if e == Event.Start then
            state = "StatePlayCard"
        else
            Platform.runLater(() =>
                e match
                case Event.Start =>
                    stage = InitStage(50,100)
                case Event.First =>
                    active = player(windowHeight, windowWidth, controler.gameState.playerActive)
                    rows = reihen(windowHeight, windowWidth)
                    ops = undoRedo(windowHeight, windowWidth)
                    stage = MainStage(windowHeight, windowWidth)
                case Event.nextPlayer =>
                    active = player(windowHeight, windowWidth, controler.gameState.playerActive)
                    rows = reihen(windowHeight, windowWidth)
                    ops = undoRedo(windowHeight, windowWidth)
                    stage = MainStage(windowHeight, windowWidth)
                case Event.Undo =>
                    state = "StatePlayCard"
                    active = player(windowHeight, windowWidth, controler.gameState.playerActive)
                    rows = reihen(windowHeight, windowWidth)
                    ops = undoRedo(windowHeight, windowWidth)
                    stage = MainStage(windowHeight, windowWidth)
                case Event.Redo =>
                    state = "StatePlayCard"
                    active = player(windowHeight, windowWidth, controler.gameState.playerActive)
                    rows = reihen(windowHeight, windowWidth)
                    ops = undoRedo(windowHeight, windowWidth)
                    stage = MainStage(windowHeight, windowWidth)
                case Event.End =>
                    end
            )
    }

    def InitStage(windowHeight: Double, windowWidth: Double): JFXApp3.PrimaryStage = {
        new JFXApp3.PrimaryStage {
            title = "Hornochsen"
            scene = new Scene {
                fill = dark
                val nameField = new TextField {
                    promptText = "Playername"
                    style = "-fx-font-size: 20pt"
                    minWidth = 1/2 * windowWidth
                    minHeight = 3/4 * windowHeight
                    alignment = Pos.Center
                }
                content = new HBox {
                    minHeight = windowHeight
                    minWidth = windowWidth
                    children = Seq(
                        nameField
                        ,new Button {
                            text = "Add"
                            style = "-fx-font-size: 20pt"
                            minWidth = 1/4 * windowWidth
                            minHeight = 3/4 * windowHeight
                            alignment = Pos.Center
                            onMouseClicked = (event) => {
                                player = player :+ nameField.text.value
                                nameField.clear()
                            }
                        }
                        ,new Button {
                            text = "Start"
                            style = "-fx-font-size: 20pt"
                            minWidth = 1/4 * windowWidth
                            minHeight = 3/4 * windowHeight
                            alignment = Pos.Center
                            onMouseClicked = (event) => {
                                if player.length > 1 then
                                    print("Start Game\n")
                                    controler.start(player)
                            }
                        }
                    )
                }
            }
        }
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
        val playingcardImage = new Image("file:images/playingCard.png")
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
                        new HBox {
                            alignment = Pos.Center
                            children = (
                                for (j <- 0 to controler.gameState.board.rows(i-1).filled-1) yield
                                    new Button {
                                        prefHeight = 80
                                        prefWidth = 40
                                        alignment = Pos.Center
                                        style="-fx-background-image: url('images/playingCard.png'); -fx-background-size: cover; -fx-font-size: 10pt"
                                        text=controler.gameState.board.rows(i-1).cards(j).toString
                                    }
                            ).toList
                        }
                    ).toList
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
                    style = "-fx-background-image: url('images/undoButton.png'); -fx-background-color: transparent; -fx-background-size: cover; -fx-font-size: 20pt"
                    prefHeight = ((3.0/5)*windowHeight)/5
                    prefWidth = ((3.0/5)*windowHeight)/5
                    onMouseClicked = (event) => {
                        if state == "StatePlayCard" then
                            controler.doOp("undo", "StatePlayCard")
                    }
                },
                new Button {
                    alignment = Pos.Center
                    style = "-fx-background-image: url('images/redoButton.png'); -fx-background-color: transparent; -fx-background-size: cover; -fx-font-size: 20pt"
                    prefHeight = ((3.0/5)*windowHeight)/5
                    prefWidth = ((3.0/5)*windowHeight)/5
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
                ,new Button {
                    alignment = Pos.Center
                    prefWidth = (1.5/5)*windowWidth
                    text = "Save"
                    style = "-fx-font-size: 20pt"
                    onMouseClicked = (event) => {
                        if state == "StatePlayCard" then
                            controler.save
                    }
                }
                ,new Button {
                    alignment = Pos.Center
                    prefWidth = (1.5/5)*windowWidth
                    text = "Load"
                    style = "-fx-font-size: 20pt"
                    onMouseClicked = (event) => {
                        if state == "StatePlayCard" then
                            controler.load
                    }
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
                    prefWidth = (1.0/4)*windowWidth
                },
                new HBox {
                    alignment = Pos.Center
                    children = playerCards(windowHeight, windowWidth, player)
                    prefWidth = (1.0/2)*windowWidth
                },
                new Text {
                    alignment = Pos.Center
                    text = "Ochsen: " + player.ochsen
                    style = "-fx-font-size: 20pt"
                    if darkmode then fill = White
                    else fill = dark
                    prefWidth = (1.0/4)*windowWidth
                }
            )
            prefHeight = (2.0/5)*windowHeight
            prefWidth = windowWidth
        }
    }

    def playerCards(windowHeight: Double, windowWidth: Double, player: InterfacePlayer): Pane = {
        if player.getCards.length < 8 then
            new HBox {
                alignment = Pos.Center
                children = (for (i <- 0 to player.getCards.length - 1) yield
                    new Button {
                        prefHeight = 80
                        prefWidth = 40
                        alignment = Pos.Center
                        style="-fx-background-image: url('images/playingCard.png'); -fx-background-size: cover; -fx-font-size: 10pt"
                        val card = player.getCards(i)
                        val p = player
                        text = card.toString
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
                                prefHeight = 80
                                prefWidth = 40
                                alignment = Pos.Center
                                style="-fx-background-image: url('images/playingCard.png'); -fx-background-size: cover; -fx-font-size: 10pt"
                                text = player.getCards(i).toString
                                val card = player.getCards(i)
                                val p = player
                                text = card.toString
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
                                prefHeight = 80
                                prefWidth = 40
                                alignment = Pos.Center
                                style="-fx-background-image: url('images/playingCard.png'); -fx-background-size: cover; -fx-font-size: 10pt"
                                text = player.getCards(i).toString
                                val card = player.getCards(i)
                                val p = player
                                text = card.toString
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
        stage = new JFXApp3.PrimaryStage()
    }
}
