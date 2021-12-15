import java.io.File
import kotlin.math.abs

data class Pos(val x: Int, val y: Int)

class Node(val pos: Pos, val cost: Int, val neighbors: MutableList<Node>) {
    override fun toString() = "Node(pos=${pos.x}x${pos.y}, cost=$cost, neighbors=${neighbors.size})"
}

class AStarInfo(val node: Node, var parent: Node?, var distanceFromStart: Int, var heuristic: Int) {
    val score: Int
        get() = distanceFromStart + heuristic

    override fun toString(): String {
        return "distanceFromStart = $distanceFromStart, heuristic = $heuristic, score = $score"
    }
}

fun manhattan(a: Pos, b: Pos) = abs(a.x - b.x) + abs(a.y - b.y)

fun main() {
    val grid = File("input.txt")
        .readLines()
        .flatMapIndexed { y, it ->
            it
                .toCharArray()
                .mapIndexed { x, tile -> Node(Pos(x, y), tile.digitToInt(), mutableListOf()) }
        }

    grid.forEach { node ->
        val neighbors = listOf(
            Pos(node.pos.x - 1, node.pos.y),
            Pos(node.pos.x + 1, node.pos.y),
            Pos(node.pos.x, node.pos.y - 1),
            Pos(node.pos.x, node.pos.y + 1),
        ).mapNotNull { grid.find { n -> n.pos == it } }

        node.neighbors.addAll(neighbors)
    }

    val start = grid.find { it.pos == Pos(0, 0) }!!
    val end = grid.find { it.pos == Pos(99, 99) }!!

    val open = mutableSetOf(
        AStarInfo(start, null, 0, manhattan(start.pos, end.pos))
    )

    val closed = mutableSetOf<AStarInfo>()

    while (true) {
        val current = open.minByOrNull { it.score }!!
        open.remove(current)
        closed.add(current)

        if (current.node == end) {
            break
        }

        for (n in current.node.neighbors) {
            if (closed.any { it.node == n }) {
                continue
            }

            var neighbor = open.find { it.node == n }
            if (neighbor == null) {
                neighbor = AStarInfo(n, current.node, current.distanceFromStart + n.cost, manhattan(n.pos, end.pos))
                open.add(neighbor)
            }

            if (neighbor.distanceFromStart <= current.distanceFromStart + n.cost) {
                continue
            }

            neighbor.distanceFromStart = current.distanceFromStart + n.cost
        }
    }

    var result = mutableListOf(end)
    while (result.last() != start) {
        result.add(closed.find { it.node == result.last() }!!.parent!!)
    }

    result = result.reversed().subList(1, result.size).toMutableList()

    println("Final path:")
    println(result.joinToString("\n") { "${it.pos}" })

    println()
    println(result.sumOf { it.cost })
}
