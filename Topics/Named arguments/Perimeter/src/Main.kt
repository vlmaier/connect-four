import kotlin.math.hypot

fun perimeter(x1: Double, y1: Double, x2: Double, y2: Double, x3: Double, y3: Double, x4: Double = x1, y4: Double = y1): Double {
    val distances = mutableListOf<Double>()
    distances.add(hypot(x1 - x2, y1 - y2))
    distances.add(hypot(x2 - x3, y2 - y3))
    distances.add(hypot(x3 - x4, y3 - y4))
    distances.add(hypot(x4 - x1, y4 - y1))
    return distances.sum()
}
