fun main() {
    val inputList: MutableList<MutableList<String>> = mutableListOf()
    val n = readln().toInt()
    for (i in 0 until n) {
        val strings = readln().split(' ').toMutableList()
        inputList.add(strings)
    }
    println("${inputList.first().first()} ${inputList.first().last()}")
    println("${inputList.last().first()} ${inputList.last().last()}")
}