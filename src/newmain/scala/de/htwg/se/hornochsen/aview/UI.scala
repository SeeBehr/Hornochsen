import de.htwg.se.hornochsen.controler._
import de.htwg.se.hornochsen.model._
import de.htwg.se.hornochsen.util._

trait UI extends Observer {
    override def update(e: Event): Unit
    def run: Unit
    def interpretLine(input: String): Unit
    def end: Unit
}