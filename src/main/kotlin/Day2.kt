fun solveDay2() {
    val totalScore = readGame(input = realInput).sumOf { round ->
        round.myScore
    }
    println("totalScore: $totalScore") // 11873
}

enum class Play {
    ROCK, PAPER, SCISSORS;

    val score: Int
        get() = when (this) {
            ROCK -> 1
            PAPER -> 2
            SCISSORS -> 3
        }

    companion object {
        fun fromChar(c: Char): Play? =
            when (c) {
                'A', 'X' -> ROCK
                'B', 'Y' -> PAPER
                'C', 'Z' -> SCISSORS
                else -> null
            }
    }
}

data class Round(val their: Play, val my: Play) {
    private val iWon: Boolean
        get() = (their == Play.ROCK && my == Play.PAPER)
                || (their == Play.PAPER && my == Play.SCISSORS)
                || (their == Play.SCISSORS && my == Play.ROCK)

    private val draw: Boolean
        get() = (their == Play.ROCK && my == Play.ROCK)
                || (their == Play.PAPER && my == Play.PAPER)
                || (their == Play.SCISSORS && my == Play.SCISSORS)

    val myScore: Int = my.score + (if (iWon) 6 else 0) + (if (draw) 3 else 0)
}

// Interpret the second part of the string as a record of the round that I played
fun String.asRound(): Round? {
    val trimmed = trim()
    val theirPlay = Play.fromChar(trimmed.first()) ?: return null
    val myPlay = Play.fromChar(trimmed.last()) ?: return null
    return Round(theirPlay, myPlay)
}

// Interpret the second part of the string as the outcome I should play for i.e. strategy
fun String.asStrategy(): Round? {
    val trimmed = trim()
    val theirPlay: Play = Play.fromChar(trimmed.first()) ?: return null
    val myPlay: Play = when (trimmed.last()) {
        'X' -> // lose
            when (theirPlay) {
                Play.ROCK -> Play.SCISSORS
                Play.PAPER -> Play.ROCK
                Play.SCISSORS -> Play.PAPER
            }

        'Y' -> // draw
            theirPlay

        'Z' -> // win
            when (theirPlay) {
                Play.ROCK -> Play.PAPER
                Play.PAPER -> Play.SCISSORS
                Play.SCISSORS -> Play.ROCK
            }

        else -> null
    } ?: return null

    return Round(theirPlay, myPlay)
}

fun readGame(input: String): List<Round> = input.lineSequence().mapNotNull { it.asStrategy() }.toList()

private val testInput = """
    A Y
    B X
    C Z
""".trimIndent()

private val realInput = """
A X
B X
C X
C Y
B Y
C Z
C X
C Y
C Y
B Y
B Y
C Z
C Y
A Y
B Z
B Z
C Y
B Z
A Y
C Y
B Y
C Y
B Y
B X
A Y
A Y
C Y
B X
C X
B Y
A Y
C Z
C X
B Y
B Y
A Y
C X
B Y
B Y
B Z
B Y
A X
C Y
A Y
B X
C Y
A Y
C X
A Z
A X
C Y
A Y
A Y
B Z
B Y
A Y
C Y
B Z
C Y
A Y
A Y
C Y
B Y
B X
B X
C Y
B Y
B X
A X
B Z
A X
B Y
A Y
B Z
B X
B Y
A Y
C X
C Y
C Y
A Y
A Z
C Z
A Y
B Z
C Y
A Y
C X
C Z
B Z
B Y
B Y
A Y
C Z
C Y
B Y
A Z
A Y
A Z
A Y
A Y
A Y
A Y
C Y
B Y
B Y
A Z
A Y
A Y
A X
B Z
C Y
A Y
B Y
B Y
A Y
A Y
B Y
B Y
C X
C Y
B Z
C Y
C Y
B Y
B Z
C X
A X
A X
A Z
B Y
B Z
C Y
C Y
C Y
A Y
C X
B X
B Y
B Y
C Y
C Z
C Y
A Y
C Y
B Z
C X
B Y
A Y
C Y
C Y
C Z
B X
B Y
B Y
A Y
C Z
C Y
B Y
C Y
B Z
C Y
A Y
B Y
B Z
C X
A Y
B Y
B Y
C Z
A Y
C Y
A X
B Y
B Y
B Y
B Z
B X
C Y
B X
C Z
B Y
B Y
C Y
B Y
B Z
A X
C Z
B Y
C Y
C Y
C X
C X
A Y
C Y
B Z
C Y
B Y
C Z
B X
A X
A Y
B Y
C Z
C Z
C Y
B X
B X
B X
B Y
B X
C Y
C Y
B X
B Y
B Z
B Z
C Y
C Y
A Y
B Y
B Y
B Y
B Y
B X
A Y
B Y
C Z
B Z
C Y
B Y
B Y
B X
A Y
C Z
C Y
C Y
C Y
C Z
C Y
B X
C X
C Z
B X
B Y
A Y
C Y
A Y
B Y
B X
C X
C X
C Y
C Y
C Y
B Y
C Y
A Y
B Y
A Y
C Z
B Y
C Y
A Y
B Y
A Y
A Y
B X
B Y
B Y
A X
C X
B X
C Y
A Y
B Y
A Y
C Z
B Y
C Z
B Z
B Y
A Y
C Y
C X
B X
C Y
A Z
A X
B Z
A Y
B X
C Y
C Y
C Y
A Y
C Z
A X
B Z
C Y
B Y
B Y
B Y
C Y
A Y
C X
B X
B X
C Y
B Z
C Y
B X
C Y
B Y
A Y
C Y
B Z
A Y
A Y
C Y
B Y
B X
A Y
C Z
B Y
C Z
B X
B Y
A Y
C Z
C Y
C Y
B Y
C Y
B X
C Y
B Y
B X
C Y
B X
A X
B Y
A Z
C Y
A Y
B Y
B X
C Y
B X
C Y
C Y
B Y
C Y
C Y
B X
B X
C Y
A Y
B X
C Y
B Y
A Y
B X
B Y
A Y
A Z
A Y
C Y
A X
B Y
C Z
B Y
A Y
B Y
A X
C X
B Y
A Y
A Y
B Y
B Y
B Y
A Y
B Z
C X
A Y
B X
B Y
A Z
B Z
A X
B Y
C X
C X
A Y
B Y
C Y
B Z
B Y
A Z
C Z
A Y
A Y
A X
B Y
B Y
C X
A Y
C Z
A Y
A Y
B Y
A Y
B Z
B Y
B X
C X
C Y
A Y
B Z
A Y
B Y
A Y
A Y
C Y
A X
C Y
B X
B X
B Y
B X
B Z
B Y
C Y
C Y
C Z
B Z
A Z
B Y
C Y
C Y
C Y
B Y
A Y
B Z
C Y
C Z
B Y
B Y
B Y
B Y
C X
B Z
A Y
A Y
B Y
B X
B Y
B X
A Y
C Y
B X
B Y
C Y
B Y
C Y
C Y
C X
B Y
B X
C Z
B Y
A Y
B X
C Y
A Z
C Y
C X
B Y
C Z
C Y
B X
C Y
A Y
A X
B X
C X
A Y
C Z
C Y
B Y
B X
C Y
B X
B Z
B Z
B Y
B Z
B X
C Y
C Y
B Y
B Y
C Y
B Y
B Y
B Z
B Y
A X
B Y
C X
B Y
A X
C Y
B Y
A Y
B Z
B Z
A Y
A X
C Y
B Y
B Y
C X
A Y
C Y
B Y
C Z
B X
A Y
A Y
B Z
A Y
B X
B X
C Y
A X
B X
B X
B Y
B Y
B Y
B Z
A Y
C Y
A Y
C Y
A Z
C X
B Y
C Y
B Y
C Y
B X
B Z
A X
A Y
C Y
C Y
B Y
A Y
A Y
A Y
A Y
A Y
C Y
C X
B Y
B Z
B Z
B Y
B Z
B Z
B X
A Y
B Z
B Y
C Z
B Y
C Z
A Z
C Y
C X
B Y
B Y
B Y
A Z
C Y
A Y
B Y
A Y
A Y
A X
B Y
B Z
B X
A X
C Z
B X
C Y
B X
B Y
B X
A Y
B X
B Y
C Z
C Y
C X
B Y
B Y
A X
C Y
B Y
A Y
C Y
C X
B Z
C Y
A Y
C X
B Y
C Y
C X
C X
C Z
C Y
C X
A Y
C Y
C Y
B X
B X
B Y
C Z
B Y
B Z
B Y
C Y
B X
A Y
B Y
B Y
B X
B Y
B Y
C Y
B Z
B Y
C X
A Y
A Y
C Y
C Z
B Y
B X
C Y
C Y
C Y
A Y
C X
A Y
B Y
B X
A Y
B Y
C Y
B X
C Y
B Y
A Y
A Z
C Y
B Y
C Y
A Y
B X
B X
B Y
C Y
B X
B Y
C Z
C Y
C Y
A Y
C Y
B Z
B Z
B Y
C Y
C Z
A X
B Y
B Z
C Z
B X
A Y
A X
B X
B X
A Z
B X
C Y
C Y
B Y
C Y
B Y
A Y
B Y
C X
B Z
C X
B X
C X
B Y
C Y
C Y
B Y
B Y
C Y
C X
B X
A Y
C Y
A X
B Y
B Y
B Y
C Y
B Z
C Y
C Z
B X
C Y
A Y
B Y
A Y
C Y
A Z
B Z
B Y
A Y
B Z
B Y
C X
B Y
C Y
C Y
A Y
C Y
A Y
B Y
C Z
C X
C Y
C X
B X
B Y
C Z
A Y
C Y
C Z
C Y
C Y
C Y
B Y
B Z
B Y
C Y
C Z
B Z
C Y
C X
A Z
B Y
A Y
B Y
B Y
A X
C Z
C Y
B Y
B Y
C Y
C Y
B Y
C Y
C Y
B X
B Y
C X
B X
B Y
C X
C Y
B Z
C X
C X
B Y
A Y
B Y
C Y
B X
A Y
C Y
C X
C X
A Z
B Z
A Y
C Y
C Y
A X
A Y
B Y
B Y
C Y
C Y
C Y
C Z
B Y
C Y
A Y
B X
C Z
A Y
C X
B Y
B Y
B Y
C Z
B Y
C Y
B Y
B X
C Y
C Y
A Y
B Y
B Y
B X
B Y
B Z
B Y
A X
C Y
B X
B Y
B X
B Y
B Y
C Y
C Y
C Y
B Y
B Y
B X
A Y
C Z
B X
B X
C X
C Y
A Y
B Y
B Y
C Y
C Y
C Z
A X
B X
A X
A Z
A X
C Y
C Y
A X
B Y
C Z
B X
C Y
B X
B Y
B Y
B X
C Y
B X
A Y
B Y
B Y
B X
B Y
C Y
B Y
B Y
C Y
C Y
B X
C Y
B X
C Y
C Y
C Z
C Y
B X
B Y
A Y
C Y
A Y
B Y
C Y
C Y
B X
B Y
B Y
A Y
B Y
A Y
B X
A X
C X
B Y
A Z
A Y
B X
B X
A Y
B X
B X
C X
C X
B X
B Z
C Z
A Y
B X
B Y
B Z
C Y
C Y
C Y
A Z
B X
B Z
B X
B Y
B Z
B Y
B Z
C Y
B Y
C X
C Y
B X
C Z
A Z
A X
B Y
C Y
A Y
C Y
A Y
A X
C Y
B Y
B Y
A Y
C Y
C X
B Z
B Y
B Z
C Y
A Y
C X
A Y
C Y
C Y
B X
C Y
A Y
C Y
C X
B Y
B Z
A Y
B X
A X
A Y
C Y
B Y
A Y
B Y
C Y
C Z
B X
A Y
A Y
A Y
A Z
A Y
B Y
C Y
C Y
B Z
B X
C Y
A Y
B X
B Y
B X
C Y
B Y
A X
C X
C Y
B X
B Y
B Z
B X
B Y
A Y
B X
B Z
C Y
A Y
C X
C Z
C X
A Z
B Z
B Y
C X
B X
C Y
B Y
A Y
C X
A Y
B X
B Y
C Y
B Y
C Y
B Z
B X
A Y
A X
C Y
C Y
B Y
B Y
B Y
B Y
C Z
A X
C Y
C Y
B Y
B Z
C Z
A Y
B X
C Y
C Y
B Z
B Y
B Y
B X
B Z
B Y
C Y
C Y
C Y
B Y
A X
A Y
C Y
A Y
A X
A Y
C Y
B X
C Y
C X
B Y
B Y
C Z
C Z
B Y
B Y
C X
C Y
B Z
A Y
C X
B X
C Y
B Y
B Z
A Y
B Z
B X
B Y
A Y
B Y
C Z
A Y
B X
A Y
B Y
B X
A Y
C Y
A Y
B Z
B Z
B Y
A X
A Y
C Z
B Y
B Z
C Y
B Y
B X
B Z
B Y
A X
A Y
C Z
C Y
A Z
B Z
B X
B X
B Y
C Y
B Y
C Y
B Y
B Z
C Z
B X
A Y
C Y
C Y
C Y
C X
C Y
A Y
B X
A Y
B X
A Y
C Y
A Y
B Y
B Y
C Z
B Y
C Y
C Y
B Z
A Y
C Y
B Y
B Y
C Y
C Z
B X
A Y
C X
C Y
B Y
B X
A Y
B Y
A Y
A X
C X
C Y
C Y
A Y
B Y
A Y
A Y
A Y
B X
B Z
C Z
C Z
B X
C Y
B X
C Y
B X
C Y
C Y
C Y
B X
B Y
A Z
C Y
B X
C Y
B Y
B Y
C Y
A Y
C Y
B X
B Y
A X
C Y
C Y
B Y
B X
B Z
C Z
A Y
A Y
B Y
A X
A Y
B X
C Y
B Z
B Y
B Z
B Y
A Y
B Y
B Y
B X
A X
B Z
B Y
A Y
B X
B Z
B Y
B X
C Z
C Z
B X
A Y
C Y
C Y
B Y
C Y
B Y
B Y
B Y
B X
B Z
B Z
B Y
B X
B Y
B X
B Y
C Z
C Y
B Z
C Z
C Y
B Y
C X
C Z
B X
C Y
C Y
B Y
C Y
B Z
B Y
B Y
A Y
C Y
C X
A Y
C Y
B X
B Y
C Y
B X
C Y
C Y
C X
C Z
B X
C X
A Y
B X
A Y
B Z
A Z
C Y
A Y
A X
B Y
B Z
A Y
B Y
C Y
A Y
B Y
C X
C Y
C Y
C X
C X
C Y
B X
B Z
C X
B Y
C Y
C Y
C Y
C Z
B Z
C Z
B Z
C X
B Y
A Z
C Z
B Y
B Y
B Y
C Y
B X
B X
B Y
C Y
C X
A Y
C Z
B Z
A Y
B Y
B Z
C Y
B X
C X
A Y
B Y
B Y
C Z
B X
A Z
C Z
B X
C Y
B X
C Y
C X
C Y
C Y
C Y
B Y
B Z
B Z
C Y
B Z
C Y
C Z
B Y
C Y
C Y
C Z
B X
A Y
B Y
B Y
B Y
A Z
B X
C Y
C Y
A Y
C Y
C Y
B X
C X
A Y
B Y
A Y
C Z
A Y
C Z
B Z
A Z
A Y
B X
C Y
B X
C Y
C X
A Y
B Y
B Y
C Z
C Z
B Z
C Y
B Y
B Z
B X
A Z
C Y
C X
B Y
C Z
A Y
B X
C X
C X
B Z
C X
B Z
B Z
A Y
B Y
B X
B Z
C X
B Y
A Y
C Y
C Y
B Y
B Z
C X
B Y
B Y
C Y
A Y
C Z
C Z
B Y
B Y
B X
C Y
B X
B Y
C Y
C Y
B X
B Z
B Y
C Z
A Y
A X
C Y
A Y
B Z
B X
B Z
B Z
B Y
B Y
B Y
A Y
A X
A Y
B Y
A Y
C Y
C Y
C Y
B Y
C Y
C Y
B X
C Y
B Y
A Y
A Y
C Z
B Y
B X
C X
B X
B Y
A Y
B X
B Y
B Z
C Y
A Y
A Z
B X
C Y
C X
B Y
B X
C Y
C Z
C Y
A Y
A X
C Z
C X
A Y
A X
B Y
C X
C X
C Y
C Z
C X
B Z
B Y
A Y
B Y
C Y
C Y
C Y
C Y
C X
B Y
A Y
A Y
C Y
A Y
A Y
C Y
A Y
C X
B X
A Y
A Y
A Y
A Y
B X
C Y
A Y
C Y
B Y
B Y
C Y
B X
C Y
A Y
C Z
A X
B X
C Y
C Y
B Y
A Y
A Y
C Y
C X
B X
B Z
B X
C X
A Y
B Y
B X
B Y
B Z
A Y
B Y
B Z
A Y
C Z
B Y
C X
C Y
A Y
C Z
C Y
B X
B Y
C Y
C Z
A Y
B X
B X
A Y
B Z
B Y
C Z
B X
B X
C Z
A Y
C Z
A Y
C X
B X
C Y
A Y
B Z
B Y
B X
C Z
A Y
B X
C X
C Y
B Y
C Z
C Y
C Y
A Y
C Z
C Y
A Y
B Y
B Y
C Y
A Y
B Y
B Y
B Z
A Y
C Z
A Z
C Z
B X
B Y
C X
A Y
A Y
C Y
A Y
B Y
C X
A X
A Y
B Y
C Y
C X
A Y
A Y
B X
C Y
B Y
C Z
C X
B X
C Y
A X
A Y
A Y
B Y
C Y
C Z
A Y
B Y
C Y
C Y
B X
A Y
B Y
B X
C Y
B Z
C Y
B X
A Y
B Y
C Y
C Z
B Y
C Y
A Z
C Z
B X
B Y
C Y
B Y
A Y
B Y
B X
C Z
C Z
C X
B Y
C Y
C Y
B Y
B Y
A Y
B Z
B X
C Y
B Y
C Y
B Z
C Z
C Z
C Y
B Y
A Y
C X
B X
B X
B Y
C Y
C Y
B Y
B Z
C Y
B X
C Y
C Z
B X
A Y
B X
C Y
C X
A Y
B X
A Y
A Y
C Y
B Y
C Y
B X
C Y
B Y
C Y
B X
C Y
B Y
A Y
A Y
C Z
B X
C Y
C Z
B Y
A X
C X
B X
B X
C Y
B X
C X
B Y
A Y
B Y
C Y
B Y
C Y
C Y
A X
C X
C Y
B Y
B Y
C Y
C Y
A Y
A Y
C Z
C Y
C Y
B X
A Y
C Z
B Y
B Y
B Y
C Z
B Z
B Z
C X
C Y
B Y
B Y
A Y
C Y
C Y
B Y
C Y
A Y
C Y
B Y
C Y
B Z
B Y
A Y
B Y
B X
C Z
C X
C X
A Y
C Z
B Y
B X
A Y
B Y
A X
B Y
B Y
B Y
B X
C Y
C Y
A Y
A Y
B Y
B Z
B Y
B X
B Z
C X
B Y
A Y
B Z
B X
A Y
B Y
A Y
B X
B X
B X
B Y
C X
B X
C X
C Y
B Y
A X
B Y
C Y
B Y
C Y
B Y
B Y
A Y
B Z
B X
B Y
B Y
B Y
A Z
A Y
C Y
C Z
C X
B Z
C Y
B Y
A Y
B X
B Y
C X
B X
B Y
C Y
B Z
C Y
B Z
B Z
B Y
B X
B Y
B Y
B Y
C Y
A Y
A X
C Y
B Y
C Z
B Z
C Y
C X
B X
C X
B X
C Z
A Y
C Y
B Z
C Y
A X
B Z
B Y
B X
B Y
B X
C X
C X
B Z
B Z
C Y
B Y
B X
B X
B Z
A Y
A Y
A Y
B Z
B X
C Z
B Y
B Y
C Y
C Y
B X
B Y
C Y
B Y
B Y
A X
B Z
A Z
B Z
C X
B Z
A Y
B Z
B Y
B Z
A Y
B Y
C Y
C Z
B Y
C X
B X
B Y
A Y
B Z
C Y
C X
C Z
A Y
B Y
C Y
A X
C Z
B X
C Y
A Y
B Y
B Z
A Y
B X
C Y
B Y
C Z
C Y
B Y
B Y
C Y
C Y
C Y
A Y
B Z
C Y
B X
A Y
C Y
A Y
A X
B Z
A Y
B X
B Y
B X
A Y
C Z
B Y
B Z
C X
C Y
C Y
B Z
B Y
C Y
C X
C Y
C Y
B Y
B Y
A Y
B X
C Y
C Y
C Y
C Y
B Y
B Y
C Z
C X
B Y
C Z
A Y
B X
A Y
B Y
C X
B X
B X
B Y
A X
C X
C Y
B Y
C Y
C X
B Y
C Z
B Y
A Y
C Y
C Y
A X
A Y
C Y
A Y
A Y
C Y
A Y
B Y
A Y
B Z
B X
A X
A X
A Z
B Y
B Y
C X
C Y
C X
C X
A Y
B X
B Y
B Y
A X
B X
B Y
B Y
C Y
B Y
C Y
C X
A X
C Y
B Y
C Y
B Y
C Z
B X
B Y
B X
B X
C Y
B Y
A X
C Y
B Y
B X
A Y
B X
A Y
C Y
B Z
B Y
A Y
A Y
B Y
B X
B Y
C Y
B Y
B Y
A Y
A Y
C Y
B Y
C Z
C X
C Z
C X
C Z
A Y
B Y
A Y
C Y
C Y
B Y
C Y
B X
B X
A Y
B Y
B Y
A Y
B X
B Y
B Y
C X
C Y
C X
C Y
C Y
C Y
C X
C Z
B X
B Y
C Y
B Y
B Y
C Y
B X
C Y
B Y
B Y
B Z
B X
A Z
C Y
C Y
A Y
B Y
B Y
A Y
B X
C Y
B Y
B Y
B Z
B X
C Y
B Y
A Y
B Z
C Y
C X
C Y
C Y
B Y
A Y
C Y
C X
A Y
B Y
B Z
B X
C Z
A X
C Y
B Y
B Z
C Y
B Y
B Y
B Y
B Y
C Y
B Y
C Y
B Y
A Y
A Y
A X
B Y
C Y
C Y
C Y
C X
C Z
C X
B X
A Y
C Y
A Y
B Y
C Y
C Z
A Y
B X
B X
A Y
B X
C Y
B Y
B Y
B Y
B Y
C Z
C Y
B Y
B X
A Y
B Z
A Y
C Y
B Z
A Y
B Y
C Y
C Y
B Y
B Y
C Y
A Z
C Y
C X
C Y
C Y
C Y
B X
C Y
A Y
C Y
B X
B Y
C Y
C X
B Y
B Y
A Y
B X
A Y
A Y
B Y
A Y
C Y
B Y
B Z
A Y
C Z
B Y
B Y
B X
A Y
B X
C Y
A Y
A Y
A Y
B Y
B Z
C Y
C Y
B X
A Y
C Y
A Y
B Y
B X
B Y
B Y
C X
B Y
B Y
B Y
B Y
A Y
A Y
C Y
A X
B X
A Y
B X
B Z
B Z
B Z
B Y
B Y
C Y
C Y
C Z
B Z
B Z
C Y
B Y
B Y
C X
C Y
C Y
B Y
A Y
B X
A Y
B Y
A Y
C Y
B Z
A Y
B Y
B Y
B Y
C Z
A Y
A Y
C Y
B Y
C Y
B Y
C Y
B Z
C Y
C X
C Y
C Y
B Y
A X
C Y
A Y
C X
A X
A Y
B Z
C Y
C Y
B Y
C Y
C Y
B Y
C Y
C Z
B Y
A X
C Y
B X
A Y
C Y
B Z
B Y
B Z
A Y
B Y
B Z
C X
B Y
C Y
A X
C X
A X
B Y
B X
C Y
C Y
B Y
B Y
C Y
B Z
C X
A Y
A Y
B Y
B X
B Y
A Y
B Y
B Z
C X
A Y
B X
C Y
C Y
A X
B Y
C Z
B X
B Y
B Y
C Y
B Z
B Y
B Z
A Y
C Y
C Y
B Y
C Z
C Y
A X
A Y
C Y
B Z
A Y
A X
C Y
B X
B X
A Y
B Y
B Z
A Y
C Y
B Y
C Z
C Y
C Y
B Y
C Y
B Z
C Y
B Y
C Z
A X
A Y
A Z
C Y
B Y
B Z
B X
B X
C Y
A X
C Y
B X
B X
C Y
A Z
B X
A Z
B Y
B Y
A Y
B Z
C Y
B Y
C Y
C Y
A Y
C Y
B Y
C Z
C Z
B Y
B X
C Y
C Y
C Y
B Y
C Y
B Z
B Z
A X
C X
B X
C Z
C Y
B X
B Y
A Z
C Y
B Z
B Y
C Y
B X
B Z
B Y
A Y
B Y
B X
C Y
B Y
B Z
C Y
C Z
C Z
C Y
A Y
C X
B Y
A Y
B Z
C Y
A X
C X
C Z
C Z
A Y
B Y
C X
C Y
C Y
B X
C X
C Y
B X
B X
C Y
B Y
B Y
B Y
B Y    
""".trimIndent()