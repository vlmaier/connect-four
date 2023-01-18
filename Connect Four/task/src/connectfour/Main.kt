package connectfour

const val DEFAULT_ROWS = 6
const val DEFAULT_COLUMNS = 7
const val DELIMITER = 'x'
const val EMPTY = ' '
const val FIRST_PLAYER_DISK = 'o'
const val SECOND_PLAYER_DISK = '*'
var rows = DEFAULT_ROWS
var columns = DEFAULT_COLUMNS
val board = mutableListOf<MutableList<Char>>()

fun main() {
    println("Connect Four")
    println("First player's name:")
    val firstPlayer = readln()
    println("Second player's name:")
    val secondPlayer = readln()
    val dimensions = askForDimensions()
    println("$firstPlayer VS $secondPlayer")
    rows = dimensions.first
    columns = dimensions.last
    println("$rows X $columns board")
    initBoard()
    printBoard()
    play(Pair(firstPlayer, secondPlayer))
}

fun askForDimensions(): IntRange {
    println("Set the board dimensions (Rows x Columns)")
    println("Press Enter for default (6 x 7)")
    val input = readln().lowercase().replace("\\s+".toRegex(), "")
    if (input.isEmpty()) {
        return DEFAULT_ROWS..DEFAULT_COLUMNS
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
    return rows..columns
}

fun initBoard() {
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

fun play(players: Pair<String, String>) {
    var currentPlayer = players.first
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
        currentPlayer = if (currentPlayer == players.first) players.second else players.first
    }
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

fun addToBoard(column: Int, disk: Char): Boolean  {
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
