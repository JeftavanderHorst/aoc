import java.io.File

data class Pos(val x: Int, val y: Int)

fun printPaper(positions: List<Pos>) {
    val width = positions.maxOf { it.x }
    val height = positions.maxOf { it.y }

    val grid = (0..height).map { y ->
        (0..width).map { x ->
            if (positions.contains(Pos(x, y))) {
                '#'
            } else {
                '.'
            }
        }
    }

    println(grid.joinToString("\n") { it.joinToString("") })
}

fun foldHorizontal(foldX: Int, positions: List<Pos>): List<Pos> {
    return positions.map {
        when {
            it.x > foldX -> Pos(foldX - (it.x - foldX), it.y)
            else -> it
        }
    }.toSet().toList()
}

fun foldVertical(foldY: Int, positions: List<Pos>): List<Pos> {
    return positions.map {
        when {
            it.y > foldY -> Pos(it.x, foldY - (it.y - foldY))
            else -> it
        }
    }
}

fun main() {
    val lines = File("input.txt").readLines()

    val positions = lines
        .takeWhile { it.isNotEmpty() }
        .map { line ->
            val parts = line.split(",")
            Pos(parts[0].toInt(), parts[1].toInt())
        }

    val final = lines
        .takeLastWhile { it.isNotEmpty() }
        .fold(positions) { acc, line ->
            val parts = line.split("=")
            val fold = parts[1].toInt()
            when (parts[0].toCharArray().last()) {
                'x' -> foldHorizontal(fold, acc)
                'y' -> foldVertical(fold, acc)
                else -> throw Exception()
            }
        }

    println(final.size)
    printPaper(final)
}
