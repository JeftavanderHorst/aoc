import java.io.File

fun main() {
    val lines = File("input.txt")
        .readLines()
        .map { it.toInt() }

    var last = lines[0]
    var increments = 0
    for (i in 1 until lines.size) {
        if (lines[i] > last) {
            increments++
        }

        last = lines[i]
    }

    println(increments)
}
