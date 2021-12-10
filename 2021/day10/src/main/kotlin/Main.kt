import java.io.File
import java.util.*


class Parser(private val string: String) {
    private var index: Int = 0
    private val stack = ArrayDeque(emptyList<Char>())

    fun getCompletion(): String? {
        while (index < string.length) {
            when (string[index++]) {
                '(' -> stack.addLast(')')
                '[' -> stack.addLast(']')
                '{' -> stack.addLast('}')
                '<' -> stack.addLast('>')
                ')' -> if (stack.last() == ')') stack.removeLast() else return null
                ']' -> if (stack.last() == ']') stack.removeLast() else return null
                '}' -> if (stack.last() == '}') stack.removeLast() else return null
                '>' -> if (stack.last() == '>') stack.removeLast() else return null
                else -> throw Exception()
            }
        }

        return stack.joinToString("").reversed()
    }
}

fun computeScore(completion: String): Long {
    var score: Long = 0
    for (c in completion.toCharArray()) {
        score *= 5
        score += when (c) {
            ')' -> 1
            ']' -> 2
            '}' -> 3
            '>' -> 4
            else -> throw Exception()
        }
    }

    return score
}

fun main() {
    val scores = File("input.txt")
        .readLines()
        .mapNotNull { Parser(it).getCompletion() }
        .map { computeScore(it) }
        .sorted()

    println(scores[scores.size / 2])
}
