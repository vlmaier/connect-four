import org.hyperskill.hstest.dynamic.DynamicTest
import org.hyperskill.hstest.stage.StageTest
import org.hyperskill.hstest.testcase.CheckResult
import org.hyperskill.hstest.testing.TestedProgram

class ConnectFourTest : StageTest<Any>() {
    @DynamicTest
    fun cf1Test(): CheckResult {
        val inputDimensions = listOf<String>("5x5", "9X9", "", "   7   x   9   ", "  8  X   6   ", "\t  9 \tX \t5  \t ")

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
            position = checkOutput(
                outputString.lowercase(),
                0,
                "set the board dimensions (rows x columns)",
                "press enter for default (6 x 7)"
            )
            if (position == -1) return CheckResult(false, "Wrong prompt for board dimensions.")

            outputString = main.execute(input).trim()
            val (r, c) = if (input == "") listOf("6", "7") else input.lowercase().split("x").map{ it -> it.trim() }

            position = checkOutput(outputString.lowercase(), 0, "anna vs joan", "$r x $c board")
            if (position == -1) return CheckResult(false, "Wrong game information output.")
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
        position = checkOutput(outputString.lowercase(), 0, "set the board dimensions (rows x columns)", "press enter for default (6 x 7)")
        if ( position  == -1 ) return CheckResult(false, "Wrong prompt for board dimensions.")

        var inputDimensions = mutableListOf<String>("4x5", "4X5", "10x6", "12x6")
        for (input in inputDimensions) {
            outputString = main.execute(input).trim()
            position = checkOutput(outputString.lowercase(), 0, "board rows should be from 5 to 9", "set the board dimensions (rows x columns)", "press enter for default (6 x 7)")
            if (position == -1) return CheckResult(false, "Wrong error message for out of range row size.")
        }

        inputDimensions = mutableListOf<String>("6x1", "7X4", "8x10", "9x30")
        for (input in inputDimensions) {
            outputString = main.execute(input).trim()
            position = checkOutput(outputString.lowercase(), 0, "board columns should be from 5 to 9", "set the board dimensions (rows x columns)", "press enter for default (6 x 7)")
            if (position == -1) return CheckResult(false, "Wrong error message for out of range column size.")
        }

        outputString = main.execute("9X5").trim()
        position = checkOutput(outputString.lowercase(), 0, "anna vs joan", "9 x 5 board")
        if (position == -1) return CheckResult(false, "Wrong game information output.")

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
        position = checkOutput(outputString.lowercase(), 0, "set the board dimensions (rows x columns)", "press enter for default (6 x 7)")
        if ( position  == -1 ) return CheckResult(false, "Wrong prompt for board dimensions.")

        val inputDimensions = mutableListOf<String>("6x", "X5", "10k6", "12Z6", "a  7x9", "5x8  t")
        for (input in inputDimensions) {
            outputString = main.execute(input).trim()
            position = checkOutput(outputString.lowercase(), 0, "invalid input", "set the board dimensions (rows x columns)", "press enter for default (6 x 7)")
            if (position == -1) return CheckResult(false, "Wrong error message for out of range row size.")
        }

        outputString = main.execute("9X5").trim()
        position = checkOutput(outputString.lowercase(), 0, "anna vs joan", "9 x 5 board")
        if (position == -1) return CheckResult(false, "Wrong game information output.")

        return CheckResult.correct()
    }

}



fun checkOutput(outputString: String, searchPos: Int, vararg checkStr: String): Int {
    var searchPosition = searchPos
    for (str in checkStr) {
        val findPosition = outputString.indexOf(str, searchPosition)
        if (findPosition == -1) return -1
        if ( outputString.substring(searchPosition until findPosition).isNotBlank() ) return -1
        searchPosition = findPosition + str.length
    }
    return searchPosition
}

