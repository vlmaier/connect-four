const val CONST: Int = 100
fun main() {
    val listNumber = mutableListOf<Int>()
    listNumber.add(CONST * CONST)
    listNumber.add(CONST * CONST * CONST)
    listNumber.add(CONST * CONST * CONST * CONST)
    println(listNumber)
}