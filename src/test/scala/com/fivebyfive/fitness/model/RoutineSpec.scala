package com.fivebyfive.fitness.model

import com.fivebyfive.fitness.UnitSpec
import com.fivebyfive.fitness.model.Exercise._
import com.fivebyfive.fitness.model.Muscle._

class RoutineSpec extends UnitSpec {
  describe("Routine") {
    describe("totalVolume") {
      it("calculates the sum of the product of reps and sets") {
        val routine = Routine(BicepCurls, Seq(RepSet(8, Some(15)), RepSet(10, Some(17.5))))
        routine.volume shouldBe 8 * 15 + 10 * 17.5
      }

      it("calculates the sum of the reps") {
        val routine = Routine(BicepCurls, Seq(RepSet(8), RepSet(10)))
        routine.volume shouldBe 8 + 10
      }
    }

    describe("volumeByMuscle") {
      it("breaks down volume by percentages") {
        val routine = Routine(BicepCurls, Seq(RepSet(8, Some(15)), RepSet(10, Some(17.5))))
        val volumeByMuscle = routine.volumeByMuscle

        val brachiiVol = (8 * 15 + 10 * 17.5) * 0.7
        val brachialisVol = (8 * 15 + 10 * 17.5) * 0.15
        val brachioradialisVol = (8 * 15 + 10 * 17.5) * 0.15

        volumeByMuscle.values.sum shouldBe routine.volume
        volumeByMuscle shouldBe Map(
          BicepsBrachii -> brachiiVol,
          Brachialis -> brachialisVol,
          Brachioradialis -> brachioradialisVol
        )
      }
    }

  }
}
