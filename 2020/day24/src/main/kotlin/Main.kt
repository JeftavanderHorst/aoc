import java.io.File

enum class Dir {
    NW, NE,
    W, E,
    SW, SE,
}

data class Pos(val x: Int, val y: Int)

fun parse(string: String) = sequence {
    var i = 0
    while (i < string.length) {
        yield(
            when (string[i++]) {
                'n' -> when (string[i++]) {
                    'w' -> Dir.NW
                    'e' -> Dir.NE
                    else -> throw Exception("Invalid character")
                }
                's' -> when (string[i++]) {
                    'w' -> Dir.SW
                    'e' -> Dir.SE
                    else -> throw Exception("Invalid character")
                }
                'w' -> Dir.W
                'e' -> Dir.E
                else -> throw Exception("Invalid character")
            }
        )
    }
}

fun follow(path: Sequence<Dir>): Pos {
    return path.fold(Pos(0, 0)) { acc, dir ->
        when (dir) {
            Dir.NW -> Pos(acc.x - 1, acc.y - 1)
            Dir.NE -> Pos(acc.x + 1, acc.y - 1)
            Dir.W -> Pos(acc.x - 2, acc.y)
            Dir.E -> Pos(acc.x + 2, acc.y)
            Dir.SW -> Pos(acc.x - 1, acc.y + 1)
            Dir.SE -> Pos(acc.x + 1, acc.y + 1)
        }
    }
}

fun adjecent(pos: Pos): Collection<Pos> {
    return setOf(
        Pos(pos.x - 1, pos.y - 1), // nw
        Pos(pos.x + 1, pos.y - 1), // ne
        Pos(pos.x + 2, pos.y), // e
        Pos(pos.x - 1, pos.y + 1), // sw
        Pos(pos.x + 1, pos.y + 1), // se
        Pos(pos.x - 2, pos.y), // w
    )
}

fun countNeighbors(pos: Pos, blackTiles: Set<Pos>): Int {
    var result = 0
    for (neighbor in adjecent(pos)) {
        if (blackTiles.contains(neighbor)) {
            result++
        }
    }

    return result
}

fun tick(blackTiles: Set<Pos>): Set<Pos> {
    val whiteTiles = (HashSet<Pos>(blackTiles)).flatMap { adjecent(it) }.filter { !blackTiles.contains(it) }.toSet()

    return (blackTiles.filter {
        val neighbors = countNeighbors(it, blackTiles)
        neighbors == 1 || neighbors == 2
    } + whiteTiles.filter {
        val neighbors = countNeighbors(it, blackTiles)
        neighbors == 2
    }).toSet()
}

fun main() {
    val flips = File("input.txt")
        .readLines()
        .map { follow(parse(it)) }

    var blackTiles = mutableSetOf<Pos>()

    for (flip in flips) {
        if (blackTiles.contains(flip)) {
            blackTiles.remove(flip)
        } else {
            blackTiles.add(flip)
        }
    }

    for (i in 1..100) {
        blackTiles = tick(blackTiles).toMutableSet()
        println("Day $i: ${blackTiles.size}")
    }
}
