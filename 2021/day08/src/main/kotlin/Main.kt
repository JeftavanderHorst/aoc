import java.io.File

fun main() {
   val lines = File("input.txt")
       .readLines()
       .map { it.split(" | ")[1] }
       .flatMap { it.split(" ") }
       .count { listOf(2, 3, 4, 7).contains(it.length) }

    println(lines)
}
