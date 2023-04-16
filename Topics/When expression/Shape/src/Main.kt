fun main() {
    var message = "You have chosen a "
    when (readln().toInt()) {
        1 -> message += "square"
        2 -> message += "circle"
        3 -> message += "triangle"
        4 -> message += "rhombus"
        else -> message = "There is no such shape!"
    }
    println(message)
}