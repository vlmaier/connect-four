fun main() {
    val chars = List(3) { readln() }
    val first = chars[0].first()
    val second = chars[1].first()
    val third = chars[2].first()
    println(first + 1 == second && second + 1 == third)
}