package de.htwg.se.hornochsen.util

import de.htwg.se.hornochsen.model.InterfaceGameState

trait FileIO {
  def load(state: InterfaceGameState): InterfaceGameState
  def save(state: InterfaceGameState): Unit
}