#include <stdio.h>

// add case in front so you don't have to write new
case class Spieler(val name: String, val karten: Vector[Int], val ochsen: Int) {
    override def toString(): String = {
        name + ":\n\tKarten: " + karten.mkString(", ") + "\n\tOchsen: " + ochsen.toString() + "\n"
    }
}

// add case in front so you don't have to write new
case class Reihe(val nummer: Int, val karten: Vector[Int]) {
    override def toString(): String = {
        "Reihe " + nummer + ": " + karten.mkString(", ")
    }
}

// add case in front so you don't have to write new
case class Spielfeld(val reihen: Vector[Reihe]){
    override def toString(): String = { 
        "Spielfeld:\n\t" + reihen.mkString("\n\t") + "\n"
    }
}

// Start of the Programm.
@main
def start() =  {
    // create Spieler p as value
    val p = new Spieler("Patrick", Vector(2), 0)
    // create Spielfeld s as value
    val s = new Spielfeld(Vector(Reihe(1, Vector(7))))
    println(p.toString() + "\n" + s.toString())
}

int notMain(int x) {
  start: 
  printf("This is an invasion by c. ");
  goto start;
}