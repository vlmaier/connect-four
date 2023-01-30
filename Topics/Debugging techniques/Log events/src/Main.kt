fun String?.capitalize(): String? {
    println("Before: $this")
    val capitalized = when {
        isNullOrBlank() -> this
        length == 1 -> uppercase()
        else -> this[0].uppercase() + substring(1)
    }
    println("After: $capitalized")
    return capitalized
}