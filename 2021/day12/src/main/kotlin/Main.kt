import java.io.File

data class Edge(val from: String, val to: String)

fun isUppercase(string: String) = string.uppercase() == string

fun paths(edges: List<Edge>) = iterator<List<String>> {
    fun traverse(i: Int, path: List<String>): Iterator<List<String>> = iterator<List<String>> {
        val indent = "  ".repeat(i)
        println("${indent}Traverse called with $path")

        if (path.last() == "end") {
            println("${indent}Is full path")
            yield(path)
        }

        val possible =
            edges.filter { edge -> edge.from == path.last() && (isUppercase(edge.to) || !path.contains(edge.to)) }

        if (possible.isEmpty()) {
            println("${indent}Dead end")
        }

        for (edge in possible) {
            println("${indent}Traversing ${edge.from} -> ${edge.to}")

            yieldAll(traverse(i + 1, path + listOf(edge.to)))
        }
    }

    yieldAll(traverse(1, listOf("start")))
}

fun main() {
    val edges = File("input.txt")
        .readLines()
        .map { it.split("-") }
        .flatMap { (from, to) ->
            listOf(
                Edge(from, to),
                Edge(to, from),
            )
        }

    val result = mutableListOf<List<String>>()
    for (path in paths(edges)) {
        result.add(path)
    }

    for (path in result) {
        println("Path found: $path")
    }

    println()
    println("Found ${result.size} paths")
}
