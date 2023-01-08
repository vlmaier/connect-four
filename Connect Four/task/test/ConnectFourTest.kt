import org.hyperskill.hstest.dynamic.DynamicTest
import org.hyperskill.hstest.stage.StageTest
import org.hyperskill.hstest.testcase.CheckResult
import org.hyperskill.hstest.testing.TestedProgram

class ConnectFourTest : StageTest<Any>() {

    @DynamicTest
    fun cf4Test(): CheckResult {
        val whiteDiscs = mutableListOf<Pair<Int, Int>>()
        val blackDiscs = mutableListOf<Pair<Int, Int>>()
        val inputDimensions = listOf<String>("5x5", "9X9", "9X5", "", "   7   x   9   ", "  8  X   6   ", "\t  9 \tX \t5  \t ")

        for (input in inputDimensions) {
            val main = TestedProgram()
            var outputString = main.start().trim()

            var position = checkOutput(outputString.lowercase(), 0, "connect four")
            if (position == -1) return CheckResult(false, "Wrong program title.")
            position = checkOutput(outputString.lowercase(), position, "first player's name:")
            if (position == -1) return CheckResult(false, "Wrong prompt for first player's name.")

            outputString = main.execute("Anna").trim()
            position = checkOutput(outputString.lowercase(), 0, "second player's name:")
            if (position == -1) return CheckResult(false, "Wrong prompt for second player's name.")

            outputString = main.execute("Joan").trim()
            position = checkOutput(outputString.lowercase(), 0,
                "set the board dimensions (rows x columns)", "press enter for default (6 x 7)")
            if (position == -1) return CheckResult(false, "Wrong prompt for board dimensions.")

            outputString = main.execute(input).trim()
            val (r, c) = if (input == "") listOf("6", "7") else input.lowercase().split("x").map{ it -> it.trim() }

            position = checkOutput(outputString.lowercase(), 0, "anna vs joan", "$r x $c board")
            if (position == -1) return CheckResult(false, "Wrong game information output.")
            position = checkOutput(outputString.lowercase(), position,
                * getBoard(r.toInt(), c.toInt() ,whiteDiscs, blackDiscs))
            if (position == -1) return CheckResult(false, "Wrong board output.")

            main.stop()
        }

        return CheckResult.correct()
    }

    @DynamicTest
    fun cf2Test(): CheckResult {
        val main = TestedProgram()
        var outputString = main.start().trim()

        var position = checkOutput(outputString.lowercase(), 0, "connect four")
        if ( position  == -1 ) return CheckResult(false, "Wrong program title.")
        position = checkOutput(outputString.lowercase(), position, "first player's name:")
        if ( position  == -1 ) return CheckResult(false, "Wrong prompt for first player's name.")

        outputString = main.execute("Anna").trim()
        position = checkOutput(outputString.lowercase(), 0, "second player's name:")
        if ( position  == -1 ) return CheckResult(false, "Wrong prompt for second player's name.")

        outputString = main.execute("Joan").trim()
        position = checkOutput(outputString.lowercase(), 0,
            "set the board dimensions (rows x columns)", "press enter for default (6 x 7)")
        if ( position  == -1 ) return CheckResult(false, "Wrong prompt for board dimensions.")

        var inputDimensions = mutableListOf<String>("4x5", "4X5", "10x6", "12x6")
        for (input in inputDimensions) {
            outputString = main.execute(input).trim()
            position = checkOutput(outputString.lowercase(), 0,
                "board rows should be from 5 to 9",
                "set the board dimensions (rows x columns)", "press enter for default (6 x 7)")
            if (position == -1) return CheckResult(false,
                "Wrong error message for out of range row size.")
        }

        inputDimensions = mutableListOf<String>("6x1", "7X4", "8x10", "9x30")
        for (input in inputDimensions) {
            outputString = main.execute(input).trim()
            position = checkOutput(outputString.lowercase(), 0,
                "board columns should be from 5 to 9",
                "set the board dimensions (rows x columns)", "press enter for default (6 x 7)")
            if (position == -1) return CheckResult(false, "Wrong error message for out of range column size.")
        }

        outputString = main.execute("9X5").trim()
        position = checkOutput(outputString.lowercase(), 0, "anna vs joan", "9 x 5 board")
        if (position == -1) return CheckResult(false, "Wrong game information output.")

        main.stop()
        return CheckResult.correct()
    }

    @DynamicTest
    fun cf3Test(): CheckResult {
        val main = TestedProgram()
        var outputString = main.start().trim()

        var position = checkOutput(outputString.lowercase(), 0, "connect four")
        if ( position  == -1 ) return CheckResult(false, "Wrong program title.")
        position = checkOutput(outputString.lowercase(), position, "first player's name:")
        if ( position  == -1 ) return CheckResult(false, "Wrong prompt for first player's name.")

        outputString = main.execute("Anna").trim()
        position = checkOutput(outputString.lowercase(), 0, "second player's name:")
        if ( position  == -1 ) return CheckResult(false, "Wrong prompt for second player's name.")

        outputString = main.execute("Joan").trim()
        position = checkOutput(outputString.lowercase(), 0,
            "set the board dimensions (rows x columns)", "press enter for default (6 x 7)")
        if ( position  == -1 ) return CheckResult(false, "Wrong prompt for board dimensions.")

        val inputDimensions = mutableListOf<String>("6x", "X5", "10k6", "12Z6", "a  7x9", "5x8  t")
        for (input in inputDimensions) {
            outputString = main.execute(input).trim()
            position = checkOutput(outputString.lowercase(), 0,
                "invalid input", "set the board dimensions (rows x columns)", "press enter for default (6 x 7)")
            if (position == -1) return CheckResult(false,
                "Wrong error message for out of range row size.")
        }

        outputString = main.execute("9X5").trim()
        position = checkOutput(outputString.lowercase(), 0, "anna vs joan", "9 x 5 board")
        if (position == -1) return CheckResult(false, "Wrong game information output.")

        main.stop()
        return CheckResult.correct()
    }

}

fun checkOutput(outputString: String, searchPos: Int, vararg checkStr: String): Int {
    var searchPosition = searchPos
    val isPlainText = outputString.contains('|') || outputString.contains('=')
    for (str in checkStr) {
        val newStr = if ( isPlainText ) str
            .replace('║', '|')
            .replace('╚', '=')
            .replace('═', '=')
            .replace('╩', '=')
            .replace('╝', '=')
        else str
        val findPosition = outputString.indexOf(newStr, searchPosition)
        if (findPosition == -1) return -1
        if ( outputString.substring(searchPosition until findPosition).isNotBlank() ) return -1
        searchPosition = findPosition + newStr.length
    }
    return searchPosition
}

fun getBoard(rows: Int, columns: Int, whiteDiscs: MutableList<Pair<Int, Int>>, blackDiscs: MutableList<Pair<Int, Int>>): Array<String> {
    val boardStr = StringBuilder()
    for (i in 1 until columns)
        boardStr.append("$i ")
    boardStr.appendLine("$columns")

    for (j in rows downTo 1) {
        for (i in 1..columns) {
            val strPos = if ( whiteDiscs.contains(Pair(j, i)) ) "║o" else if ( blackDiscs.contains(Pair(j, i)) ) "║*" else "║ "
            boardStr.append(strPos)
        }
        boardStr.appendLine("║")
    }

    boardStr.append("╚═")
    for (i in 1 until columns)
        boardStr.append("╩═")
    boardStr.appendLine("╝")

    return boardStr.toString().split("\n").toTypedArray()
}


