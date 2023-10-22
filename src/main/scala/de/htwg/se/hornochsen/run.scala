package de.htwg.se.hornochsen
// Start of the Programm.
@main
def start() =  {
    // create Player p as value
    val p = new Player(name = "Patrick", cards = Vector(2), ochsen = 0)
    // create Board s as value
    val s = new Board(rows = Vector(Row(1, Vector(7))))
    println(p.toString() + "\n" + s.toString())
}