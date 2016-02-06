package com.fivebyfive.fitness.model

import com.fivebyfive.fitness.UnitSpec

class RoutineSpec extends UnitSpec {
  describe("Routine") {
    describe("volume") {
      it("calculates the sum of the product of reps and sets") {
        val routine = Routine(curls, Seq(RepSet(8, Some(15)), RepSet(10, Some(17.5))))
        routine.volume shouldBe 8 * 15 + 10 * 17.5
      }

      it("calculates the sum of the reps") {
        val routine = Routine(curls, Seq(RepSet(8), RepSet(10)))
        routine.volume shouldBe 8 + 10
      }
    }

  }
}
