private const val AMOUNT_OF_LINES = 2

fun main() {
    val lines = List(AMOUNT_OF_LINES) { readln() }
    println(lines[0] == lines[1])
}