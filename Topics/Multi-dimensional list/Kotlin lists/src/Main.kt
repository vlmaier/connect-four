fun main() {    
    val inputList: MutableList<MutableList<String>> = mutableListOf()
    val n = readLine()!!.toInt()
    for (i in 0 until n) {
        val strings = readLine()!!.split(' ').toMutableList()
        inputList.add(strings)
    }
    // Do not change lines above
    // Write your code here
    val thirdRow = inputList[2]
    println(thirdRow.joinToString())
}