package connectfour

const val DEFAULT_ROWS = 6
const val DEFAULT_COLUMNS = 7
const val DELIMITER = 'x'

fun main() {
    println("Connect Four")
    println("First player's name:")
    val firstPlayer = readln()
    println("Second player's name:")
    val secondPlayer = readln()
    val dimensions = askForDimensions()
    println("$firstPlayer VS $secondPlayer")
    println("${dimensions.first} X ${dimensions.last} board")
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