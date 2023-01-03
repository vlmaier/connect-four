fun main() {
    val x = readln().toBoolean()
    val y = readln().toBoolean()
    val z = readln().toBoolean()
    println(!(x && y) || z)
}