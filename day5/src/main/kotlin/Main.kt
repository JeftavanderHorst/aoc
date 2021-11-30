import java.io.File

fun interpret(string: String, zero: Char, one: Char): Int {
    return string
        .replace(zero, '0')
        .replace(one, '1')
        .toInt(2)
}

fun id(string: String): Int {
    val row = interpret(string.slice(0..6), 'F', 'B')
    val col = interpret(string.slice(7..9), 'L', 'R')
    return row * 8 + col
}

fun main() {
    val chairs = File("input.txt").readLines().map { id(it) }
    val max = chairs.maxOrNull()!!
    val candidates = (0..max).filter { !chairs.contains(it) }

    println(candidates.last())
}
