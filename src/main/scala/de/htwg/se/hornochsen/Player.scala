package de.htwg.se.hornochsen
// add case in front so you don't have to write new
case class Player(val name: String, val cards: Vector[Int], val ochsen: Int) {
    override def toString(): String = {
        name + ":\n\tcards: " + cards.mkString(", ") + "\n\tOchsen: " + ochsen.toString() + "\n"
    }
}
case class AllPlayer(player: Vector[Player]) {
    override def toString(): String = {
        player.mkString("\n")
    }
}