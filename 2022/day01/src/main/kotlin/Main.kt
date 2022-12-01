package main.kotlin

import java.io.File

fun main() {
    print(
            File("input.txt")
                    .readText()
                    .split("\n\n")
                    .map { elf -> elf.lines().sumOf { it.toInt() } }
                    .sortedDescending()
                    .take(3)
                    .sum()
    )
}
