fun main() {
    val year = readln().toInt()
    if (year < 1900 || year > 3000) return
    print(if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) "Leap" else "Regular")
}