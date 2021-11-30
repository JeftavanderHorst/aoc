import java.io.File
import kotlin.math.floor

fun main() {
    println(
        File("input.txt")
            .readLines()
            .map { it.toDouble() }
            .sumOf { mass -> floor(mass / 3) - 2 }
    )
}
