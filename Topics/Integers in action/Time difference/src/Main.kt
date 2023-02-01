fun main() {
    val firstMoment = List(3) { readln().toInt() }
    val secondMoment = List(3) { readln().toInt() }
    val hours = secondMoment[0] - firstMoment[0]
    val minutes = secondMoment[1] - firstMoment[1]
    val seconds = secondMoment[2] - firstMoment[2]
    println(seconds + minutes * 60 + hours * 60 * 60)
}