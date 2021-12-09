import java.io.File

data class Pos(val x: Int, val y: Int)

fun main() {
    val lines = File("input.txt")
        .readLines()
        .map { it.toCharArray().map { it.digitToInt() } }

    fun inBounds(pos: Pos): Boolean = lines.indices.contains(pos.y) && lines[0].indices.contains(pos.x)

    val lows = mutableListOf<Int>()
    for (y in lines.indices) {
        for (x in lines[0].indices) {
            val isLowPoint = listOf(
                Pos(x - 1, y),
                Pos(x + 1, y),
                Pos(x, y - 1),
                Pos(x, y + 1),
            )
                .filter { inBounds(it) }
                .all { lines[it.y][it.x] > lines[y][x] }

            if (isLowPoint) {
                lows.add(lines[y][x] + 1)
            }
        }
    }

    println(lows.sum())
}
