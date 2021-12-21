import java.io.File

fun main() {
    val players = File("input.txt")
        .readLines()
        .map { it.split(" ").last().toInt() }
        .toMutableList()
    val scores = mutableListOf(0, 0)

    var die = 0
    var turn = 0

    var rolls = 0
    while (true) {
        val roll1 = ((die++) % 100) + 1
        val roll2 = ((die++) % 100) + 1
        val roll3 = ((die++) % 100) + 1
        rolls += 3

        val move = roll1 + roll2 + roll3

        players[turn] = ((players[turn] + move - 1) % 10) + 1
        scores[turn] += players[turn]

        println("Player ${turn + 1} moves $roll1+$roll2+$roll3 = $move to space ${players[turn]} for a total score of ${scores[turn]}")

        if (scores[turn] >= 1000) {
            break
        }

        turn = when (turn) {
            1 -> 0
            0 -> 1
            else -> throw Exception()
        }
    }

    println("Player ${turn + 1} wins after $rolls rolls")
    val losingScore = when (turn) {
        0 -> scores[1]
        1 -> scores[0]
        else -> throw Exception()
    }
    println("Final score: $losingScore x $rolls = ${losingScore * rolls}")
}
