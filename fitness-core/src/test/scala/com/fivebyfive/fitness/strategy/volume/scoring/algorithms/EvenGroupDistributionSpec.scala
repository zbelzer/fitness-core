package com.fivebyfive.fitness.strategy.volume.scoring.algorithms

import com.fivebyfive.fitness.UnitSpec
import com.fivebyfive.fitness.model.Exercise._
import com.fivebyfive.fitness.model.{History, RepSet, Routine, Workout}
import com.fivebyfive.fitness.strategy.scoring.Score
import com.fivebyfive.fitness.strategy.scoring.algorithms.EvenGroupDistribution
import org.joda.time.DateTime

class EvenGroupDistributionSpec extends UnitSpec {
  describe("EvenGroupDistribution") {
    val history = History(Seq())

    describe("score") {
      it("only counts disctinct muscle groups") {
        val workout = Workout(Seq(
          Routine(BicepCurls, Seq(RepSet(8, Some(22.5)), RepSet(8, Some(22.5)))),
          Routine(DragCurls, Seq(RepSet(8, Some(22.5)), RepSet(8, Some(22.5))))
        ), new DateTime("2016-01-30"))

        val score = EvenGroupDistribution.score(history, workout)

        score shouldBe Score(EvenGroupDistribution, 1.0/3.0)
      }

      it("scores a workout with just upper body 1/3") {
        val workout = Workout(Seq(
          Routine(DragCurls, Seq(RepSet(8, Some(22.5)), RepSet(8, Some(22.5))))
        ), new DateTime("2016-01-30"))

        val score = EvenGroupDistribution.score(history, workout)

        score shouldBe Score(EvenGroupDistribution, 1.0/3.0)
      }

      it("scores a workout with upper and lower body 2/3") {
        val workout = Workout(Seq(
          Routine(DragCurls, Seq(RepSet(8, Some(22.5)), RepSet(8, Some(22.5)))),
          Routine(LegExtensions, Seq(RepSet(8, Some(22.5)), RepSet(8, Some(22.5))))
        ), new DateTime("2016-01-30"))

        val score = EvenGroupDistribution.score(history, workout)

        score shouldBe Score(EvenGroupDistribution, 2.0/3.0)
      }

      it("scores a workout with all three groups 1.0") {
        val workout = Workout(Seq(
          Routine(DragCurls, Seq(RepSet(8, Some(22.5)), RepSet(8, Some(22.5)))),
          Routine(LegExtensions, Seq(RepSet(8, Some(22.5)), RepSet(8, Some(22.5)))),
          Routine(SitUps, Seq(RepSet(8, Some(22.5)), RepSet(8, Some(22.5))))
        ), new DateTime("2016-01-30"))

        val score = EvenGroupDistribution.score(history, workout)

        score shouldBe Score(EvenGroupDistribution, 1.0)
      }
    }
  }
}
