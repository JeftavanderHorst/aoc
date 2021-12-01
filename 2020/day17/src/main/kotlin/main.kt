data class Vec4(val x: Int, val y: Int, val z: Int, val w: Int)

fun parseInput(input: String): HashSet<Vec4> {
    val result = HashSet<Vec4>()

    val lines = input.split('\n').withIndex()
    for ((y, line) in lines) {
        val cells = line.toCharArray().withIndex()
        for ((x, cell) in cells) {
            if (cell == '#') {
                result.add(Vec4(x, y, 0, 0))
            }
        }
    }

    return result
}

fun neighbours(pos: Vec4) = sequence {
    for (x in -1..1) {
        for (y in -1..1) {
            for (z in -1..1) {
                for (w in -1..1) {
                    if (!(x == 0 && y == 0 && z == 0 && w == 0)) {
                        yield(Vec4(pos.x + x, pos.y + y, pos.z + z, pos.w + w))
                    }
                }
            }
        }
    }
}

fun countNeighbours(pos: Vec4, cubes: HashSet<Vec4>): Int {
    return neighbours(pos).count { neighbour -> cubes.contains(neighbour) }
}

fun tick(start: HashSet<Vec4>): HashSet<Vec4> {
    val positionsToRecompute: HashSet<Vec4> = hashSetOf()
    for (pos in start) {
        positionsToRecompute.add(pos)
        positionsToRecompute.addAll(neighbours(pos))
    }

    val result: HashSet<Vec4> = hashSetOf()
    for (pos in positionsToRecompute) {
        val numberOfNeighbours = countNeighbours(pos, start);
        if (start.contains(pos) && numberOfNeighbours == 2 || numberOfNeighbours == 3) {
            result.add(pos)
        }
    }

    return result
}

fun main() {
    var world = parseInput("""
        ...#..#.
        #..#...#
        .....###
        ##....##
        ......##
        ........
        .#......
        ##...#..
    """.trimIndent())

    for (i in 0..5) {
        world = tick(world)
    }

    println(world.size)
}
