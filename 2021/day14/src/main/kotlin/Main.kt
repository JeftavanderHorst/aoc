import java.io.File

data class Rule(val pattern: String, val created: Char)

fun main() {
    val lines = File("input.txt").readLines()

    var state = lines.first()

    val rules = lines
        .takeLastWhile { it.isNotEmpty() }
        .map { it.split(" -> ") }
        .map { Rule(it[0], it[1].first()) }

    println("Initial: $state")

    for (i in 1..10) {
        state = state
            .windowed(2, 1, true)
            .joinToString("") {
                var result = it
                for (rule in rules) {
                    if (it == rule.pattern) {
                        result = "${rule.pattern.first()}${rule.created}"
                        break
                    }
                }
                result
            }

        println("Done with step $i")
    }

    val elements = state.toCharArray().distinct().toSet()

    val counts = elements.map { element -> state.toCharArray().count { it == element } }
    val max = counts.maxOrNull()!!
    val min = counts.minOrNull()!!
    println("$max - $min = ${max - min}")
}
