const val CHAR_SIZE = 4

fun main() = List(CHAR_SIZE) { readln().first() }.forEach { println(it.isDigit()) }