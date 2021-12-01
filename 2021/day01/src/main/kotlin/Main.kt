import java.io.File

fun main() {
    val lines = File("input.txt")
        .readLines()
        .map { it.toInt() }

    var last = lines[0] + lines[1] + lines[2]
    var increments = 0
    for (i in 2 until lines.size) {
        val window = lines[i] + lines[i - 1] + lines[i - 2]

        if (window > last) {
            increments++
        }

        last = window
    }

    println(increments)
}
