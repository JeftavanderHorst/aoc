import java.io.File

data class Pos(val x: Int, val y: Int)

fun main() {
    val grid = File("input.txt")
        .readLines()
        .map { line -> line.toCharArray().map { digit -> digit.digitToInt() }.toMutableList() }.toMutableList()

    val octopusCount = grid.size * grid[0].size
    println(octopusCount)

    fun inBounds(pos: Pos): Boolean = grid.indices.contains(pos.y) && grid[pos.y].indices.contains(pos.x)

    var flashes = 0
    val updated = mutableSetOf<Pos>()
    fun flash(x: Int, y: Int) {
        flashes++

        grid[y][x] = 0
        updated.add(Pos(x, y))

        listOf(
            Pos(x - 1, y - 1),
            Pos(x, y - 1),
            Pos(x + 1, y - 1),
            Pos(x - 1, y),
            Pos(x + 1, y),
            Pos(x - 1, y + 1),
            Pos(x, y + 1),
            Pos(x + 1, y + 1),
        )
            .filter { inBounds(it) }
            .forEach {
                grid[it.y][it.x]++
                if (grid[it.y][it.x] > 9) {
                    flash(it.x, it.y)
                }
            }
    }

    var generation = 0
    while (true) {
        generation++

        for (y in grid.indices) {
            for (x in grid[y].indices) {
                grid[y][x]++
            }
        }

        for (y in grid.indices) {
            for (x in grid[y].indices) {
                if (grid[y][x] > 9) {
                    flash(x, y)
                }
            }
        }

        for (p in updated) {
            grid[p.y][p.x] = 0
        }

        println("Generation $generation: ${updated.size} flashes")

        if (updated.size >= octopusCount) {
            break
        }

        updated.clear()
    }

    println("$generation")
}
