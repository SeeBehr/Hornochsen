// add case in front so you don't have to write new
case class Player(val name: String, val cards: Vector[Int], val ochsen: Int) {
    override def toString(): String = {
        name + ":\n\tcards: " + cards.mkString(", ") + "\n\tOchsen: " + ochsen.toString() + "\n"
    }
}

// add case in front so you don't have to write new
case class Row(val nummer: Int, val cards: Vector[Int]) {
    override def toString(): String = {
        "Row " + nummer + ": " + cards.mkString(", ")
    }
}

// add case in front so you don't have to write new
case class Board(val Rown: Vector[Row]){
    override def toString(): String = { 
        "Board:\n\t" + Rown.mkString("\n\t") + "\n"
    }
}

// Start of the Programm.
@main
def start() =  {
    // create Player p as value
    val p = new Player("Patrick", Vector(2), 0)
    // create Board s as value
    val s = new Board(Vector(Row(1, Vector(7))))
    println(p.toString() + "\n" + s.toString())
}