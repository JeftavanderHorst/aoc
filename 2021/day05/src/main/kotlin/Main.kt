import java.io.File
import kotlin.math.max
import kotlin.math.min

data class Pos(val x: Int, val y: Int)

data class Line(val from: Pos, val to: Pos)

fun parsePos(string: String): Pos {
    val parts = string.split(",")
    return Pos(parts[0].toInt(), parts[1].toInt())
}

fun trace(line: Line): List<Pos> {
    if (line.from.y == line.to.y) {
        // Horizontal line
        return (min(line.to.x, line.from.x)..max(line.to.x, line.from.x)).map { Pos(it, line.from.y) }
    }

    // Vertical line
    return (min(line.to.y, line.from.y)..max(line.to.y, line.from.y)).map { Pos(line.from.x, it) }
}

fun main() {
    val positions = File("input.txt")
        .readLines()
        .map { it.split(" -> ") }
        .map { Line(parsePos(it[0]), parsePos(it[1])) }
        .filter { it.from.x == it.to.x || it.from.y == it.to.y }
        .flatMap { trace(it) }

    val tiles = mutableMapOf<Pos, Int>()

    for (pos in positions) {
        if (!tiles.containsKey(pos)) {
            tiles[pos] = 0
        }

        tiles[pos] = tiles[pos]!! + 1
    }

    println(tiles.count { it.value >= 2})
}
