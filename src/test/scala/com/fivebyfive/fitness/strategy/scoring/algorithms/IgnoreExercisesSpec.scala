package com.fivebyfive.fitness.strategy.scoring.algorithms

import com.fivebyfive.fitness.BaseSpec
import com.fivebyfive.fitness.model.Exercise.{BicepCurls, DragCurls, TricepExtensions}
import com.fivebyfive.fitness.model.{History, RepSet, Routine, Workout}
import com.fivebyfive.fitness.strategy.scoring.Score
import org.joda.time.DateTime

class IgnoreExercisesSpec extends BaseSpec {
  describe("IgnoredExercises") {
    val history = History(Seq())

    describe("score") {
      it("scores a workout with 1 out of 1 exercises ignored a 0.0") {
        val workout = Workout(Seq(
          Routine(BicepCurls, Seq(RepSet(8, Some(15)), RepSet(10, Some(17.5))))
        ), new DateTime("2016-01-08"))

        val ignoredExercises = new IgnoreExercises(Seq(BicepCurls))
        val score = ignoredExercises.score(history, workout)
        score shouldBe Score(ignoredExercises, 0.0)
      }

      it("scores a workout with 1 out of 2 exercises ignored a 0.5") {
        val workout = Workout(Seq(
          Routine(BicepCurls, Seq(RepSet(8, Some(15)), RepSet(10, Some(17.5)))),
          Routine(DragCurls, Seq(RepSet(8, Some(22.5)), RepSet(8, Some(22.5))))
        ), new DateTime("2016-01-08"))

        val ignoredExercises = new IgnoreExercises(Seq(BicepCurls))
        val score = ignoredExercises.score(history, workout)
        score shouldBe Score(ignoredExercises, 0.5)
      }

      it("scores a workout with 0 out of 2 exercises ignored a 1.0") {
        val workout = Workout(Seq(
          Routine(BicepCurls, Seq(RepSet(8, Some(15)), RepSet(10, Some(17.5)))),
          Routine(DragCurls, Seq(RepSet(8, Some(22.5)), RepSet(8, Some(22.5))))
        ), new DateTime("2016-01-08"))

        val ignoredExercises = new IgnoreExercises(Seq(TricepExtensions))
        val score = ignoredExercises.score(history, workout)
        score shouldBe Score(ignoredExercises, 1.0)
      }
    }
  }
}
