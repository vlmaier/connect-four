fun solution(numbers: List<Int>) {
    numbers.forEach {
        if (it % 2 == 0) {
            print("$it ")
        }
    }
}