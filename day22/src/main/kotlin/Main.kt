import java.io.File
import kotlin.math.max

class Deck(val cards: MutableList<Int>) {
    val score: Int
        get() = cards
            .reversed()
            .withIndex()
            .fold(0) { acc, indexedValue -> acc + (indexedValue.index + 1) * indexedValue.value }
}

fun main() {
    val lines = File("input.txt")
        .readLines()
        .map { line -> line.split(',').map { card -> card.toInt() } }

    val a = Deck(lines[0].toMutableList())
    val b = Deck(lines[1].toMutableList())

    while (a.cards.isNotEmpty() && b.cards.isNotEmpty()) {
        val playedA = a.cards.removeFirst()
        val playedB = b.cards.removeFirst()

        if (playedA > playedB) {
            a.cards.add(playedA)
            a.cards.add(playedB)
        } else {
            b.cards.add(playedB)
            b.cards.add(playedA)
        }
    }

    println("Winning score: ${max(a.score, b.score)}")
}
