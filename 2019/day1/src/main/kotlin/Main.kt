import java.io.File
import kotlin.math.floor

fun computeFuel(mass: Double): Double {
    var result = floor(mass / 3) - 2

    if (result > 6) {
        result += computeFuel(result)
    }

    return result
}

fun main() {
    println(
        File("input.txt")
            .readLines()
            .map { it.toDouble() }
            .sumOf { mass -> computeFuel(mass) }
    )
}
