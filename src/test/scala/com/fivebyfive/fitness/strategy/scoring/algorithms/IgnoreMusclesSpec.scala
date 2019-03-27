package com.fivebyfive.fitness.strategy.scoring.algorithms

import com.fivebyfive.fitness.BaseSpec
import com.fivebyfive.fitness.model.Exercise.{BicepCurls, DragCurls, TricepExtensions}
import com.fivebyfive.fitness.model.Muscle.{BicepsBrachii, TricepsBrachii}
import com.fivebyfive.fitness.model.{History, RepSet, Routine, Workout}
import com.fivebyfive.fitness.strategy.scoring.Score
import org.joda.time.DateTime

class IgnoreMusclesSpec extends BaseSpec {
  describe("IgnoredMuscles") {
    val history = History(Seq())

    describe("score") {
      it("scores a workout with 1 exercise containing the 1 ignored muscle") {
        val workout = Workout(Seq(
          Routine(BicepCurls, Seq(RepSet(8, Some(15)), RepSet(10, Some(17.5))))
        ), new DateTime("2016-01-08"))

        val ignoredMuscles = new IgnoreMuscles(Seq(BicepsBrachii))
        val score = ignoredMuscles.score(history, workout)
        score shouldBe Score(ignoredMuscles, 0.0)
      }

      it("scores a workout with 1 exercise of 2 containing the 1 ignored muscle") {
        val workout = Workout(Seq(
          Routine(BicepCurls, Seq(RepSet(8, Some(15)), RepSet(10, Some(17.5)))),
          Routine(TricepExtensions, Seq(RepSet(8, Some(15)), RepSet(10, Some(17.5))))
        ), new DateTime("2016-01-08"))

        val ignoredMuscles = new IgnoreMuscles(Seq(TricepsBrachii))
        val score = ignoredMuscles.score(history, workout)
        score shouldBe Score(ignoredMuscles, 0.5)
      }

      it("scores a workout with 1 exercise not containing the 1 ignored muscle") {
        val workout = Workout(Seq(
          Routine(BicepCurls, Seq(RepSet(8, Some(15)), RepSet(10, Some(17.5))))
        ), new DateTime("2016-01-08"))

        val ignoredMuscles = new IgnoreMuscles(Seq(TricepsBrachii))
        val score = ignoredMuscles.score(history, workout)
        score shouldBe Score(ignoredMuscles, 1.0)
      }
    }
  }
}
