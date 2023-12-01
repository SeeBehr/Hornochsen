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

class GUI(controler: Controler) extends UI{
    object Stage extends JFXApp3 {
        override def start(): Unit = {
            stage = new JFXApp3.PrimaryStage {
                title = "Hornochsen"
                scene = new Scene {
                    fill = Black
                    content = new VBox {
                        children = Seq(
                            new Text {
                                text = "Hornochsen"
                                style = "-fx-font-size: 20pt"
                                fill = Red
                            },
                            new HBox {
                                children = Seq(
                                    new Text {
                                        text = "Row Number: "
                                    },
                                    new Text {
                                        text = "Cards"
                                    }
                                )
                            },
                            new HBox {
                                children = Seq(
                                    new Text {
                                        text = "Spielername"
                                    },
                                    new Text {
                                        text = "Handkarten"
                                    },
                                    new Text {
                                        text = "Ochsen"
                                    }
                                )
                            },
                        )
                    }
                }
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