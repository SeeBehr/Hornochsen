import de.htwg.se.hornochsen._


val p1 = Player("Patrick", Vector(2), 0)
val p2 = Player("Sebastian", Vector(7,15), 3)
val ap = AllPlayer(Vector(p1, p2))
val r1 = Row(1, Vector(7))
val r2 = Row(2, Vector(1,5))
val f = Board(Vector(r1, r2))
println(ap.toString() + "\n" + f.toString())