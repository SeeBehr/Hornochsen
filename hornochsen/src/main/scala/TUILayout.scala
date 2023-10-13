class Spieler(val name: String, val karten: Array[Int], val ochsen: Int) {
        override def toString(): String = {
        name + ":\n\t" + karten.mkString(", ") + "\n\tOchsen: " + ochsen.toString() + "\n"
    }
}
class Reihe(val nummer: Int, val karten: Array[Int]) {
    override def toString(): String = {
        "Reihe " + nummer + ": " + karten.mkString(", ")
    }
}
class Spielfeld(val reihen: Array[Reihe]){
    override def toString(): String = {
        "Spielfeld:\n\t" + reihen.mkString("\n\t") + "\n"
    }
}

@main
def start() =  {
    val p = new Spieler("Patrick", Array(2, 7, 4), 0)
    val f = new Spielfeld(Array(Reihe(1, Array(1, 2, 3)), Reihe(2, Array(1, 2, 3)), Reihe(3, Array(1, 2, 3))))
    println(p.toString() + "\n" + f.toString())
}