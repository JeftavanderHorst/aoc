import java.io.File

fun main() {
    val lines = File("input.txt")
        .readLines()

    val counts = MutableList(lines[0].length) { 0 }
    for (line in lines) {
        for ((index, char) in line.toCharArray().withIndex()) {
            if (char == '1') {
                counts[index]++
            }
        }
    }

    val gamma = counts.map {
        when {
            it > (lines.size / 2) -> '1'
            else -> '0'
        }
    }.joinToString("").toInt(2)

    val epsilon = counts.map {
        when {
            it > (lines.size / 2) -> '0'
            else -> '1'
        }
    }.joinToString("").toInt(2)

    println("$gamma * $epsilon = ${gamma * epsilon}")
}
