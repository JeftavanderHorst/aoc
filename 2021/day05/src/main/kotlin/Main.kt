import java.io.File
import kotlin.math.min
import kotlin.math.max
import kotlin.math.abs

data class Pos(val x: Int, val y: Int)

data class Line(val from: Pos, val to: Pos)

fun parsePos(string: String): Pos {
    val parts = string.split(",")
    return Pos(parts[0].toInt(), parts[1].toInt())
}

fun trace(line: Line): List<Pos> {
    val dx = abs(line.from.x - line.to.x)
    val dy = line.from.y - line.to.y

    if (dx == 0) {
        // Vertical line
        return (min(line.to.y, line.from.y)..max(line.to.y, line.from.y)).map { Pos(line.from.x, it) }
    }

    val slope = dy / dx

    val range = when {
        line.from.x < line.to.x -> line.from.x..line.to.x
        line.from.x > line.to.x -> line.from.x downTo line.to.x
        else -> throw Exception()
    }

    return range.withIndex().map { Pos(it.value, line.from.y - (slope * it.index)) }
}

fun main() {
    val positions = File("input.txt")
        .readLines()
        .map { it.split(" -> ") }
        .map { Line(parsePos(it[0]), parsePos(it[1])) }
        .flatMap { trace(it) }

    val tiles = mutableMapOf<Pos, Int>()

    for (pos in positions) {
        if (!tiles.containsKey(pos)) {
            tiles[pos] = 0
        }

        tiles[pos] = tiles[pos]!! + 1
    }

    println(tiles.count { it.value >= 2 })
}
