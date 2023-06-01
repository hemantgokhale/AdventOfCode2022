package day5

fun solve() {
    assert(testInput.part1Answer() == "CMZ")
    assert(realInput.part1Answer() == "ZBDRNPMVH")

    assert(testInput.part2Answer() == "MCD")
    assert(realInput.part2Answer() == "WDLPFNNNB")
}

private fun String.part1Answer(): String {
    val (stacksStr, movesStr) = split("\n\n")
    val stacks = stacksStr.readStacks()
    movesStr.readMoves().forEach { it.applyOneAtATime(stacks) }
    return stacks.mapNotNull { it.lastOrNull() }.joinToString(separator = "")
}

private fun String.part2Answer(): String {
    val (stacksStr, movesStr) = split("\n\n")
    val stacks = stacksStr.readStacks()
    movesStr.readMoves().forEach { it.applyAllAtOnce(stacks) }
    return stacks.mapNotNull { it.lastOrNull() }.joinToString(separator = "")
}

/**
 * Notes:
 * 1. Each crate is represented by a Char
 * 2. A stack is modeled as a mutable list of Char. The end of the list is the top of the stack.
 * 3. The stack identifier is shifted by 1. i.e. the input identifies them ∂as 1, 2, 3, etc. In our code that maps to 0, 1, 2, etc.
 */
data class Move(val count: Int, val from: Int, val to: Int) {
    fun applyOneAtATime(stacks: List<MutableList<Char>>) {
        repeat(count) {
            stacks[to].add(stacks[from].removeLast())
        }
    }

    fun applyAllAtOnce(stacks: List<MutableList<Char>>) {
        stacks[to].addAll(stacks[from].removeLast(count))
    }
}

// Remove the last n items from a list and return them. The order of elements is unchanged.
private fun <T> MutableList<T>.removeLast(n: Int): List<T> {
    val removedItems = mutableListOf<T>()
    repeat(n) {
        removedItems.add(index = 0, removeLast())
    }
    return removedItems
}

private fun String.readStacks(): List<MutableList<Char>> {
    val lines = lines()
    val stacks = MutableList(lines.first().length / 3) { mutableListOf<Char>() } // each list is a stack
    lineSequence().forEach { line ->
        if (line.startsWith(" 1")) return@forEach // ignore the last line containing stack numbers.
        for (i in 1 until line.length step 4) {
            val crate = line[i]
            if (crate != ' ') stacks[i / 4].add(0, crate)
        }
    }
    return stacks
}

private val regex = Regex("""move (\d+) from (\d+) to (\d+)""")
private fun String.readMoves(): List<Move> =
    lineSequence()
        .mapNotNull { line ->
            regex.matchEntire(line)
                ?.destructured
                ?.let { (count, from, to) -> Move(count.toInt(), from.toInt() - 1, to.toInt() - 1) }
        }
        .toList()


private val testInput = """
    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3 

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2
""".trimIndent()

private val realInput = """
[T]     [Q]             [S]        
[R]     [M]             [L] [V] [G]
[D] [V] [V]             [Q] [N] [C]
[H] [T] [S] [C]         [V] [D] [Z]
[Q] [J] [D] [M]     [Z] [C] [M] [F]
[N] [B] [H] [N] [B] [W] [N] [J] [M]
[P] [G] [R] [Z] [Z] [C] [Z] [G] [P]
[B] [W] [N] [P] [D] [V] [G] [L] [T]
 1   2   3   4   5   6   7   8   9 

move 5 from 4 to 9
move 3 from 5 to 1
move 12 from 9 to 6
move 1 from 6 to 9
move 3 from 2 to 8
move 6 from 3 to 9
move 2 from 2 to 9
move 2 from 3 to 5
move 9 from 8 to 1
move 1 from 6 to 9
move 1 from 8 to 3
move 14 from 1 to 2
move 8 from 2 to 6
move 2 from 2 to 7
move 2 from 5 to 8
move 5 from 2 to 6
move 9 from 7 to 8
move 1 from 9 to 8
move 5 from 6 to 9
move 1 from 3 to 8
move 1 from 7 to 5
move 1 from 1 to 5
move 4 from 1 to 7
move 15 from 6 to 1
move 4 from 7 to 6
move 2 from 5 to 7
move 9 from 8 to 7
move 13 from 1 to 3
move 8 from 6 to 9
move 1 from 6 to 8
move 1 from 7 to 5
move 2 from 1 to 3
move 4 from 7 to 1
move 13 from 3 to 6
move 2 from 1 to 3
move 1 from 5 to 8
move 2 from 3 to 4
move 5 from 7 to 1
move 4 from 1 to 9
move 2 from 4 to 5
move 4 from 6 to 2
move 3 from 2 to 5
move 6 from 8 to 1
move 7 from 6 to 7
move 1 from 3 to 5
move 1 from 2 to 4
move 8 from 1 to 8
move 4 from 6 to 2
move 3 from 5 to 3
move 1 from 4 to 3
move 2 from 1 to 3
move 8 from 8 to 5
move 2 from 3 to 8
move 4 from 5 to 3
move 1 from 9 to 2
move 1 from 8 to 3
move 1 from 2 to 1
move 15 from 9 to 3
move 6 from 7 to 5
move 1 from 7 to 3
move 2 from 2 to 8
move 6 from 9 to 4
move 22 from 3 to 6
move 3 from 8 to 6
move 1 from 1 to 2
move 2 from 9 to 8
move 6 from 4 to 7
move 6 from 7 to 2
move 16 from 6 to 9
move 8 from 2 to 1
move 4 from 6 to 1
move 2 from 3 to 4
move 9 from 5 to 4
move 1 from 7 to 9
move 1 from 6 to 2
move 3 from 5 to 7
move 16 from 9 to 4
move 2 from 7 to 1
move 4 from 6 to 3
move 1 from 9 to 5
move 1 from 9 to 7
move 1 from 7 to 6
move 1 from 7 to 9
move 2 from 9 to 2
move 1 from 6 to 1
move 2 from 8 to 1
move 11 from 4 to 2
move 9 from 2 to 6
move 9 from 6 to 1
move 15 from 4 to 6
move 1 from 4 to 2
move 1 from 5 to 3
move 6 from 6 to 4
move 3 from 2 to 1
move 2 from 4 to 6
move 3 from 6 to 2
move 7 from 6 to 2
move 1 from 4 to 7
move 1 from 7 to 2
move 5 from 3 to 6
move 1 from 5 to 4
move 1 from 4 to 5
move 8 from 1 to 6
move 1 from 4 to 8
move 12 from 6 to 1
move 1 from 3 to 4
move 1 from 4 to 1
move 1 from 3 to 4
move 2 from 6 to 5
move 31 from 1 to 7
move 2 from 5 to 7
move 1 from 8 to 2
move 1 from 5 to 8
move 1 from 8 to 6
move 3 from 4 to 9
move 3 from 9 to 4
move 2 from 4 to 3
move 2 from 1 to 6
move 2 from 3 to 8
move 1 from 4 to 9
move 4 from 2 to 9
move 17 from 7 to 8
move 3 from 8 to 2
move 2 from 9 to 4
move 4 from 2 to 5
move 1 from 1 to 4
move 1 from 9 to 3
move 8 from 8 to 4
move 1 from 9 to 4
move 4 from 8 to 3
move 8 from 2 to 5
move 2 from 2 to 3
move 1 from 2 to 1
move 1 from 8 to 4
move 2 from 8 to 1
move 1 from 7 to 2
move 1 from 8 to 6
move 3 from 4 to 5
move 8 from 4 to 7
move 1 from 2 to 8
move 1 from 8 to 1
move 2 from 4 to 7
move 8 from 5 to 9
move 7 from 5 to 2
move 6 from 3 to 1
move 6 from 1 to 2
move 9 from 9 to 4
move 5 from 7 to 4
move 2 from 1 to 2
move 9 from 4 to 2
move 3 from 6 to 2
move 1 from 6 to 8
move 1 from 8 to 9
move 1 from 3 to 5
move 6 from 7 to 5
move 4 from 4 to 2
move 19 from 2 to 3
move 1 from 4 to 6
move 7 from 7 to 5
move 2 from 1 to 8
move 12 from 3 to 4
move 3 from 4 to 1
move 1 from 6 to 3
move 8 from 5 to 9
move 3 from 9 to 7
move 6 from 4 to 3
move 3 from 1 to 2
move 13 from 3 to 7
move 3 from 4 to 6
move 4 from 9 to 4
move 14 from 7 to 8
move 3 from 5 to 2
move 3 from 2 to 6
move 1 from 6 to 2
move 1 from 3 to 9
move 4 from 4 to 6
move 11 from 2 to 7
move 2 from 9 to 6
move 3 from 5 to 6
move 1 from 9 to 7
move 14 from 6 to 5
move 1 from 5 to 1
move 4 from 5 to 8
move 2 from 5 to 6
move 4 from 2 to 5
move 1 from 2 to 9
move 14 from 8 to 5
move 2 from 8 to 4
move 3 from 8 to 7
move 5 from 5 to 4
move 13 from 5 to 7
move 5 from 7 to 6
move 31 from 7 to 9
move 7 from 6 to 7
move 6 from 5 to 7
move 1 from 8 to 9
move 1 from 5 to 3
move 1 from 3 to 5
move 1 from 1 to 8
move 6 from 4 to 3
move 1 from 8 to 5
move 1 from 4 to 1
move 33 from 9 to 3
move 13 from 7 to 1
move 29 from 3 to 2
move 3 from 3 to 8
move 1 from 5 to 2
move 20 from 2 to 6
move 19 from 6 to 4
move 1 from 7 to 4
move 5 from 1 to 7
move 1 from 8 to 7
move 2 from 8 to 5
move 10 from 2 to 8
move 6 from 3 to 9
move 4 from 7 to 1
move 1 from 3 to 5
move 1 from 1 to 2
move 1 from 7 to 6
move 1 from 2 to 8
move 1 from 8 to 7
move 4 from 9 to 7
move 2 from 5 to 2
move 1 from 8 to 5
move 1 from 8 to 6
move 7 from 8 to 3
move 2 from 9 to 4
move 3 from 5 to 1
move 2 from 2 to 5
move 5 from 7 to 8
move 10 from 4 to 1
move 5 from 8 to 5
move 10 from 1 to 3
move 2 from 6 to 4
move 1 from 7 to 3
move 1 from 8 to 1
move 3 from 5 to 8
move 12 from 4 to 7
move 3 from 5 to 3
move 16 from 1 to 7
move 2 from 3 to 7
move 1 from 5 to 6
move 3 from 8 to 4
move 1 from 4 to 7
move 1 from 6 to 3
move 14 from 3 to 1
move 5 from 3 to 8
move 1 from 3 to 5
move 1 from 7 to 6
move 1 from 6 to 2
move 13 from 7 to 2
move 1 from 5 to 3
move 3 from 4 to 2
move 1 from 3 to 5
move 3 from 8 to 9
move 2 from 8 to 9
move 1 from 6 to 4
move 5 from 2 to 4
move 3 from 2 to 5
move 7 from 7 to 3
move 7 from 4 to 7
move 5 from 3 to 7
move 8 from 2 to 3
move 5 from 9 to 5
move 11 from 1 to 9
move 4 from 3 to 1
move 1 from 2 to 7
move 4 from 1 to 7
move 22 from 7 to 3
move 5 from 3 to 4
move 1 from 7 to 1
move 1 from 1 to 4
move 3 from 4 to 6
move 3 from 1 to 3
move 2 from 6 to 1
move 2 from 4 to 9
move 13 from 9 to 1
move 1 from 6 to 5
move 4 from 7 to 1
move 3 from 1 to 6
move 19 from 3 to 9
move 5 from 3 to 1
move 18 from 9 to 8
move 1 from 9 to 3
move 11 from 1 to 7
move 1 from 4 to 5
move 13 from 8 to 1
move 7 from 5 to 8
move 7 from 8 to 5
move 3 from 6 to 5
move 2 from 3 to 9
move 1 from 3 to 7
move 5 from 5 to 2
move 10 from 1 to 5
move 9 from 7 to 9
move 11 from 5 to 2
move 2 from 8 to 4
move 1 from 4 to 3
move 2 from 7 to 3
move 1 from 7 to 4
move 3 from 8 to 3
move 8 from 5 to 2
move 2 from 3 to 8
move 4 from 3 to 8
move 6 from 2 to 6
move 5 from 1 to 8
move 8 from 2 to 7
move 2 from 4 to 7
move 9 from 2 to 9
move 4 from 7 to 8
move 5 from 1 to 8
move 3 from 7 to 4
move 1 from 8 to 3
move 3 from 7 to 2
move 3 from 1 to 9
move 1 from 4 to 9
move 1 from 6 to 3
move 18 from 8 to 5
move 1 from 8 to 2
move 2 from 4 to 9
move 3 from 2 to 1
move 2 from 2 to 3
move 24 from 9 to 8
move 3 from 3 to 7
move 15 from 8 to 2
move 12 from 2 to 5
move 1 from 7 to 4
move 1 from 3 to 1
move 28 from 5 to 4
move 1 from 7 to 9
move 2 from 2 to 1
move 4 from 6 to 3
move 1 from 5 to 3
move 1 from 5 to 9
move 1 from 2 to 6
move 5 from 3 to 5
move 8 from 4 to 2
move 2 from 6 to 2
move 1 from 7 to 3
move 4 from 2 to 8
move 3 from 1 to 2
move 5 from 2 to 5
move 3 from 5 to 4
move 2 from 1 to 5
move 2 from 2 to 1
move 4 from 9 to 2
move 7 from 8 to 9
move 1 from 3 to 1
move 1 from 1 to 7
move 2 from 8 to 3
move 4 from 9 to 3
move 9 from 5 to 7
move 3 from 3 to 5
move 1 from 5 to 3
move 7 from 7 to 9
move 1 from 7 to 9
move 1 from 5 to 9
move 1 from 5 to 1
move 1 from 8 to 5
move 9 from 9 to 1
move 2 from 7 to 2
move 1 from 5 to 6
move 4 from 3 to 2
move 11 from 2 to 4
move 1 from 8 to 4
move 1 from 8 to 2
move 1 from 2 to 8
move 1 from 6 to 5
move 1 from 8 to 6
move 6 from 1 to 7
move 1 from 5 to 6
move 1 from 6 to 5
move 3 from 9 to 8
move 3 from 8 to 1
move 3 from 7 to 8
move 1 from 6 to 9
move 1 from 2 to 4
move 1 from 9 to 7
move 2 from 7 to 9
move 10 from 1 to 6
move 2 from 9 to 3
move 1 from 5 to 7
move 3 from 7 to 5
move 3 from 5 to 3
move 4 from 6 to 3
move 18 from 4 to 2
move 3 from 4 to 1
move 1 from 1 to 3
move 2 from 1 to 2
move 8 from 2 to 9
move 1 from 4 to 7
move 1 from 7 to 1
move 3 from 9 to 2
move 3 from 8 to 6
move 1 from 4 to 9
move 7 from 2 to 8
move 7 from 6 to 7
move 3 from 9 to 2
move 3 from 2 to 5
move 6 from 4 to 6
move 2 from 5 to 6
move 3 from 3 to 6
move 6 from 6 to 3
move 5 from 7 to 5
move 2 from 4 to 8
move 5 from 5 to 2
move 1 from 7 to 2
move 4 from 6 to 4
move 1 from 7 to 8
move 1 from 6 to 4
move 1 from 5 to 7
move 1 from 3 to 4
move 1 from 6 to 4
move 2 from 9 to 1
move 3 from 1 to 3
move 1 from 3 to 1
move 9 from 2 to 1
move 8 from 1 to 5
move 1 from 7 to 1
move 1 from 9 to 1
move 4 from 5 to 7
move 4 from 7 to 5
move 1 from 1 to 9
move 5 from 2 to 4
move 1 from 9 to 6
move 8 from 8 to 9
move 18 from 4 to 9
move 3 from 5 to 4
move 2 from 6 to 5
move 1 from 8 to 5
move 17 from 9 to 6
move 2 from 8 to 1
move 1 from 4 to 6
move 8 from 6 to 3
move 1 from 1 to 8
move 5 from 5 to 3
move 1 from 1 to 7
move 1 from 8 to 6
move 2 from 4 to 5
move 6 from 9 to 4
move 1 from 7 to 5
move 7 from 6 to 8
move 2 from 6 to 5
move 6 from 8 to 3
move 1 from 9 to 6
move 2 from 9 to 5
move 1 from 3 to 1
move 1 from 8 to 6
move 7 from 5 to 6
move 7 from 6 to 7
move 5 from 4 to 9
move 1 from 4 to 5
move 2 from 9 to 6
move 3 from 1 to 7
move 5 from 6 to 8
move 1 from 1 to 5
move 21 from 3 to 6
move 3 from 7 to 2
move 2 from 9 to 3
move 1 from 9 to 7
move 5 from 5 to 7
move 7 from 6 to 7
move 14 from 7 to 1
move 3 from 2 to 8
move 12 from 1 to 4
move 5 from 7 to 6
move 1 from 7 to 4
move 8 from 8 to 3
move 8 from 3 to 5
move 6 from 5 to 6
move 1 from 5 to 3
move 2 from 1 to 8
move 2 from 8 to 3
move 10 from 3 to 7
move 8 from 4 to 3
move 3 from 4 to 9
move 3 from 9 to 2
move 1 from 2 to 5
move 2 from 2 to 9
move 13 from 3 to 1
move 1 from 4 to 1
move 2 from 1 to 7
move 1 from 5 to 8
move 1 from 9 to 6
move 1 from 9 to 2
move 1 from 4 to 9
move 8 from 6 to 2
move 1 from 9 to 5
move 1 from 2 to 8
move 1 from 5 to 9
move 2 from 2 to 3
move 12 from 6 to 8
move 1 from 3 to 7
move 8 from 8 to 4
move 1 from 9 to 1
move 13 from 1 to 3
move 2 from 4 to 5
move 12 from 7 to 2
move 1 from 5 to 8
move 3 from 3 to 8
move 2 from 4 to 1
move 1 from 1 to 9
""".trimIndent()