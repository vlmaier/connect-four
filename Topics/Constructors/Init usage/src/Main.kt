fun main() {
    val timerValue = readln().toInt()
    val timer = ByteTimer(timerValue)
    println(timer.time)
}

class ByteTimer(_time: Int) {
    var time = _time
    init {
        when {
            time > 127 -> time = 127
            time < -128 -> time = -128
        }
    }
}