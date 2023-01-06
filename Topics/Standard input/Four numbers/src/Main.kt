import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)
    val numbers = List(4) { scanner.nextInt() }
    numbers.forEach { println(it) }
}