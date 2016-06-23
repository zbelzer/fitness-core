package com.fivebyfive.fitness.strategy.routine

import com.fivebyfive.fitness.UnitSpec
import com.fivebyfive.fitness.model.Exercise.BicepCurls
import com.fivebyfive.fitness.model.{RepSet, Routine}

class TestBuilder extends RoutineBuilder {
  val numSets = 3
  val numReps = 5
}

class RoutineBuilderSpec extends UnitSpec {
  describe("RoutineBuilder") {
    describe("buildRoutine") {
      it("fits desired volume into given sets and reps") {
        val builder = new TestBuilder

        val sets = Seq(
          RepSet(reps = 5, weight = Some(10)),
          RepSet(reps = 5, weight = Some(10)),
          RepSet(reps = 5, weight = Some(10))
        )

        val previousRoutine = Routine(BicepCurls, sets)
        val targetVolume = 165

        val result = builder.buildRoutine(previousRoutine, targetVolume)
        result.sets.size shouldBe 3
        result.sets.foreach { s => s.reps shouldEqual 5}
        result.sets.foreach { s => s.weight shouldEqual Some(11)}
      }
    }

    it("adjusts reps when no weight provided") {
      val builder = new TestBuilder

      val sets = Seq(
        RepSet(reps = 5),
        RepSet(reps = 5),
        RepSet(reps = 5)
      )

      val previousRoutine = Routine(BicepCurls, sets)
      val targetVolume = 18

      val result = builder.buildRoutine(previousRoutine, targetVolume)
      result.sets.size shouldBe 3
      result.sets.foreach { s => s.reps shouldEqual 6}
      result.sets.foreach { s => s.weight shouldEqual None }
    }

    it("handles negative volumes") {
      val builder = new TestBuilder

      val sets = Seq(
        RepSet(reps = 5, weight = Some(-10)),
        RepSet(reps = 5, weight = Some(-10)),
        RepSet(reps = 5, weight = Some(-10))
      )

      val previousRoutine = Routine(BicepCurls, sets)
      val targetVolume = -135

      val result = builder.buildRoutine(previousRoutine, targetVolume)
      result.sets.size shouldBe 3
      result.sets.foreach { s => s.reps shouldEqual 5}
      result.sets.foreach { s => s.weight shouldEqual Some(-9) }
    }
  }
}

