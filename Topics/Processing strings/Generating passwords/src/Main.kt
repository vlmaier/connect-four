import kotlin.random.Random

fun main() {
    val input = readln().split(" ").map { it.toInt() }
    val uppercase = ('A'..'Z').toList()
    val lowercase = ('a'..'z').toList()
    val digits = ('0'..'9').toList()
    val all = uppercase + lowercase + digits
    var random = ""
    random += pickSymbols(uppercase, input[0])
    random += pickSymbols(lowercase, input[1])
    random += pickSymbols(digits, input[2])
    if (random.length < input[3]) {
        random += pickSymbols(all, kotlin.math.abs(random.length - input[3]))
    }
    val password = random.toCharArray()
    password.shuffle()
    println(password)
}

fun pickSymbols(symbols: List<Char>, size: Int): String {
    if (size == 0) {
        return ""
    }
    var output = ""
    for (i in 1 ..size) {
        output += getRandomSymbol(output, symbols)
    }
    return output
}

fun getRandomSymbol(output: String, symbols: List<Char>): Char {
    val index = Random.nextInt(symbols.size)
    val symbol = symbols[index]
    if (output.contains(symbol)) {
        return getRandomSymbol(output, symbols)
    }
    return symbol
}