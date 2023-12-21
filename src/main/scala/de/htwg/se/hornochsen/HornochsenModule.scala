package de.htwg.se.hornochsen

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import net.codingwell.scalaguice.ScalaModule
import com.google.inject.TypeLiteral
import de.htwg.se.hornochsen.controler.InterfaceControler
import de.htwg.se.hornochsen.controler.BaseControler.Controler
import de.htwg.se.hornochsen.model.BaseModel.GameState
import de.htwg.se.hornochsen.model.InterfaceGameState
import com.google.inject.assistedinject.FactoryModuleBuilder
import de.htwg.se.hornochsen.model.GameStateFactory


class HornochsenModule extends AbstractModule {

  override def configure(): Unit = {
    install(new FactoryModuleBuilder()
      .implement(classOf[InterfaceGameState], classOf[GameState])
      .build(classOf[GameStateFactory]))
  }
}