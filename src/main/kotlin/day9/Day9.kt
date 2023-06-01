package day9

import kotlin.math.abs

fun solve() {
    // part 1
    assert(testInput1.moveHistorySize(2) == 13)
    assert(realInput.moveHistorySize(2) == 5907)

    // part 2
    assert(testInput1.moveHistorySize(10) == 1)
    assert(testInput2.moveHistorySize(10) == 36)
    assert(realInput.moveHistorySize(10) == 2303)
}

private fun String.moveHistorySize(knotCount: Int): Int {
    val rope = Rope(knotCount)
    moves().forEach { rope.apply(it) }
    return rope.knots.last().moveHistory.size
}

private data class Move(val direction: Char, val distance: Int)
private data class Location(val x: Int, val y: Int) {
    override fun toString(): String = "($x, $y)"
}

private class Knot(var x: Int, var y: Int) {
    val moveHistory = mutableSetOf<Location>()

    init {
        moveHistory.add(Location(x, y))
    }

    fun setNewLocation(location: Location) {
        x = location.x
        y = location.y
        moveHistory.add(location)
    }

    fun isTouching(other: Knot): Boolean =
        abs(x - other.x) <= 1 && abs(y - other.y) <= 1
}

private class Rope(knotCount: Int) {
    val knots = List(knotCount) { Knot(0, 0) }

    fun apply(move: Move) {
        repeat(move.distance) {
            moveHeadByOneStep(move.direction)
            for (i in 1 until knots.size) {
                repositionKnot(i)
            }
        }
    }

    fun moveHeadByOneStep(direction: Char) {
        val head = knots.first()
        val location = when (direction) {
            'R' -> Location(head.x + 1, head.y)
            'L' -> Location(head.x - 1, head.y)
            'U' -> Location(head.x, head.y + 1)
            'D' -> Location(head.x, head.y - 1)
            else -> Location(head.x, head.y)
        }
        head.setNewLocation(location)
    }

    // Reposition the specified knot to follow is head (i.e. the knot before it)
    fun repositionKnot(index: Int) {
        val head = knots[index - 1]
        val tail = knots[index]
        if (tail.isTouching(head)) return

        // 1. if the head and tail share the same x or y values, the new location is average of the two.
        // 2. if they are two steps apart on both axes, then also the new location is average of the two.
        // 3 & 4 if they are apart by one step on one axis and by two on the other, move diagonally so that
        // the distance is reduced by on step on both axes.
        val newLocation =
            if (tail.x == head.x || tail.y == head.y) {
                Location((tail.x + head.x) / 2, (tail.y + head.y) / 2)
            } else if (abs(tail.x - head.x) == 2 && abs(tail.y - head.y) == 2) {
                Location((tail.x + head.x) / 2, (tail.y + head.y) / 2)
            } else if (abs(tail.x - head.x) == 2 && abs(tail.y - head.y) == 1) {
                Location((tail.x + head.x) / 2, head.y)
            } else {
                Location(head.x, (tail.y + head.y) / 2)
            }

        assert(Knot(newLocation.x, newLocation.y).isTouching(head)) {
            "Could not move the tail to a location that is touching the head. Should never have reached here."
        }
        tail.setNewLocation(newLocation)
    }
}

private val regexMove = Regex("""(\w) (\d+)""")
private fun String.moves(): List<Move> =
    lineSequence()
        .mapNotNull {
            regexMove.matchEntire(it)
                ?.destructured
                ?.let { (dir, dis) -> Move(dir.first(), dis.toInt()) }
        }
        .toList()

private val testInput1 = """
R 4
U 4
L 3
D 1
R 4
D 1
L 5
R 2
""".trimIndent()

private val testInput2 = """
R 5
U 8
L 8
D 3
R 17
D 10
L 25
U 20
""".trimIndent()

private val realInput = """
U 1
L 1
R 2
L 1
D 2
R 1
U 2
R 1
U 1
R 2
D 2
L 1
R 1
D 2
R 1
L 1
R 1
D 1
U 1
R 1
L 1
R 2
U 1
R 1
D 2
L 2
U 1
R 1
D 1
L 1
R 1
L 2
D 1
U 1
D 2
R 2
D 2
R 2
D 2
R 2
U 1
L 2
R 2
D 2
L 2
U 2
R 1
L 1
U 1
R 1
D 1
R 1
L 1
R 1
D 2
L 2
U 1
R 1
L 1
R 1
U 2
D 2
U 2
L 1
U 2
L 1
R 2
U 2
D 1
L 1
R 1
L 2
U 1
D 2
R 2
U 2
R 1
L 1
U 2
R 2
L 1
R 2
D 1
U 2
D 2
R 2
L 1
R 1
U 2
D 2
U 2
D 1
U 1
R 2
D 1
R 1
L 1
D 2
L 2
U 2
R 2
U 1
R 2
U 1
D 1
R 2
U 1
R 1
U 2
R 2
D 2
L 2
D 3
R 2
U 2
R 2
U 2
L 3
U 1
L 1
R 3
D 2
R 2
U 1
L 2
D 2
L 3
U 3
D 2
R 3
U 3
R 2
D 2
U 3
L 1
U 2
R 2
L 1
D 3
U 1
R 3
L 2
D 1
U 2
L 3
R 3
U 3
L 1
D 1
R 3
D 3
R 1
U 2
R 3
L 2
R 1
U 2
L 3
D 1
R 3
U 2
D 2
R 2
D 3
L 3
D 1
L 2
D 1
R 2
L 1
D 2
U 1
D 3
U 1
R 1
U 1
R 3
L 3
U 2
D 3
R 3
U 1
L 1
R 1
U 3
L 2
U 2
D 3
L 2
D 1
R 2
D 2
L 1
R 2
U 1
D 3
R 3
D 2
R 3
L 3
R 3
D 1
U 1
R 1
U 2
D 2
L 2
D 3
R 2
D 3
U 2
L 2
R 1
U 3
L 1
D 1
U 1
D 2
U 1
L 3
R 2
D 1
L 3
U 1
R 3
D 2
U 4
D 1
L 4
R 2
L 2
R 2
U 4
R 1
U 3
L 2
U 2
R 3
L 3
D 3
R 4
D 4
R 3
D 1
L 4
D 4
U 3
D 3
L 4
R 4
L 2
R 3
L 4
D 3
L 3
U 1
R 2
U 1
L 3
U 4
D 1
L 2
U 2
L 1
D 1
R 2
L 4
R 3
U 4
R 1
D 1
R 1
U 3
D 4
R 1
U 4
R 2
D 2
U 2
R 2
U 4
L 1
D 3
L 4
D 2
L 2
D 2
U 4
L 4
R 1
D 1
R 3
D 3
L 4
R 3
D 2
R 1
D 1
R 1
U 1
D 1
L 1
D 1
R 2
L 3
D 1
R 1
L 4
D 4
R 4
L 3
D 4
U 2
L 1
D 3
U 2
D 1
R 2
D 2
L 2
U 1
D 4
R 3
U 4
D 1
R 3
U 1
D 1
U 1
R 1
D 1
R 3
U 4
R 4
U 2
L 1
U 1
L 3
R 5
D 2
U 1
R 3
D 3
R 1
D 1
L 5
R 1
D 1
R 2
U 3
R 2
L 4
U 3
D 4
U 1
R 3
D 1
R 2
L 3
R 3
U 2
L 5
U 3
R 4
D 4
R 2
D 3
R 2
U 4
L 5
R 2
L 3
U 4
L 5
R 3
D 3
U 4
R 2
U 5
R 2
U 5
D 1
L 2
D 2
U 3
R 3
L 3
U 3
D 5
R 4
D 4
L 4
D 5
R 1
U 4
L 2
R 1
D 3
L 2
R 2
L 3
R 2
L 2
D 3
U 5
D 5
L 3
R 5
U 3
L 3
D 5
L 4
D 1
L 2
D 4
U 4
D 1
R 1
L 4
R 3
U 2
L 3
U 4
D 4
R 2
D 4
U 2
D 4
U 2
L 4
D 5
L 4
R 1
L 2
R 3
D 2
R 5
L 1
U 5
R 1
D 5
L 1
D 2
U 5
D 2
U 1
D 6
U 2
L 5
D 2
R 1
D 1
R 1
D 5
U 1
D 5
R 4
L 3
R 4
U 1
D 5
U 5
L 4
U 5
L 4
U 5
R 2
L 6
D 4
L 1
U 1
R 6
D 4
L 1
D 2
R 2
U 5
R 5
U 4
L 5
R 4
D 4
U 4
D 1
R 2
L 5
U 3
R 1
L 3
U 6
R 5
L 2
D 4
R 4
U 5
R 2
U 6
L 4
U 6
R 2
L 6
D 5
R 6
U 1
L 3
U 4
R 2
L 5
D 4
L 6
U 1
D 1
R 2
D 5
L 1
R 5
L 3
U 5
D 4
U 5
D 4
U 2
R 4
L 3
D 4
L 5
R 6
U 6
D 5
U 2
R 4
D 5
L 5
R 3
L 3
D 3
U 6
L 1
D 3
R 2
L 3
R 2
L 6
D 1
L 6
D 4
L 6
R 4
D 5
L 3
U 6
R 5
U 4
D 1
L 5
U 1
D 2
U 2
D 5
R 7
L 3
U 5
R 4
L 5
U 1
R 2
D 6
L 6
D 2
L 1
D 6
U 2
D 2
U 2
D 6
L 1
R 6
U 7
D 2
L 3
R 3
U 6
D 3
R 4
U 3
L 7
D 3
U 6
D 2
U 1
R 4
L 1
R 3
U 5
D 6
R 2
L 4
D 5
R 6
L 5
D 1
R 3
D 2
L 7
U 1
R 5
D 6
U 6
D 4
U 6
D 2
L 2
U 7
D 1
R 3
U 3
R 1
L 6
D 7
L 4
R 1
D 7
L 2
U 6
D 2
R 6
D 7
U 7
R 5
L 1
U 3
R 5
D 4
U 2
D 2
L 6
D 2
U 7
R 4
L 7
U 4
D 6
U 2
D 6
L 3
U 5
R 5
D 4
R 1
D 6
U 6
R 3
L 2
U 1
R 7
D 4
L 2
D 4
L 5
U 3
R 6
L 3
D 7
R 7
L 1
U 4
L 6
R 7
U 3
R 1
D 8
L 3
D 3
U 3
R 2
L 6
U 4
L 2
D 7
L 5
D 1
R 3
U 2
L 6
R 2
U 1
D 6
U 4
D 1
U 8
D 7
R 3
L 3
R 1
D 1
R 5
L 5
R 6
U 4
R 4
U 3
R 4
L 6
R 4
U 6
L 5
U 8
L 1
D 1
L 4
U 8
R 2
U 7
L 4
U 7
L 7
D 5
U 1
R 7
U 7
D 6
R 5
U 5
D 2
R 8
L 1
D 3
L 5
D 5
R 1
U 1
R 4
D 3
R 1
U 4
R 8
D 3
L 4
R 7
L 4
R 7
L 7
R 1
L 1
D 2
L 6
R 7
D 5
U 7
L 8
U 1
L 8
U 2
R 6
L 4
U 1
R 1
U 3
L 6
D 7
L 3
D 2
R 4
U 5
D 1
U 2
D 6
L 6
U 4
D 1
L 2
R 4
U 2
L 1
R 5
L 3
R 5
U 7
R 4
L 4
R 2
D 4
L 1
R 2
D 1
L 2
R 2
U 2
R 5
L 7
U 3
R 1
D 5
U 7
D 8
R 7
D 5
U 2
L 5
D 4
L 2
D 8
U 7
D 3
U 4
L 9
D 2
U 7
R 7
D 9
U 3
R 2
L 7
U 5
L 9
D 5
R 6
D 5
R 1
D 1
R 5
D 3
U 2
R 5
D 8
U 4
R 9
U 5
L 2
U 2
R 5
D 9
U 2
D 3
R 4
D 5
U 7
L 2
U 8
R 7
L 1
U 4
D 6
R 8
U 3
D 4
U 1
R 5
U 8
D 9
U 5
D 9
R 7
D 3
L 1
D 4
R 8
D 8
U 5
D 7
L 5
U 1
D 6
L 7
D 1
R 1
U 2
L 3
R 4
L 1
D 7
L 3
U 9
R 1
U 9
L 7
R 1
U 5
L 4
U 8
D 9
U 3
D 9
R 5
U 8
L 9
U 9
D 7
R 7
L 2
U 9
R 7
U 10
D 7
L 1
D 2
R 4
D 9
L 4
D 7
R 9
U 1
D 4
U 8
L 9
R 9
D 3
R 2
D 7
U 2
D 7
U 1
R 3
D 1
U 10
D 5
U 6
D 1
R 5
D 6
U 1
R 6
U 2
D 5
U 8
L 9
D 1
U 9
D 6
L 10
R 4
D 1
L 6
R 1
L 3
D 4
R 9
D 7
R 4
D 8
R 8
L 5
U 7
R 3
U 5
D 4
L 5
D 7
L 10
U 3
D 9
R 7
U 4
D 3
R 7
U 5
D 10
R 1
L 7
U 7
R 5
U 4
L 7
D 6
R 1
D 1
R 4
L 3
R 4
D 3
L 7
D 7
L 6
R 4
L 1
D 2
U 4
D 8
L 8
R 1
U 4
D 1
U 2
L 1
R 4
U 7
D 9
U 4
D 10
U 5
R 3
D 6
U 7
D 6
L 7
R 7
L 7
R 6
D 4
U 4
L 7
U 9
L 6
D 2
L 2
U 10
L 4
U 3
D 3
L 5
U 10
D 10
R 8
D 2
L 6
D 9
R 7
L 8
R 3
U 1
D 4
U 2
R 4
U 5
D 8
U 6
L 4
U 9
R 5
U 8
L 11
D 11
U 6
D 4
U 3
R 7
L 8
R 5
D 5
U 5
R 1
U 10
L 4
D 6
U 11
R 3
D 10
U 10
L 7
R 11
U 9
L 10
U 10
L 11
D 2
L 9
D 3
L 11
D 2
U 1
D 5
L 6
U 3
D 10
R 8
U 10
R 9
L 1
D 6
U 6
D 6
L 8
R 3
U 1
D 2
L 6
R 10
U 7
D 2
L 5
D 10
U 5
D 9
R 4
L 9
D 6
U 9
R 5
L 9
U 1
D 2
R 2
L 5
R 4
D 8
U 8
D 11
L 4
D 3
U 1
L 9
R 8
L 10
R 8
L 10
U 10
D 5
R 11
U 2
R 8
U 1
L 6
U 5
D 2
U 2
R 3
L 4
R 1
D 6
L 6
U 5
R 1
L 6
D 1
R 5
L 2
R 11
D 5
L 6
R 1
D 11
U 9
D 12
U 8
R 7
L 6
R 9
U 7
D 2
R 5
D 12
L 10
D 7
L 5
U 7
D 9
R 7
U 8
D 3
U 8
D 5
U 1
L 10
U 7
L 2
R 5
L 2
U 1
L 8
R 2
D 8
R 9
D 9
R 11
D 4
R 3
U 2
D 9
R 12
U 11
D 8
R 6
D 9
U 3
L 5
R 1
U 4
R 4
U 10
R 11
U 5
R 9
U 1
R 5
D 3
R 8
U 3
R 6
U 4
R 3
D 2
L 2
U 7
L 2
U 12
D 10
L 7
U 6
R 7
U 1
L 11
U 12
R 12
L 12
D 10
R 9
U 12
L 6
U 2
L 7
U 11
R 7
U 6
L 3
U 3
D 1
U 11
R 1
D 4
R 10
L 10
R 6
D 11
R 1
D 5
L 1
R 1
L 4
R 7
U 12
R 6
L 10
D 12
L 4
R 8
U 5
L 7
R 10
D 13
U 7
D 1
R 1
D 9
R 3
D 1
L 4
D 3
R 5
U 9
R 11
L 4
R 1
D 11
L 2
R 4
D 7
R 3
U 2
D 6
R 2
L 13
R 5
L 4
R 4
U 13
R 3
U 4
R 3
D 1
L 3
U 6
L 8
U 5
R 4
D 10
L 3
U 10
R 11
D 11
U 2
D 2
R 4
U 2
D 1
R 10
U 5
L 9
U 11
R 10
D 4
L 1
U 5
R 4
U 6
R 9
D 12
L 10
R 5
L 5
D 9
U 2
R 7
U 2
R 5
L 8
D 5
R 1
D 5
R 8
U 11
R 7
L 13
U 10
D 3
U 1
D 2
L 1
D 12
L 9
D 12
R 5
U 8
R 6
L 6
R 5
U 7
L 10
D 5
U 11
D 4
U 8
R 7
U 8
R 2
L 10
D 5
L 10
D 3
U 13
D 11
U 3
R 10
U 9
D 7
U 6
R 1
U 9
R 11
D 7
R 6
U 9
R 6
U 12
D 6
R 13
L 14
U 10
R 9
L 13
R 7
D 9
U 12
L 8
D 5
R 3
U 5
R 10
L 4
D 5
R 4
D 2
L 12
R 8
D 9
R 5
D 7
U 5
D 11
L 13
D 12
R 2
U 5
L 1
U 2
L 6
U 9
D 10
L 3
D 8
R 12
U 9
R 9
U 9
R 8
U 6
L 10
U 5
R 9
D 2
U 12
D 3
R 12
L 10
D 5
L 7
D 9
L 5
R 7
D 8
U 10
L 11
R 9
L 13
R 8
U 9
D 8
U 9
R 10
L 4
U 1
D 10
L 1
D 4
L 1
U 13
R 8
L 3
D 5
R 5
U 4
R 5
U 6
L 14
R 5
U 8
L 7
D 7
R 4
U 4
L 14
U 7
R 14
D 3
U 10
L 7
R 5
L 5
R 15
L 3
D 15
L 6
R 3
L 10
U 7
L 2
R 7
L 1
R 8
U 12
D 6
U 11
D 12
U 9
R 2
D 14
L 4
U 9
D 15
R 8
D 9
L 9
D 14
U 5
R 14
D 15
R 6
L 3
U 13
R 13
U 5
D 14
L 2
D 12
U 6
R 5
U 5
L 6
R 5
D 12
U 9
L 7
D 15
R 5
U 7
D 1
R 2
L 11
U 8
R 3
D 3
U 6
D 15
U 2
D 11
R 6
U 9
D 7
U 3
L 13
U 3
L 15
R 10
L 6
D 15
L 3
U 5
R 13
D 13
L 5
R 8
D 2
R 8
L 9
R 7
L 5
U 3
D 11
R 13
U 15
D 5
R 4
U 11
R 14
L 1
U 6
L 13
R 14
L 12
D 5
R 3
D 4
R 5
U 8
R 11
D 8
U 5
R 9
D 8
R 4
U 5
L 7
D 6
U 4
L 2
D 15
U 5
R 16
L 4
R 1
D 12
L 2
R 6
D 6
R 14
U 6
L 14
R 12
D 8
R 1
U 2
R 3
D 7
R 4
L 2
U 6
L 1
U 7
L 12
R 11
D 13
U 16
R 15
D 14
U 13
D 15
R 11
U 12
R 5
D 8
R 2
L 14
R 10
L 16
U 10
D 13
L 11
U 10
R 5
D 7
L 15
D 12
L 4
R 16
U 8
R 8
L 7
U 9
R 7
U 16
R 2
U 5
R 5
D 4
L 7
D 5
L 4
U 13
D 9
R 2
D 2
U 5
L 3
D 16
R 13
L 10
R 7
L 9
R 3
L 5
D 10
L 10
R 2
L 13
R 5
L 11
R 16
U 12
R 11
D 12
R 7
L 2
U 12
D 11
U 12
L 1
R 6
U 3
L 3
D 1
R 5
U 6
L 15
R 8
D 5
L 16
D 13
R 15
D 14
R 4
U 15
R 5
L 8
D 12
U 11
D 13
L 9
D 13
L 7
U 9
R 15
U 13
D 16
R 6
L 8
R 7
U 4
R 10
U 7
D 10
U 8
D 7
L 17
R 7
L 8
D 5
L 5
U 15
D 6
L 1
D 2
R 17
L 6
D 15
R 4
L 2
R 10
D 4
R 6
D 11
U 1
L 17
D 11
U 6
D 17
R 15
L 13
D 9
R 12
U 13
R 2
L 12
D 1
R 17
U 13
D 15
U 7
R 8
D 6
R 8
D 16
U 16
D 2
U 1
R 7
U 15
L 7
R 12
U 10
D 4
R 17
D 11
R 12
D 8
L 3
R 3
D 5
R 17
L 15
U 11
R 1
L 4
R 6
D 6
U 2
L 1
D 2
U 1
D 11
L 1
R 11
D 7
U 16
L 14
U 5
R 13
D 9
U 17
D 3
R 12
U 16
R 10
L 17
D 5
U 16
D 12
L 2
R 16
L 17
U 4
R 15
D 4
L 11
U 3
R 17
D 8
R 7
D 15
R 14
D 6
R 5
L 1
D 5
L 11
D 5
U 13
L 13
U 8
D 8
L 13
D 1
L 10
U 14
R 7
L 11
D 4
R 16
D 3
L 9
R 3
D 4
L 4
U 2
R 13
D 18
U 15
D 7
L 18
U 3
D 17
U 13
R 9
L 8
R 9
U 16
R 14
D 17
L 16
R 3
U 4
D 1
R 8
D 14
L 14
U 17
L 16
R 15
D 9
U 13
R 14
U 9
D 13
R 2
L 3
D 8
U 5
R 1
L 5
R 4
U 8
L 7
U 5
R 11
D 17
L 4
U 3
R 1
U 18
D 1
U 6
R 2
D 16
U 15
D 8
L 13
R 2
U 16
L 9
D 1
U 9
R 5
L 16
D 2
L 2
R 8
D 17
R 13
U 5
R 6
U 2
L 12
R 2
D 14
R 14
L 11
R 1
L 9
D 18
L 4
U 4
D 15
L 14
R 15
U 6
D 3
R 15
L 12
U 3
D 12
U 13
L 6
U 17
D 6
L 10
R 16
L 7
U 2
D 1
R 10
U 11
R 1
U 19
D 5
L 11
D 14
L 17
U 16
L 13
R 4
L 16
D 10
L 4
D 9
R 19
L 19
R 12
L 12
D 8
L 6
D 14
U 7
D 13
U 18
D 13
U 6
D 6
R 18
U 18
D 15
L 14
R 7
D 11
R 4
L 14
R 5
D 4
L 12
D 16
R 17
U 15
L 9
U 10
R 7
D 10
L 3
U 15
L 1
D 15
U 1
D 12
L 4
D 10
U 6
R 6
L 11
D 5
R 1
D 11
U 16
L 16
R 19
U 8
R 11
D 17
L 19
R 6
U 7
R 2
D 9
R 11
D 17
U 17
D 4
R 8
D 6
U 10
R 3
L 17
D 5
R 19
U 1
L 5
U 5
D 6
U 6
L 18
R 18
L 4
R 12
D 14
L 17
R 8
L 13
R 6
L 14
D 3
R 13
L 16
D 14
U 6
D 6
""".trimIndent()