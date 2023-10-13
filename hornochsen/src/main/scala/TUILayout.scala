class Spieler(val name: String, val karten: Vector[Int], val ochsen: Int) {
    override def toString(): String = {
        name + ":\n\t" + karten.mkString(", ") + "\n\tOchsen: " + ochsen.toString() + "\n"
    }
}

class Reihe(val nummer: Int, val karten: Vector[Int]) {
    override def toString(): String = {
        "Reihe " + nummer + ": " + karten.mkString(", ")
    }
}

class Spielfeld(val reihen: Vector[Reihe]){
    override def toString(): String = { 
        "Spielfeld:\n\t" + reihen.mkString("\n\t") + "\n"
    }
}

@main
def start() =  {
    val p = new Spieler("Patrick", Vector(2, 7, 4), 0)
    val f = new Spielfeld(Vector(Reihe(1, Vector(1, 2, 3)), Reihe(2, Vector(1, 2, 3)), Reihe(3, Vector(1, 2, 3))))
    println(p.toString() + "\n" + f.toString())
}