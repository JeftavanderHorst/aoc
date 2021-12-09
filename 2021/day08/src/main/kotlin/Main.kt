import java.io.File

fun decode(signals: List<Set<Char>>): Map<String, Int> {
    val wiresFor = mutableMapOf<Int, Set<Char>>()

    wiresFor[1] = signals.first { it.size == 2 }
    wiresFor[4] = signals.first { it.size == 4 }
    wiresFor[7] = signals.first { it.size == 3 }
    wiresFor[8] = signals.first { it.size == 7 }
    wiresFor[3] = signals.first { it.size == 5 && wiresFor[7]!!.all { c -> it.contains(c) } }
    wiresFor[9] = signals.first { it.size == 6 && wiresFor[4]!!.all { c -> it.contains(c) } }
    wiresFor[0] = signals.first {
        it.size == 6 && wiresFor[7]!!.all { c -> it.contains(c) } && !wiresFor[4]!!.all { c ->
            it.contains(c)
        }
    }
    wiresFor[6] = signals.first { it.size == 6 && !wiresFor[1]!!.all { c -> it.contains(c) } }
    wiresFor[5] = signals.first { it.size == 5 && it.all { c -> wiresFor[6]!!.contains(c) } }
    wiresFor[2] = signals.first {
        it.size == 5 && !wiresFor[7]!!.all { c -> it.contains(c) } && !it.all { c ->
            wiresFor[6]!!.contains(c)
        }
    }

    val meanings = mutableMapOf<String, Int>()
    for (w in wiresFor) {
        meanings[w.value.toList().sorted().joinToString("")] = w.key
    }

    return meanings
}

fun main() {
    val lines = File("input.txt")
        .readLines()
        .map { it.split(" | ") }

    var sum = 0
    for ((signals, outputs) in lines) {
        val meanings = decode(signals.split(" ").map { it.toCharArray().toSet() })

        sum += outputs
            .split(" ")
            .map { it.toCharArray().sorted().joinToString("") }
            .map { meanings[it] }
            .joinToString("")
            .toInt()
    }

    println(sum)
}
