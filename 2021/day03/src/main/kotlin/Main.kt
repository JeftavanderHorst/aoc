import java.io.File

fun majority(strings: List<String>, index: Int, tiebreaker: Char): Char {
    var zeroes = 0
    var ones = 0

    for (string in strings) {
        when (string[index]) {
            '0' -> zeroes++
            '1' -> ones++
            else -> throw Exception("Invalid input")
        }
    }

    return when {
        (zeroes > ones) -> '0'
        (ones > zeroes) -> '1'
        else -> tiebreaker
    }
}

fun main() {
    var lines = File("input.txt").readLines()

    for (i in 0 until lines[0].length) {
        val majority = majority(lines, i, '1')
        lines = lines.filter { it[i] == majority }

        if (lines.size == 1) {
            break
        }
    }

    val oxygen = lines[0].toInt(2)
    println(oxygen)

    // Reading input again lmao
    lines = File("input.txt").readLines()
    for (i in 0 until lines[0].length) {
        val majority = majority(lines, i, '1')
        lines = lines.filter { it[i] != majority }

        if (lines.size == 1) {
            println(lines)
            break
        }
    }

    val co2 = lines[0].toInt(2)
    println(co2)

    println(oxygen * co2)
}
