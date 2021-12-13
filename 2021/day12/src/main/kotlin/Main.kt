import java.io.File

data class Edge(val from: String, val to: String)

fun isUppercase(string: String) = string.uppercase() == string

fun mayBeVisited(node: String, path: List<String>): Boolean {
    if (node == "start" || node == "end") {
        return !path.contains(node)
    }

    if (isUppercase(node)) {
        return true
    }

    // If the set of visited small caves is smaller than the list of those caves, at least one has been visited twice
    val smallCaveVisitedTwice = path.filter { !isUppercase(it) }.toSet().size != path.filter { !isUppercase(it) }.size

    if (path.contains(node) && smallCaveVisitedTwice) {
        return false
    }

    return true
}

fun paths(edges: List<Edge>) = iterator {
    fun traverse(i: Int, path: List<String>): Iterator<List<String>> = iterator {
        if (path.last() == "end") {
            yield(path)
        }

        val possible = edges.filter { edge -> edge.from == path.last() && mayBeVisited(edge.to, path) }

        for (edge in possible) {
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

    println("Found ${result.size} paths")
}
