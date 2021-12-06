import java.io.File

// Only used to avoid double counting values when evaluating final score
enum class Orientation { Hor, Vert }

class RowOrColumn(val board: Int, val numbers: MutableSet<Int>, val orientation: Orientation) {
    override fun toString(): String {
        return "$orientation $board $numbers"
    }
}

fun parseBoard(board: Int, string: String): List<RowOrColumn> {
    val result = mutableListOf<RowOrColumn>()

    val numbers = string
        .split(' ', '\n')
        .filter { it.isNotEmpty() }
        .map { it.toInt() }

    for (row in 0..4) {
        // Horizontal
        result.add(
            RowOrColumn(
                board,
                mutableSetOf(
                    numbers[row * 5],
                    numbers[row * 5 + 1],
                    numbers[row * 5 + 2],
                    numbers[row * 5 + 3],
                    numbers[row * 5 + 4],
                ),
                Orientation.Hor
            )
        )

        // Vertical
        result.add(
            RowOrColumn(
                board,
                mutableSetOf(
                    numbers[row],
                    numbers[row + 5],
                    numbers[row + 10],
                    numbers[row + 15],
                    numbers[row + 20],
                ),
                Orientation.Vert
            )
        )
    }

    return result
}

fun play(sets: List<RowOrColumn>, inputs: List<Int>) {
    for (input in inputs) {
        for (s in sets) {
            s.numbers.remove(input)
            if (s.numbers.isEmpty()) {
                println("Board ${s.board} wins")

                val remainingValue = sets
                    .filter { it.board == s.board }
                    .filter { it.orientation == Orientation.Hor }
                    .flatMap { it.numbers.filter { num -> num != input } }
                    .sum()

                println("Score: $remainingValue * $input = ${remainingValue * input}")

                return
            }
        }
    }
}

fun main() {
    val lines = File("input.txt")
        .readText()
        .split("\n\n")

    val inputs = lines
        .first()
        .split(',')
        .map { it.toInt() }

    val sets: List<RowOrColumn> = lines
        .drop(1)
        .withIndex()
        .flatMap { parseBoard(it.index, it.value) }

    play(sets, inputs)
}
