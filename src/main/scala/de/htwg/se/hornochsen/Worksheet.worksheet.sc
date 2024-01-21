import de.htwg.se.hornochsen.controler.BaseControler.Controler
import de.htwg.se.hornochsen.model._
import de.htwg.se.hornochsen.util.IO.JsonIO
val io = JsonIO()
val game = initGameState(Vector[InterfacePlayer](makePlayer("Patrick"), makePlayer("Sebastian")), makeDummyBoard(), makeDummyDeck())
var controller = new Controler(game)
controller.save
val json = io.load(game)