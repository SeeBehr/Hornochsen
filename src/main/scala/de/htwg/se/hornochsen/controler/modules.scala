package de.htwg.se.hornochsen.controler.modules

import de.htwg.se.hornochsen.controler.BaseControler.Controler
import de.htwg.se.hornochsen.controler.InterfaceControler
import de.htwg.se.hornochsen.model.InterfaceGameState

object Default {
  given InterfaceControler(using state: InterfaceGameState): InterfaceControler = new Controler(state)
}