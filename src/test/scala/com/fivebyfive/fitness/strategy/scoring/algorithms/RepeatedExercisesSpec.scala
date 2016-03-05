package com.fivebyfive.fitness.strategy.scoring.algorithms

import com.fivebyfive.fitness.UnitSpec
import com.fivebyfive.fitness.model.Exercise._
import com.fivebyfive.fitness.model.{History, RepSet, Routine, Workout}
import com.fivebyfive.fitness.strategy.scoring.Score
import org.joda.time.DateTime

class RepeatedExercisesSpec extends UnitSpec {
  describe("RepeatedExercises") {
    describe("score") {
      it("scores a completely unique workout with 1") {
        val workout = Workout(Seq(
          Routine(DragCurls, Seq(RepSet(8, Some(22.5)), RepSet(8, Some(22.5)))),
          Routine(StandingTwist, Seq(RepSet(10, Some(40)), RepSet(10, Some(40)))),
          Routine(PullUps, Seq(RepSet(30), RepSet(30), RepSet(30)))
        ), new DateTime("2016-01-30"))

        val history = History(Seq(workout1))
        val score = RepeatedExercises.score(history, workout)

        score shouldBe Score(RepeatedExercises, 1.0)
      }

      it("scores a workout with half repeats a 0.5") {
        val workout = Workout(Seq(
          Routine(BicepCurls, Seq(RepSet(8, Some(22.5)), RepSet(8, Some(22.5)))),
          Routine(SitUps, Seq(RepSet(10, Some(40)), RepSet(10, Some(40)))),
          Routine(PullUps, Seq(RepSet(30), RepSet(30), RepSet(30))),
          Routine(ReverseLunges, Seq(RepSet(30), RepSet(30), RepSet(30)))
        ), new DateTime("2016-01-30"))

        val workout2 = Workout(Seq(
          Routine(DragCurls, Seq(RepSet(8, Some(22.5)), RepSet(8, Some(22.5)))),
          Routine(SitUps, Seq(RepSet(10, Some(40)), RepSet(10, Some(40)))),
          Routine(PullUps, Seq(RepSet(30), RepSet(30), RepSet(30))),
          Routine(LegExtensions, Seq(RepSet(30), RepSet(30), RepSet(30)))
        ), new DateTime("2016-01-30"))

        val history = History(Seq(workout))
        val score = RepeatedExercises.score(history, workout2)

        score shouldBe Score(RepeatedExercises, 0.5)
      }

      it("scores a completely duplicate workout with 0") {
        val history = History(Seq(workout1))
        val score = RepeatedExercises.score(history, workout1)

        score shouldBe Score(RepeatedExercises, 0.0)
      }

    }
  }
}
