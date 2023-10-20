// Start of the Programm.
@main
def start() =  {
    // create Player p as value
    val p = new Player("Patrick", Vector(2), 0)
    // create Board s as value
    val s = new Board(Vector(Row(1, Vector(7))))
    println(p.toString() + "\n" + s.toString())
}