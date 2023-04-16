package connectfour

const val DEFAULT_ROWS = 6
const val DEFAULT_COLUMNS = 7
const val DELIMITER = 'x'
const val EMPTY = ' '
const val FIRST_PLAYER_DISK = 'o'
const val SECOND_PLAYER_DISK = '*'
const val WIN_POINTS = 2
const val DRAW_POINTS = 1
var rows = DEFAULT_ROWS
var columns = DEFAULT_COLUMNS
var board = mutableListOf<MutableList<Char>>()
var firstPlayerScore = 0
var secondPlayerScore = 0

fun main() {
    println("Connect Four")
    println("First player's name:")
    val firstPlayer = readln()
    println("Second player's name:")
    val secondPlayer = readln()
    val dimensions = askForDimensions()
    val numberOfGames = askForNumberOfGames()
    println("$firstPlayer VS $secondPlayer")
    rows = dimensions.first
    columns = dimensions.second
    println("$rows X $columns board")
    println(if (numberOfGames == 1) "Single game" else "Total $numberOfGames games")
    for (i in 1..numberOfGames) {
        if (numberOfGames > 1) {
            println("Game #$i")
        }
        initBoard()
        printBoard()
        play(Pair(firstPlayer, secondPlayer), if (i % 2 != 0) firstPlayer else secondPlayer)
        println("Score")
        println("$firstPlayer: $firstPlayerScore $secondPlayer: $secondPlayerScore")
    }
    println("Game Over!")
}

fun askForDimensions(): Pair<Int, Int> {
    println("Set the board dimensions (Rows x Columns)")
    println("Press Enter for default (6 x 7)")
    val input = readln().lowercase().replace("\\s+".toRegex(), "")
    if (input.isEmpty()) {
        return Pair(DEFAULT_ROWS, DEFAULT_COLUMNS)
    }
    val pattern = "\\d+x\\d+".toRegex(RegexOption.IGNORE_CASE)
    if (!input.matches(pattern)) {
        println("Invalid input")
        return askForDimensions()
    }
    val dimensions = input.split(DELIMITER).map { it.toInt() }.toIntArray()
    val rows = dimensions[0]
    val columns = dimensions[1]
    val validRange = 5..9
    if (rows !in validRange) {
        println("Board rows should be from 5 to 9")
        return askForDimensions()
    }
    if (columns !in validRange) {
        println("Board columns should be from 5 to 9")
        return askForDimensions()
    }
    return Pair(rows, columns)
}

fun askForNumberOfGames(): Int {
    println("Do you want to play single or multiple games?")
    println("For a single game, input 1 or press Enter")
    println("Input a number of games:")
    val numberOfGames = readln()
    if (numberOfGames == "") {
        return 1
    }
    if (!"^[1-9]\\d*\$".toRegex().matches(numberOfGames)) {
        println("Invalid input")
        return askForNumberOfGames()
    }
    return numberOfGames.toInt()
}

fun initBoard() {
    board = mutableListOf()
    repeat(rows) {
        board.add(MutableList(columns) { EMPTY })
    }
}

fun printBoard() {
    println(" ${(1..columns).toList().joinToString(" ")} ")
    for (row in board) {
        for (column in row) {
            print("║$column")
        }
        print("║")
        println()
    }
    println("╚" + "═╩".repeat(columns - 1) + "═╝")
}

fun play(players: Pair<String, String>, whoGoesFirst: String) {
    var currentPlayer = whoGoesFirst
    while (true) {
        val column = askForColumn(currentPlayer)
        if (column == -1) {
            continue
        }
        if (column != null) {
            val disk = if (currentPlayer == players.first) FIRST_PLAYER_DISK else SECOND_PLAYER_DISK
            val isAdded = addToBoard(column, disk)
            if (!isAdded) {
                println("Column $column is full")
                continue
            }
        } else {
            break
        }
        printBoard()
        if (checkWinningCondition()) {
            println("Player $currentPlayer won")
            if (currentPlayer == players.first) {
                firstPlayerScore += WIN_POINTS
            } else {
                secondPlayerScore += WIN_POINTS
            }
            break
        }
        if (checkDrawCondition()) {
            println("It is a draw")
            firstPlayerScore += DRAW_POINTS
            secondPlayerScore += DRAW_POINTS
            break
        }
        currentPlayer = if (currentPlayer == players.first) players.second else players.first
    }
}


fun checkWinningCondition(): Boolean {
    var horizontals = ""
    var verticals = ""
    for (i in board.indices) {
        var horizontal = ""
        var vertical = ""
        for (j in board.indices) {
            horizontal += board[i][j]
            vertical += board[j][i]
        }
        verticals += "$vertical#"
        horizontals += "$horizontal#"
    }
    val diagonals = loopDiagonally()
    val toValidate = (horizontals + verticals + diagonals).replace("\\s".toRegex(), "")
    return "\\*{4}|o{4}".toRegex().find(toValidate) != null
}

fun loopDiagonally(): String {
    var diagonals = loopDiagonally(board)
    diagonals += loopDiagonally(transpose(board.reversed().toMutableList()))
    return diagonals
}

fun <E> loopDiagonally(matrix: List<List<E>>): String {
    val rows = matrix.size
    val columns = matrix[0].size
    var diagonals = ""
    for (k in 0..(columns + rows - 2)) {
        var diagonal = ""
        for (j in 0..k) {
            val i = k - j
            if (i < rows && j < columns) {
                diagonal += matrix[i][j]
            }
        }
        diagonals += "$diagonal#"
    }
    return diagonals
}

fun <E> transpose(matrix: List<List<E>>): List<List<E>> {
    fun <E> List<E>.head(): E = this.first()
    fun <E> List<E>.tail(): List<E> = this.takeLast(this.size - 1)
    fun <E> E.append(array: List<E>): List<E> = listOf(this).plus(array)

    matrix.filter { it.isNotEmpty() }.let { m ->
        return when (m.isNotEmpty()) {
            true -> m.map { it.head() }.append(transpose(m.map { it.tail() }))
            else -> emptyList()
        }
    }
}

fun checkDrawCondition(): Boolean {
    return board.flatten().find { it == ' ' } == null
}

fun askForColumn(playerName: String): Int? {
    println("$playerName's turn:")
    val input = readln()
    if (input == "end") {
        println("Game over!")
        return null
    }
    if (!"\\d+".toRegex().matches(input)) {
        println("Incorrect column number")
        return -1
    }
    val column = input.toInt()
    if (column !in 1..columns) {
        println("The column number is out of range (1 - $columns)")
        return -1
    }
    return column
}

fun addToBoard(column: Int, disk: Char): Boolean {
    var isAdded = false
    for (row in board.reversed()) {
        if (row[column - 1] == EMPTY) {
            row[column - 1] = disk
            isAdded = true
            break
        }
    }
    return isAdded
}
