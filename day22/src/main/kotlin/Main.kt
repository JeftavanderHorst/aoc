import java.io.File
import kotlin.math.max

class Deck(val cards: MutableList<Int>) {
    val score: Int
        get() = cards
            .reversed()
            .withIndex()
            .fold(0) { acc, indexedValue -> acc + (indexedValue.index + 1) * indexedValue.value }

    fun copy(count: Int): Deck {
        return Deck(ArrayList(cards.take(count)))
    }
}

fun play(a: Deck, b: Deck): Char {
    val playedGames = mutableSetOf<String>()

    while (a.cards.isNotEmpty() && b.cards.isNotEmpty()) {
        val hash = "${a.cards}-${b.cards}"
        if (playedGames.contains(hash)) {
            return 'a'
        }

        playedGames.add(hash)

        val playedA = a.cards.removeFirst()
        val playedB = b.cards.removeFirst()

        if (a.cards.size >= playedA && b.cards.size >= playedB) {
            val winner = play(a.copy(playedA), b.copy(playedB))

            if (winner == 'a') {
                a.cards.add(playedA)
                a.cards.add(playedB)
            } else if (winner == 'b') {
                b.cards.add(playedB)
                b.cards.add(playedA)
            }
        } else if (playedA > playedB) {
            a.cards.add(playedA)
            a.cards.add(playedB)
        } else {
            b.cards.add(playedB)
            b.cards.add(playedA)
        }
    }

    return if (a.cards.isEmpty()) {
        'b'
    } else {
        'a'
    }
}

fun main() {
    val lines = File("input.txt")
        .readLines()
        .map { line -> line.split(',').map { card -> card.toInt() } }

    val a = Deck(lines[0].toMutableList())
    val b = Deck(lines[1].toMutableList())

    play(a, b)

    println("Winning score: ${max(a.score, b.score)}")
}
