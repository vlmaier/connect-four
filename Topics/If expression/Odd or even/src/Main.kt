fun main() = println(
    when {
        readln().toInt() % 2 == 0 -> "EVEN"
        else -> "ODD"
    }
)