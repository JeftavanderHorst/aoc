package main.kotlin

import java.io.File

fun main() {
    print(
            File("input.txt")
                    .readText()
                    .split("\n\n")
                    .maxOfOrNull { elf -> elf.lines().sumOf { it.toInt() } }
    )
}
