import java.io.File

data class Pos(val x: Int, val y: Int)

fun main() {
    val pos = File("input.txt")
        .readLines()
        .map { it.split(' ') }
        .fold(Pos(0, 0)) { acc, command ->
            val delta = command[1].toInt()
            when (command[0]) {
                "forward" -> Pos(acc.x + delta, acc.y)
                "up" -> Pos(acc.x, acc.y - delta)
                "down" -> Pos(acc.x, acc.y + delta)
                else -> throw Exception("Invalid command")
            }
        }

    println(pos.x * pos.y)
}
