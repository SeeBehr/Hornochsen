package de.htwg.se.hornochsen.model

case class Player(val name: String, val cards: Vector[Int], val ochsen: Int) {
    override def toString(): String = {
        name + ":\n\tcards: " + cards.mkString(", ") + "\n\tOchsen: " + ochsen.toString() + "\n"
    }

    def playCard(cardNr: Int): Player = {
        Player(name, cards.filterNot(_ == cardNr), ochsen)
    }
}
case class AllPlayers(player: Vector[Player]) {
    override def toString(): String = {
        player.mkString("\n")
    }
}
def initAllPlayers(numPlayer: Int, numHandCards: Int, input:()=>String, deck: Deck):(AllPlayers,Deck) = {
    (AllPlayers(player=Vector.tabulate(numPlayer)(index => {
        println(s"Spielername${index+1}: ")
        Player(name=input(),
        cards=Vector.tabulate(numHandCards)(count => deck.cards(count+numHandCards*index)), ochsen=0)
    })), deck.remcount(numHandCards*numPlayer))
}