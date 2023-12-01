package de.htwg.se.hornochsen.aview
import de.htwg.se.hornochsen.util.Event
import de.htwg.se.hornochsen.model.Player
import de.htwg.se.hornochsen.controler.Controler

class GUI(controler: Controler) extends UI {

  override def end: Unit = ???

  override def run: Unit = ???

  override def WhichRowTake(name: String, read: () => String): Int = ???

  override def playCards(players: Vector[Player], read: () => String): Vector[(Int, Player)] = ???

  override def update(e: Event): Unit = ???
}