package day10

fun solve() {
    assert(testInput.totalStrength() == 13140)
    assert(realInput.totalStrength() == 14340)

    println(testInput.spritePattern())
    println(realInput.spritePattern()) // the answer is PAPJCBHP
}

private val regex = Regex("""addx (-?\d+)""")

private data class Operation(val cycles: Int, val value: Int)

private fun String.operation(): Operation? =
    if (this == "noop") {
        Operation(1, 0)
    } else {
        regex.matchEntire(this)?.destructured?.let { (valueStr) ->
            valueStr.toIntOrNull()?.let { value ->
                Operation(2, value)
            }
        }
    }

private fun String.operations(): Sequence<Operation> = lineSequence().mapNotNull { it.trim().operation() }

private fun String.registerValues(): List<Int> {
    val registerValues = mutableListOf<Int>() // contains value in the register at the end of each cycle
    registerValues.add(1) // starting value of the register (at the end of cycle 0)
    this.operations().forEach { operation ->
        for (i in 1 until operation.cycles) {
            registerValues.add(registerValues.last())
        }
        registerValues.add(registerValues.last() + operation.value)
    }
    return registerValues
}

private fun String.totalStrength(): Int {
    val registerValues = registerValues()
    return listOf(20, 60, 100, 140, 180, 220).sumOf { it * registerValues[it - 1] }
}

private fun String.spritePattern(): String {
    var answer = ""
    val registerValues = this.registerValues()
    for (i in registerValues.indices) {
        val index = i % 40
        if (index == 0) answer += "\n"
        answer += if (index in registerValues[i] - 1..registerValues[i] + 1) {
            "#"
        } else {
            "."
        }
    }
    return answer
}

private val testInput = """
addx 15
addx -11
addx 6
addx -3
addx 5
addx -1
addx -8
addx 13
addx 4
noop
addx -1
addx 5
addx -1
addx 5
addx -1
addx 5
addx -1
addx 5
addx -1
addx -35
addx 1
addx 24
addx -19
addx 1
addx 16
addx -11
noop
noop
addx 21
addx -15
noop
noop
addx -3
addx 9
addx 1
addx -3
addx 8
addx 1
addx 5
noop
noop
noop
noop
noop
addx -36
noop
addx 1
addx 7
noop
noop
noop
addx 2
addx 6
noop
noop
noop
noop
noop
addx 1
noop
noop
addx 7
addx 1
noop
addx -13
addx 13
addx 7
noop
addx 1
addx -33
noop
noop
noop
addx 2
noop
noop
noop
addx 8
noop
addx -1
addx 2
addx 1
noop
addx 17
addx -9
addx 1
addx 1
addx -3
addx 11
noop
noop
addx 1
noop
addx 1
noop
noop
addx -13
addx -19
addx 1
addx 3
addx 26
addx -30
addx 12
addx -1
addx 3
addx 1
noop
noop
noop
addx -9
addx 18
addx 1
addx 2
noop
noop
addx 9
noop
noop
noop
addx -1
addx 2
addx -37
addx 1
addx 3
noop
addx 15
addx -21
addx 22
addx -6
addx 1
noop
addx 2
addx 1
noop
addx -10
noop
noop
addx 20
addx 1
addx 2
addx 2
addx -6
addx -11
noop
noop
noop
""".trimIndent()

private val realInput = """
noop
noop
noop
addx 6
addx -1
noop
addx 5
noop
noop
addx -12
addx 19
addx -1
noop
addx 4
addx -11
addx 16
noop
noop
addx 5
addx 3
addx -2
addx 4
noop
noop
noop
addx -37
noop
addx 3
addx 2
addx 5
addx 2
addx 10
addx -9
noop
addx 1
addx 4
addx 2
noop
addx 3
addx 2
addx 5
addx 2
addx 3
addx -2
addx 2
addx 5
addx -40
addx 25
addx -22
addx 2
addx 5
addx 2
addx 3
addx -2
noop
addx 23
addx -18
addx 2
noop
noop
addx 7
noop
noop
addx 5
noop
noop
noop
addx 1
addx 2
addx 5
addx -40
addx 3
addx 8
addx -4
addx 1
addx 4
noop
noop
noop
addx -8
noop
addx 16
addx 2
addx 4
addx 1
noop
addx -17
addx 18
addx 2
addx 5
addx 2
addx 1
addx -11
addx -27
addx 17
addx -10
addx 3
addx -2
addx 2
addx 7
noop
addx -2
noop
addx 3
addx 2
noop
addx 3
addx 2
noop
addx 3
addx 2
addx 5
addx 2
addx -5
addx -2
addx -30
addx 14
addx -7
addx 22
addx -21
addx 2
addx 6
addx 2
addx -1
noop
addx 8
addx -3
noop
addx 5
addx 1
addx 4
noop
addx 3
addx -2
addx 2
addx -11
noop
noop
noop
""".trimIndent()