package com.fivebyfive.fitness.model

import com.fivebyfive.fitness.BaseSpec
import com.fivebyfive.fitness.strategy.scoring.Scoring
import com.fivebyfive.fitness.strategy.scoring.algorithms.{EvenGroupDistribution, IgnoreExercises, RepeatedExercises}
import org.apache.commons.io.IOUtils
import org.joda.time.DateTime

class ProgramSpec extends BaseSpec {

  val scoring = Scoring(Seq(
    EvenGroupDistribution,
    RepeatedExercises,
    IgnoreExercises(Nil)
  ))

  val program = Program(scoring = scoring, settings = WorkoutSettings.default)
  val today = new DateTime("2016-08-30")

  describe("Program") {
    describe("nextWorkout") {
      it("should not blow up") {
        val rawData = getClass.getResourceAsStream("/fixtures/down.csv")
        val history = History.fromCSV(IOUtils.toByteArray(rawData))

        program.nextWorkout(today, history).getOrElse {
          throw new Exception("A Workout could not be generated")
        }
      }

      it("doesn't break on this file") {
        val rawData = getClass.getResourceAsStream("/fixtures/error.csv")
        val history = History.fromCSV(IOUtils.toByteArray(rawData))

        history.workouts.size shouldBe 7

        program.nextWorkout(today, history).getOrElse {
          throw new Exception("A Workout could not be generated")
        }
      }

      it("doesn't break on this file either") {
        val rawData = getClass.getResourceAsStream("/fixtures/error2.csv")
        val history = History.fromCSV(IOUtils.toByteArray(rawData))

        history.workouts.size shouldBe 2

        program.nextWorkout(today, history).getOrElse {
          throw new Exception("A Workout could not be generated")
        }

      }
    }
  }
}
