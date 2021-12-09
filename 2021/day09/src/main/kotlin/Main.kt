import java.io.File

data class Pos(val x: Int, val y: Int)

fun main() {
    val lines = File("input.txt")
        .readLines()
        .map { it.toCharArray().map { height -> height.digitToInt() } }

    fun inBounds(pos: Pos): Boolean = lines.indices.contains(pos.y) && lines[0].indices.contains(pos.x)

    val assignedToBasin = mutableSetOf<Pos>()

    fun measureBasin(height: Int, pos: Pos): Int {
        if (lines[pos.y][pos.x] >= 9 || assignedToBasin.contains(pos)) return 0

        assignedToBasin.add(pos)

        return 1 + listOf(
            Pos(pos.x - 1, pos.y),
            Pos(pos.x + 1, pos.y),
            Pos(pos.x, pos.y - 1),
            Pos(pos.x, pos.y + 1),
        )
            .filter { inBounds(it) }
            .sumOf { measureBasin(height + 1, it) }
    }

    val sizes = mutableListOf<Int>()
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
                sizes.add(measureBasin(lines[y][x], Pos(x, y)))
                println("Basin $x x $y has size ${sizes.last()}")
            }
        }
    }

    sizes.sortDescending()
    println("${sizes[0]} * ${sizes[1]} * ${sizes[2]} = ${sizes[0] * sizes[1] * sizes[2]}")
}
