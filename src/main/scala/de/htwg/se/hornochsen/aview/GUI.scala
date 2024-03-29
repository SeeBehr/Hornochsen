package de.htwg.se.hornochsen.aview

import de.htwg.se.hornochsen.controler.InterfaceControler
import de.htwg.se.hornochsen.model.InterfacePlayer
import de.htwg.se.hornochsen.util.Event
import de.htwg.se.hornochsen.{controler, model, util}

import scalafx.Includes._
import scalafx.application.{JFXApp3, Platform}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene._
import control.{Alert, Button, TextField}
import image.Image
import layout._
import media.{Media, MediaPlayer, MediaView}
import paint._
import paint.Color._
import text.{Text, TextAlignment}
import scalafx.stage.Popup
import scalafx.util.Duration

import java.io.File


class GUI(controler: InterfaceControler) extends UI with JFXApp3{
    val windowWidth: Double = 720
    val windowHeight: Double = 640
    var darkmode: Boolean = true
    var rows: VBox = new VBox
    var ops: VBox = new VBox
    var active: HBox = new HBox
    var dark: Color = new Color(50,50,50)
    var bright: Color = new Color(140,140,140)
    
    override def start(): Unit = {
        stage = InitStage(500,800)
    }

    override def update(e:Event) = {
        if e == Event.Start then
            println("Gui Start")
        else
            Platform.runLater(() =>
                e match
                case Event.Start =>
                    stage = InitStage(50,100)
                case Event.nextPlayer =>
                    /*
                     build up theupdated GUI
                     */
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
                new Media(if System.nanoTime()%10 > 6 then 
                    new File("src/main/resources/video/RickRoll.mp4").toURI().toString()
                else
                    new File("src/main/resources/video/tutorial.mp4").toURI().toString())
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
        var player: Vector[String] = Vector.empty;
        new JFXApp3.PrimaryStage {
            onCloseRequest = (event) => {
                System.exit(0)
            }
            title = "Hornochsen"
            scene = new Scene {
                fill = dark
                val nameField = new TextField {
                    promptText = "Playername"
                    style = f"-fx-font-size: 20pt; -fx-color: ${dark.toString.replace("[SFX]0x","#")}}"
                    minWidth = 1/7 * windowWidth
                    minHeight = 1/7 * windowHeight
                    alignment = Pos.Center
                }
                content = new VBox {
                    children = Seq(
                        new Text {
                            text = "Hornochsen"
                            style = "-fx-font-size: 40pt; -fx-font-weight: bold"
                            fill = Red
                            alignment = Pos.Center
                        }
                        ,buffer(30,30,30,30)
                        ,new HBox {
                            children = Seq(
                                buffer(30,30,30,30)
                                ,new Text {
                                    text = "Wilkommen bei Hornochsen\nFür eine kurze Einführung klicke auf den Button"
                                    style = "-fx-font-size: 20pt"
                                    fill = bright
                                    alignment = Pos.CenterLeft
                                }
                                ,buffer(30,30,30,30)
                                ,new Button {
                                    text = "Tutorial"
                                    style = "-fx-font-size: 20pt; -fx-background-color: transparent; -fx-text-fill: blue; -fx-font-weight: bold"
                                    minWidth = 1/6 * windowWidth
                                    minHeight = 1/6 * windowHeight
                                    alignment = Pos.CenterRight
                                    onMouseClicked = (event) => {
                                        mediaPlayer.show(stage)
                                    }
                                }
                            )
                        }
                        ,buffer(30,30,30,30)
                        ,new Text {
                            text = "Es kann mit 2-6 Spielern gespielt werden"
                            style = "-fx-font-size: 20pt"
                            fill = bright
                            alignment = Pos.Center
                        }
                        ,new HBox {
                            minHeight = 1/3 * windowHeight
                            minWidth = windowWidth
                            alignment = Pos.Center
                            children = Seq(
                                new Text {
                                    text = "Playername"
                                    style = "-fx-font-size: 20pt"
                                    fill = bright
                                }
                                ,buffer(10,10,10,10)
                                ,nameField
                                ,buffer(10,10,10,10)
                                ,new VBox{
                                    children = Seq(new Button {
                                        text = "Add"
                                        style = f"-fx-font-size: 10pt; -fx-background-color: transparent; -fx-font-weight: bold; -fx-text-fill: ${bright.toString.replace("[SFX]0x","#")}"
                                        minWidth = 2/7 * windowWidth
                                        minHeight = 2/7 * windowHeight
                                        alignment = Pos.Center
                                        onMouseClicked = (event) => {
                                            if nameField.text.value != "" then
                                                player = player :+ nameField.text.value
                                                nameField.clear()
                                        }
                                    }
                                    ,buffer(10,10,10,10)
                                    ,new Button {
                                        text = "Remove"
                                        style = f"-fx-font-size: 10pt; -fx-background-color: transparent; -fx-font-weight: bold; -fx-text-fill: ${bright.toString.replace("[SFX]0x","#")}}"
                                        minWidth = 1/7 * windowWidth
                                        minHeight = 1/7 * windowHeight
                                        alignment = Pos.Center
                                        onMouseClicked = (event) => {
                                            player = player.filterNot(p => p == nameField.text.value)
                                            nameField.clear()
                                        }
                                    })
                                }
                                ,buffer(10,10,10,10)
                                ,new Button {
                                    text = "Spielerliste"
                                    style = f"-fx-font-size: 10pt; -fx-background-color: transparent; -fx-font-weight: bold; -fx-text-fill: ${bright.toString.replace("[SFX]0x","#")}"
                                    minWidth = 1/7 * windowWidth
                                    minHeight = 1/7 * windowHeight
                                    alignment = Pos.Center
                                    onMouseClicked = (event) => {
                                        val alert = new Alert(Alert.AlertType.Information) {
                                            initOwner(stage)
                                            fill = dark
                                            title = "Spielerliste"
                                            headerText = "Spielerliste"
                                            contentText = player.mkString("\n")
                                        }
                                        alert.showAndWait()
                                    }
                                }
                                ,buffer(10,10,10,10)
                                ,new VBox {
                                    children = Seq(new Button {
                                        text = "Start"
                                        style = f"-fx-font-size: 10pt; -fx-background-color: transparent; -fx-font-weight: bold; -fx-text-fill: ${bright.toString.replace("[SFX]0x","#")}"
                                        minWidth = 1/7 * windowWidth
                                        minHeight = 1/7 * windowHeight
                                        alignment = Pos.Center
                                        onMouseClicked = (event) => {
                                            if player.length > 1 then
                                                val p = player
                                                player = Vector.empty
                                                print("Start Game\n")
                                                controler.start(p)
                                        }
                                    }
                                    ,buffer(10,10,10,10)
                                    ,new Button {
                                        text = "Load"
                                        style = f"-fx-font-size: 10pt; -fx-background-color: transparent; -fx-font-weight: bold; -fx-text-fill: ${bright.toString.replace("[SFX]0x","#")}"
                                        minWidth = 1/7 * windowWidth
                                        minHeight = 1/7 * windowHeight
                                        alignment = Pos.Center
                                        onMouseClicked = (event) => {
                                            controler.load
                                        }
                                    })
                                }
                                ,buffer(30,30,30,30)
                            )
                        }
                        ,buffer(30,30,30,30)
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
                        controler.doOp("undo")
                    }
                },
                buffer(5,5,5,5)
                ,new Button {
                    alignment = Pos.Center
                    style = "-fx-background-image: url('images/redoButton.png'); -fx-background-color: transparent; -fx-background-size: cover; -fx-font-size: 20pt"
                    prefHeight = ((3.0/5)*windowHeight)/5
                    prefWidth = ((3.0/5)*windowHeight)/5
                    onMouseClicked = (event) => {
                        controler.doOp("redo")
                    }
                }
                ,buffer(5,5,5,5)
                ,new Button {
                    alignment = Pos.Center
                    prefWidth = (1.5/5)*windowWidth
                    text = "Mode"
                    style = f"-fx-font-size: 20pt; -fx-background-color: transparent; -fx-color: ${if !darkmode then bright.toString.replace("[SFX]0x","#") else dark.toString.replace("[SFX]0x","#")}"
                    onMouseClicked = (event) =>
                        darkmode = !darkmode
                        if darkmode then
                            bright = new Color(140,140,140)
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
                    style = f"-fx-font-size: 20pt; -fx-background-color: transparent; -fx-color: ${if !darkmode then bright.toString.replace("[SFX]0x","#") else dark.toString.replace("[SFX]0x","#")}"
                    onMouseClicked = (event) => {
                        controler.save
                    }
                }
                ,new Button {
                    alignment = Pos.Center
                    prefWidth = (1.5/5)*windowWidth
                    text = "Load"
                    style = f"-fx-font-size: 20pt; -fx-background-color: transparent; -fx-color: ${if !darkmode then bright.toString.replace("[SFX]0x","#") else dark.toString.replace("[SFX]0x","#")}"
                    onMouseClicked = (event) => {
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
            prefHeight = (1.0/5)*windowHeight
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
                            controler.playCard(p, card)
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
                                    controler.playCard(p, card)
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
                                    controler.playCard(p, card)
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
        val order = controler.gameState.players.sortBy((player: InterfacePlayer) => player.ochsen)
        stage = new JFXApp3.PrimaryStage() {
            onCloseRequest = (event) => {
                System.exit(0)
            }
            title = "Hornochsen"
            scene = new Scene {
                fill = dark
                content = new HBox {
                    children = Seq(
                        buffer(20,20,20,20)
                        ,new VBox {
                            children = Seq(
                                buffer(10,10,10,10)
                                ,new VBox {
                                    children = Seq(
                                        buffer(10,10,10,10)
                                        ,new Text {
                                            text = order.head.name + " hat Gewonnen\n " + order.head.ochsen + " Ochsen"
                                            style = "-fx-font-size: 40pt; -fx-font-weight: bold; -fx-text-alignment: center"
                                            fill = Green
                                            alignment = Pos.Center
                                        }
                                        ,buffer(20,20,20,20)
                                        ,new Text {
                                            text = "Die anderen Spieler:"
                                            style = "-fx-font-size: 20pt"
                                        }
                                        ,buffer(10,10,10,10)
                                        ,new Text {
                                            text = (for p <- order.tail yield p.name + " mit " + p.ochsen + " Ochsen\n").mkString
                                            style = "-fx-font-size: 20pt"
                                            fill = bright
                                            alignment = Pos.Center
                                        }
                                        ,new Button {
                                            text = "Restart"
                                            style = "-fx-font-size: 20pt"
                                            minWidth = 1/6 * windowWidth
                                            minHeight = 1/6 * windowHeight
                                            alignment = Pos.Center
                                            onMouseClicked = (event) => {
                                                stage = InitStage(500,800)
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
                        ,buffer(20,20,20,20)
                    )
                }
                resizable = false
            }
        }
    }
}
