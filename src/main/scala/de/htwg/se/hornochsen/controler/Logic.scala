package de.htwg.se.hornochsen.controler

import de.htwg.se.hornochsen.model._

case class GameState(
    players: Vector[Player],
    board: Board,
    remDeck: Deck
) {}

def which(
    cards: Vector[(Int, Player)]
): ((Int, Player), Vector[(Int, Player)]) = {
    var min = cards.min((x, y) => x._1 - y._1)
    return (min, cards.filter(x => x._1 != min._1))
}

def where(b: Board, card: Int): Int = {
    val lastElements: Vector[Int] = b.rows.map(row => row.cards(row.filled-1))
    val minRow = lastElements.map(x => card - x).filter(x => x > 0)
    print(minRow)
    if minRow.length == 0
    then
        return -1
    return lastElements.indexOf(minRow.min((x, y) => x - y))
}

def canAdd(b: Board, index: Int): Boolean = {
    if index < 0 | index >= b.rows(0).cards.length then return false
    return b.rows(index).filled < b.rows(index).cards.length
}

def initDeck(number: Int): Deck = Deck(Vector.tabulate(number)(x => x))

def initBoard(numRows: Int, numRowCards: Int, deck: Deck): (Board, Deck) = {
    (
        Board(
        rows = Vector.tabulate(numRows)(index => {
            Row(
            nummer = index + 1,
            cards = Vector.tabulate(numRowCards)(count =>
                if count == 0 then deck.cards(index) else 0
            )
            )
        }),
        playedCards = Vector.empty
        ),
        deck.remcount(numRows)
    )
}

def initAllPlayers(
    numPlayer: Int,
    numHandCards: Int,
    input: () => String,
    deck: Deck
): (Vector[Player], Deck) = {
    (
        Vector.tabulate(numPlayer)(index => {
        println(s"Spielername${index + 1}: ")
        Player(
            name = input(),
            cards = Vector.tabulate(numHandCards)(count =>
            deck.cards(count + numHandCards * index)
            ),
            ochsen = 0
        )
        }),
        deck.remcount(numHandCards * numPlayer)
    )
}

def updateGamestate(
    gameState: GameState,
    cardsToPlay: Vector[(Int, Player)],
    WhichRowTake: String => Int
): GameState = {
	print("start update")
	val update: Vector[(Board, Player)] =
		cardsToPlay.sortBy((card: Int, player: Player) => card: Int)
    
	.map[(Board, Player)]((card, player) =>
		val index: Int = where(gameState.board, card)
		if index == -1
		then 
			val nim = WhichRowTake(player.name)
			val (board, ochsen) = gameState.board.takeRow(card, nim)
			print("update"+index)
			val updatedPlayer = player.playCard(card).addOchsen(ochsen)
			(board, updatedPlayer)
		else 
			if !canAdd(gameState.board, index)
			then
				val (board, ochsen) = gameState.board.takeRow(card, index)
				val updatedPlayer = player.playCard(card).addOchsen(ochsen)
				print("update")
				(board, updatedPlayer)
				else
					val board = gameState.board.addCard(card, index)
					val updatedPlayer = player.playCard(card)
					print("update")
					(board, updatedPlayer)
	)
	/*
	.foreach((card: Int, player: Player) =>
		val index: Int = where(gameState.board, card)
		if index != -1 && canAdd(gameState.board, index)
		then
			val newBoard = gameState.board.addCard(card, index)
			val uplayer = player.playCard(card)
			(newBoard, gameState.players.updated(gameState.players.indexOf(player), uplayer))
		if index != -1
		then
			val (newBoard, ochsen) = gameState.board.takeRow(card, index)
			val uplayer = player.playCard(card).addOchsen(ochsen)
			(newBoard, gameState.players.updated(gameState.players.indexOf(player), uplayer))
		else
			val nim = WhichRowTake(player.name)
			val (newBoard, ochsen) = gameState.board.takeRow(card, nim)
			val uplayer = player.playCard(card).addOchsen(ochsen)
			(newBoard, gameState.players.updated(gameState.players.indexOf(player), uplayer)))
	}*/
	/*
	.foldLeft((gameState.board, gameState.players)) { case ((board, players), (card, player)) =>
		val index: Int = where(board, card)
		if index != -1 && canAdd(board, index)
		then
			val nBoard = board.addCard(card, index)
			val uplayer = player.playCard(card)
			(nBoard, players.updated(players.indexOf(player), uplayer))
		else
			if index != -1
			then
				val (nBoard, ochsen) = board.takeRow(card, index)
				val uplayer = player.playCard(card).addOchsen(ochsen)
				(nBoard, players.updated(players.indexOf(player), uplayer))
			else
				val take = WhichRowTake(player.name)
				val (nBoard, ochsen) = board.takeRow(card, take)
				val uplayer = player.playCard(card).addOchsen(ochsen)
				(nBoard, players.updated(players.indexOf(player), uplayer))
	}*/
	val updatedPlayers: Vector[Player] = update.map((d, player) => player)
	GameState(players=updatedPlayers, board=update.last._1, remDeck=gameState.remDeck)
}

def selectAllCards(
    gameState: GameState,
    cardsToPlay: Vector[(Int, Player)]
): GameState = {
    return GameState(
        board = Board(
        rows = gameState.board.rows,
        playedCards = gameState.board.playedCards.appendedAll(for {
            pl <- gameState.players
            card <- cardsToPlay.filter(f => f._2 == pl)
            updatedPlayer = pl.playCard(card._1)
        } yield (card._1, updatedPlayer))
        ),
        players = gameState.players.map { pl =>
        gameState.board.playedCards
            .collectFirst {
            case (card, updatedPlayer) if updatedPlayer.name == pl.name =>
                updatedPlayer
            }
            .getOrElse(pl)
        },
        remDeck = gameState.remDeck
    )
}
