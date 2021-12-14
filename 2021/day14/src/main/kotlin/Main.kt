import java.io.File

fun main() {
    val lines = File("input.txt").readLines()

    // Tracks how often each element occurs in polymer
    val elements = lines
        .first()
        .toCharArray()
        .groupBy { it }
        .mapValues { it.value.size.toLong() }
        .toMutableMap()

    // How often each pair of elements occurs in polymer
    val pairs = lines
        .first()
        .windowed(2)
        .groupBy { it }
        .mapValues { it.value.size.toLong() }

    val rules = lines
        .takeLastWhile { it.isNotEmpty() }
        .map { it.split(" -> ") }
        .associate { it.first() to it.last()[0] }

    (1..40).fold(pairs) { acc, _ ->
        val result = mutableMapOf<String, Long>()

        for ((pair, count) in acc) {
            val created = rules[pair]

            if (created == null) {
                result[pair] = count
                break
            }

            val left = "${pair.first()}$created"
            val right = "$created${pair.last()}"

            result[left] = (result[left] ?: 0) + count
            result[right] = (result[right] ?: 0) + count

            elements[created] = (elements[created] ?: 0) + count
        }

        result
    }

    val max = elements.maxOf { it.value }
    val min = elements.minOf { it.value }
    println("$max - $min = ${max - min}")
}
