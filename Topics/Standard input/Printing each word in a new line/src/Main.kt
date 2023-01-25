import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)
    var words = mutableListOf<String>()
    while (scanner.hasNext()) {
        words.add(scanner.next())
    }
    words = words.map { it.split(' ') }.flatten().toMutableList()
    words.forEach { println(it) }
}