import kotlin.math.pow

fun main() {
    when (readln()) {
        "amount" -> compoundInterest(amount = readln().toInt())
        "percent" -> compoundInterest(percent = readln().toInt())
        "years" -> compoundInterest(years = readln().toInt())
        else -> compoundInterest()
    }.let { println(it) }
}

fun compoundInterest(amount: Int = 1000, percent: Int = 5, years: Int = 10): Int {
    return (amount * (1 + percent.toDouble().div(100.0)).pow(years)).toInt()
}