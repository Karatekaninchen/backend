package sc.plugin2021

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import sc.plugin2021.util.Constants
import sc.plugin2021.util.GameRuleLogic
import sc.shared.PlayerScore
import sc.shared.ScoreCause
import java.math.BigDecimal

class GameTest: WordSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    "A Game" When {
        val game = Game()
        val state = game.gameState
        "started with two players" should {
            val one = game.onPlayerJoined()
            one.color shouldBe Team.ONE
            val two = game.onPlayerJoined()
            two.color shouldBe Team.TWO
            game.start()
            "finish without issues and return correct scores" {
                while (true) {
                    try {
                        val condition = game.checkWinCondition()
                        if (condition != null) {
                            println("Game ended with $condition")
                            break
                        }
                        val moves = GameRuleLogic.getPossibleMoves(state)
                        moves shouldNotBe emptySet<SetMove>()
                        game.onAction(state.currentPlayer, moves.random())
                    } catch (e: Exception) {
                        println(e.message)
                        break
                    }
                }
                
                val scores = game.playerScores
                val score1 = game.getScoreFor(one)
                val score2 = game.getScoreFor(two)
                scores shouldBe listOf(score1, score2)
                score1.cause shouldBe ScoreCause.REGULAR
                score2.cause shouldBe ScoreCause.REGULAR
                
                val points1 = BigDecimal(state.getPointsForPlayer(one.color))
                val points2 = BigDecimal(state.getPointsForPlayer(two.color))
                when {
                    points1 < points2 -> {
                        score1.parts shouldBe listOf(BigDecimal(0), points1)
                        score2.parts shouldBe listOf(BigDecimal(2), points2)
                    }
                    points1 > points2 -> {
                        score1.parts shouldBe listOf(BigDecimal(2), points1)
                        score2.parts shouldBe listOf(BigDecimal(0), points2)
                    }
                    points1 == points2 -> {
                        score1.parts shouldBe listOf(BigDecimal(1), points1)
                        score2.parts shouldBe listOf(BigDecimal(1), points2)
                    }
                }
            }
            "end in a draw when everyone skips" {
                for (s in 0 until 4)
                    game.onAction(state.currentPlayer, GameRuleLogic.streamPossibleMoves(state).first())
                
                shouldNotThrowAny {
                    while (!game.checkGameOver()) {
                        game.onAction(state.currentPlayer, SkipMove(state.currentColor))
                    }
                }
                shouldThrow<java.lang.IndexOutOfBoundsException> {
                    game.onAction(state.currentPlayer, SkipMove(state.currentColor))
                }
                
                game.playerScores shouldContainExactly List(2) {
                    PlayerScore(ScoreCause.REGULAR, "", Constants.DRAW_SCORE, 10)
                }
            }
        }
    }
})
