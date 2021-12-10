import java.io.File
import java.util.*


class Parser(private val string: String) {
    private var index: Int = 0
    private val stack = ArrayDeque(emptyList<Char>())

    fun getScore(): Int {
        while (index < string.length) {
            when (string[index++]) {
                '(' -> stack.addLast(')')
                '[' -> stack.addLast(']')
                '{' -> stack.addLast('}')
                '<' -> stack.addLast('>')
                ')' -> if (stack.last() == ')') stack.removeLast() else return 3
                ']' -> if (stack.last() == ']') stack.removeLast() else return 57
                '}' -> if (stack.last() == '}') stack.removeLast() else return 1197
                '>' -> if (stack.last() == '>') stack.removeLast() else return 25137
                else -> throw Exception()
            }
        }

        return 0
    }
}

fun main() {
    println(
        File("input.txt")
            .readLines()
            .sumOf { Parser(it).getScore() }
    )
}
