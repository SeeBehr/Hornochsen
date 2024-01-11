package de.htwg.se.hornochsen.aview

import de.htwg.se.hornochsen.{util, model, controler}
import util.Event
import model.InterfacePlayer
import controler.InterfaceControler

import scalafx.application.{JFXApp3, Platform}
import scalafx.stage.Screen
import scalafx.scene.{paint, Scene, layout, text, control, shape, image, media}
import paint._
import Color._
import layout._
import control.{Dialog, DialogPane, ButtonType}
import text.{Text, TextAlignment}
import control.{Button, TextField, Alert}
import image.{Image, ImageView}
import media.{MediaPlayer, MediaView, Media}
import scalafx.geometry.{Insets, Pos}
import scalafx.Includes._
import scalafx.event.{EventHandler, ActionEvent}
import scala.annotation.meta.setter
import scalafx.geometry.Bounds
import java.io.File
import scalafx.stage.Popup
import scalafx.util.Duration


class GUI(controler: InterfaceControler) extends UI with JFXApp3{
    val windowWidth: Double = 720
    val windowHeight: Double = 640
    var darkmode: Boolean = true
    var state: String = "StatePlayCard"
    var rows: VBox = new VBox
    var ops: VBox = new VBox
    var active: HBox = new HBox
    var dark: Color = new Color(50,50,50)
    var bright: Color = new Color(140,140,140)
    var player: Vector[String] = Vector.empty;
    
    override def start(): Unit = {
        stage = InitStage(500,800)
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

    def mediaPlayer: Popup = {
        val mediap = new MediaPlayer(
                new Media(new File("src/main/resources/video/tutorial.mp4").toURI().toString())
            )
            mediap.setAutoPlay(true)
            mediap.volume = 0.1
        new Popup {
            content.add(
                new VBox {
                    children = Seq(
                        new HBox {
                            alignment = Pos.TOP_RIGHT
                            background = new Background(fills = Seq(new BackgroundFill(Black, CornerRadii.Empty, Insets.Empty)), images = Seq.empty)
                            children = Seq(
                                new Button {
                                    text = "-5"
                                    style = "-fx-font-size: 20pt; -fx-background-color: black; -fx-font-color: white; -fx-font-weight: bold"
                                    minWidth = 30
                                    minHeight = 30
                                    alignment = Pos.Center
                                    onMouseClicked = (event) => {
                                        mediap.seek(mediap.getCurrentTime().subtract(Duration.apply(5*Math.pow(10,3))))
                                    }
                                }
                                ,new Button {
                                    text = "+5"
                                    style = "-fx-font-size: 20pt; -fx-background-color: black; -fx-font-color: white; -fx-font-weight: bold"
                                    minWidth = 30
                                    minHeight = 30
                                    alignment = Pos.Center
                                    onMouseClicked = (event) => {
                                        mediap.seek(mediap.getCurrentTime().add(Duration.apply(5*Math.pow(10,3))))
                                    }
                                }
                                ,new Button {
                                    text = "X"
                                    style = "-fx-font-size: 20pt; -fx-background-color: DarkRed; -fx-font-color: white; -fx-font-weight: bold"
                                    minWidth = 30
                                    minHeight = 30
                                    alignment = Pos.Center
                                    onMouseClicked = (event) => {
                                        mediap.stop()
                                        hide()
                                    }
                                })
                        }
                        ,new MediaView(mediap) {
                            fitWidth = 800
                            fitHeight = 500
                        }
                    )
                }
            )
        }
    }

    def InitStage(windowHeight: Double, windowWidth: Double): JFXApp3.PrimaryStage = {
        new JFXApp3.PrimaryStage {
            onCloseRequest = (event) => {
                System.exit(0)
            }
            title = "Hornochsen"
            scene = new Scene {
                fill = dark
                val nameField = new TextField {
                    promptText = "Playername"
                    style = "-fx-font-size: 20pt"
                    minWidth = 1/6 * windowWidth
                    minHeight = 1/7 * windowHeight
                    alignment = Pos.Center
                }
                content = new VBox {
                    children = Seq(
                        new Text {
                            text = "Hornochsen"
                            style = "-fx-font-size: 40pt"
                            fill = Red
                            alignment = Pos.Center
                        }
                        ,new HBox {
                            minHeight = 30
                        }
                        ,new HBox {
                            children = Seq(
                                new HBox {
                                    minWidth = 30
                                }
                                ,new Text {
                                    text = "Wilkommen bei Hornochsen\nFür eine kurze Einführung klicke auf den Button"
                                    style = "-fx-font-size: 20pt"
                                    fill = bright
                                    alignment = Pos.CenterLeft
                                }
                                ,new HBox {
                                    minWidth = 30
                                }
                                ,new Button {
                                    text = "Tutorial"
                                    style = "-fx-font-size: 20pt"
                                    minWidth = 1/6 * windowWidth
                                    minHeight = 1/6 * windowHeight
                                    alignment = Pos.CenterRight
                                    onMouseClicked = (event) => {
                                        mediaPlayer.show(stage)
                                    }
                                }
                            )
                        }
                        ,new HBox {
                            minHeight = 30
                        }
                        ,new Text {
                            text = "Es müssen 2-6 Spieler spielen"
                            style = "-fx-font-size: 20pt"
                            fill = bright
                            alignment = Pos.Center
                        }
                        ,new HBox {
                            minHeight = 1/3 * windowHeight
                            minWidth = windowWidth
                            alignment = Pos.CenterRight
                            children = Seq(
                                new Text {
                                    text = "Playername"
                                    style = "-fx-font-size: 20pt"
                                    fill = bright
                                }
                                ,new HBox {
                                    minWidth = 10
                                }
                                ,nameField
                                ,new HBox {
                                    minWidth = 10
                                }
                                ,new Button {
                                    text = "Add"
                                    style = "-fx-font-size: 20pt"
                                    minWidth = 1/6 * windowWidth
                                    minHeight = 1/6 * windowHeight
                                    alignment = Pos.Center
                                    onMouseClicked = (event) => {
                                        player = player :+ nameField.text.value
                                        nameField.clear()
                                    }
                                }
                                ,new HBox {
                                    minWidth = 10
                                }
                                ,new Button {
                                    text = "Start"
                                    style = "-fx-font-size: 20pt"
                                    minWidth = 1/6 * windowWidth
                                    minHeight = 1/6 * windowHeight
                                    alignment = Pos.Center
                                    onMouseClicked = (event) => {
                                        if player.length > 1 then
                                            print("Start Game\n")
                                            controler.start(player)
                                    }
                                }
                                ,new HBox {
                                    minWidth = 30
                                }
                                ,new Button {
                                    text = "Load"
                                    style = "-fx-font-size: 20pt"
                                    minWidth = 1/6 * windowWidth
                                    minHeight = 1/6 * windowHeight
                                    alignment = Pos.Center
                                    onMouseClicked = (event) => {
                                        controler.load
                                    }
                                }
                                ,new HBox {
                                    minWidth = 30
                                }
                            )
                        }
                        ,new HBox {
                            minHeight = 30
                        }
                    )
                }
                resizable = false
            }
        }
    }

    def buffer(l:Int, r:Int, o:Int, u:Int): HBox ={
        new HBox {
            minWidth = l
            maxWidth = r
            minHeight = o
            maxHeight = u
        }
    }

    def MainStage(windowHeight: Double = windowHeight,
    windowWidth: Double = windowWidth): JFXApp3.PrimaryStage = {
        new JFXApp3.PrimaryStage {
            onCloseRequest = (event) => {
                System.exit(0)
            }
            title = "Hornochsen"
            scene = new Scene {
                if darkmode then fill = dark
                else fill = bright
                content = new VBox {
                    children = Seq(
                        buffer(10,10,10,10)
                        ,new HBox {
                            children = Seq(
                                buffer(10,10,10,10)
                                ,rows
                                ,ops
                                ,buffer(10,10,10,10)
                            )
                        },
                        buffer(10,10,10,10)
                        ,active
                    )
                }
                resizable = false
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
                buffer(10,10,10,10)
                ,new Text {
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
                ,buffer(10,10,10,10)
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
        stage = new JFXApp3.PrimaryStage() {
            onCloseRequest = (event) => {
                System.exit(0)
            }
            title = "Hornochsen"
            scene = new Scene {
                fill = dark
                content = new VBox {
                    children = Seq(
                        new Text {
                            text = "Game Over"
                            style = "-fx-font-size: 40pt"
                            fill = Red
                            alignment = Pos.Center
                        }
                        ,buffer(10,10,10,10)
                        ,new HBox {
                            children = Seq(
                                buffer(10,10,10,10)
                                ,new Text {
                                    text = "Player: " + controler.gameState.playerActive.name + " hat gewonnen"
                                    style = "-fx-font-size: 20pt"
                                    fill = bright
                                    alignment = Pos.CenterLeft
                                }
                                ,buffer(10,10,10,10)
                                ,new Button {
                                    text = "Restart"
                                    style = "-fx-font-size: 20pt"
                                    minWidth = 1/6 * windowWidth
                                    minHeight = 1/6 * windowHeight
                                    alignment = Pos.CenterRight
                                    onMouseClicked = (event) => {
                                        controler.restart
                                    }
                                }
                                ,buffer(10,10,10,10)
                            )
                        }
                        ,
                        buffer(10,10,10,10)
                    )
                }
                resizable = false
            }
        }
    }
}
