import java.io.File

data class Pos(var x: Long, var y: Long)

fun main() {
    val commands = File("input.txt")
        .readLines()
        .map { it.split(' ') }

    val pos = Pos(0, 0)
    var aim: Long = 0

    for (command in commands) {
        val delta = command[1].toLong()
        when (command[0]) {
            "up" -> aim -= delta
            "down" -> aim += delta
            "forward" -> {
                pos.x += delta
                pos.y += aim * delta
            }
        }
    }

    println(pos.x * pos.y)
}
