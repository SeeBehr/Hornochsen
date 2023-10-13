
case class Spieler(val name: String, val karten: Vector[Int], val ochsen: Int) {
    override def toString(): String = {
        name + ":\n\tKarten: " + karten.mkString(", ") + "\n\tOchsen: " + ochsen.toString() + "\n"
    }
}

case class Reihe(val nummer: Int, val karten: Vector[Int]) {
    override def toString(): String = {
        "Reihe " + nummer + ": " + karten.mkString(", ")
    }
}

case class Spielfeld(val reihen: Vector[Reihe]){
    override def toString(): String = { 
        "Spielfeld:\n\t" + reihen.mkString("\n\t") + "\n"
    }
}

// Das ist ein Kommentar der nichts bringt. 
@main
def start() =  {
    val p = new Spieler("Patrick", Vector(2), 0)
    val f = new Spielfeld(Vector(Reihe(1, Vector(7))))
    println(p.toString() + "\n" + f.toString())
}