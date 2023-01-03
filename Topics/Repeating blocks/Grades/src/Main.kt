const val D = 'D'
const val C = 'C'
const val B = 'B'
const val A = 'A'

fun main() {
    val grades = mutableMapOf(D to 0, C to 0, B to 0, A to 0)
    repeat(readln().toInt()) {
        when (readln().toInt()) {
            2 -> grades[D] = grades[D]?.plus(1)!!
            3 -> grades[C] = grades[C]?.plus(1)!!
            4 -> grades[B] = grades[B]?.plus(1)!!
            5 -> grades[A] = grades[A]?.plus(1)!!
        }
    }
    print("${grades['D']} ${grades['C']} ${grades['B']} ${grades['A']}")
}