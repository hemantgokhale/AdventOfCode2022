package day11

fun solve() {
    assert(testInput.monkeyBusiness() == 10605)
    println(realInput.monkeyBusiness())
}

private fun String.monkeyBusiness(): Int {
    val monkeys = this.monkeys()
    repeat(20) { monkeys.playOneRound() }
    return monkeys.map { it.inspectionsCount }.sortedDescending().take(2).reduce { acc, i -> acc * i }
}
private fun List<Monkey>.playOneRound() {
    for (monkey in this) {
        while (monkey.items.isNotEmpty()) {
            monkey.inspectionsCount++
            val item = monkey.items.removeFirst()
            val worryLevel = when (monkey.operator) {
                Operator.ADD -> item + monkey.operand
                Operator.SQUARE -> item * item
                Operator.MULTIPLY -> item * monkey.operand
            } / 3
            if (worryLevel % monkey.divisibleBy == 0) {
                this[monkey.targetForTrue].items.add(worryLevel)
            } else {
                this[monkey.targetForFalse].items.add(worryLevel)
            }
        }
    }
}

private enum class Operator {
    ADD, MULTIPLY, SQUARE;

    companion object {
        fun fromString(str: String): Operator {
            if (str == "+") return ADD
            if (str == "*") return MULTIPLY
            throw Exception("$str does not map to an operation")
        }
    }
}

private data class Monkey(
    val id: Int,
    val items: MutableList<Int>,
    val operator: Operator,
    val operand: Int,
    val divisibleBy: Int,
    val targetForTrue: Int,
    val targetForFalse: Int,
    var inspectionsCount: Int = 0
)

private fun String.id(): Int {
    val regex = Regex("""Monkey (\d+):""")
    return regex.matchEntire(this.trim())?.destructured?.let { (id) -> id.toIntOrNull() }
        ?: throw Exception("Failed to extract id from: $this")
}

private fun String.items(): MutableList<Int> {
    val regex = Regex("""Starting items: (.*)""")
    return regex.matchEntire(this.trim())?.destructured?.let { (numbers) -> numbers.split(", ") }?.map { it.toInt() }
        ?.toMutableList()
        ?: throw Exception("Failed to extract items from: $this")
}

private fun String.operation(): Pair<Operator, Int> {
    val regex = Regex("""Operation: new = old ([+*]) (\d+|old)""")
    return regex.matchEntire(this.trim())?.destructured?.let { (operation, operand) ->
        if (operand == "old") {
            Pair(Operator.SQUARE, 1)// the operand doesn't really matter for this operation.
        } else {
            Pair(Operator.fromString(operation), operand.toInt())
        }
    }
        ?: throw Exception("Failed to extract operation from: $this")
}

private fun String.divisibleBy(): Int {
    val regex = Regex("""Test: divisible by (\d+)""")
    return regex.matchEntire(this.trim())?.destructured?.let { (id) -> id.toIntOrNull() }
        ?: throw Exception("Failed to extract 'divisibleBy' from: $this")
}

private fun String.targetForTrue(): Int {
    val regex = Regex("""If true: throw to monkey (\d+)""")
    return regex.matchEntire(this.trim())?.destructured?.let { (id) -> id.toIntOrNull() }
        ?: throw Exception("Failed to extract 'targetForTrue' from: $this")
}

private fun String.targetForFalse(): Int {
    val regex = Regex("""If false: throw to monkey (\d+)""")
    return regex.matchEntire(this.trim())?.destructured?.let { (id) -> id.toIntOrNull() }
        ?: throw Exception("Failed to extract 'targetForFalse' from: $this")
}

private fun String.monkey(): Monkey {
    val lines = lines()
    val id = lines[0].id()
    val items = lines[1].items()
    val (operator, operand) = lines[2].operation()
    val divisibleBy = lines[3].divisibleBy()
    val targetForTrue = lines[4].targetForTrue()
    val targetForFalse = lines[5].targetForFalse()
    return Monkey(id, items, operator, operand, divisibleBy, targetForTrue, targetForFalse)
}

private fun String.monkeys(): List<Monkey> = split("\n\n").map { it.monkey() }

val testInput = """
Monkey 0:
  Starting items: 79, 98
  Operation: new = old * 19
  Test: divisible by 23
    If true: throw to monkey 2
    If false: throw to monkey 3

Monkey 1:
  Starting items: 54, 65, 75, 74
  Operation: new = old + 6
  Test: divisible by 19
    If true: throw to monkey 2
    If false: throw to monkey 0

Monkey 2:
  Starting items: 79, 60, 97
  Operation: new = old * old
  Test: divisible by 13
    If true: throw to monkey 1
    If false: throw to monkey 3

Monkey 3:
  Starting items: 74
  Operation: new = old + 3
  Test: divisible by 17
    If true: throw to monkey 0
    If false: throw to monkey 1
""".trimIndent()

val realInput = """
Monkey 0:
  Starting items: 50, 70, 54, 83, 52, 78
  Operation: new = old * 3
  Test: divisible by 11
    If true: throw to monkey 2
    If false: throw to monkey 7

Monkey 1:
  Starting items: 71, 52, 58, 60, 71
  Operation: new = old * old
  Test: divisible by 7
    If true: throw to monkey 0
    If false: throw to monkey 2

Monkey 2:
  Starting items: 66, 56, 56, 94, 60, 86, 73
  Operation: new = old + 1
  Test: divisible by 3
    If true: throw to monkey 7
    If false: throw to monkey 5

Monkey 3:
  Starting items: 83, 99
  Operation: new = old + 8
  Test: divisible by 5
    If true: throw to monkey 6
    If false: throw to monkey 4

Monkey 4:
  Starting items: 98, 98, 79
  Operation: new = old + 3
  Test: divisible by 17
    If true: throw to monkey 1
    If false: throw to monkey 0

Monkey 5:
  Starting items: 76
  Operation: new = old + 4
  Test: divisible by 13
    If true: throw to monkey 6
    If false: throw to monkey 3

Monkey 6:
  Starting items: 52, 51, 84, 54
  Operation: new = old * 17
  Test: divisible by 19
    If true: throw to monkey 4
    If false: throw to monkey 1

Monkey 7:
  Starting items: 82, 86, 91, 79, 94, 92, 59, 94
  Operation: new = old + 7
  Test: divisible by 2
    If true: throw to monkey 5
    If false: throw to monkey 3
""".trimIndent()