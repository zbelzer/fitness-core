package com.fivebyfive.fitness.strategy

import com.fivebyfive.fitness.UnitSpec
import com.fivebyfive.fitness.model.RepSet

class LinearSimpleSpec extends UnitSpec {
  describe("LinearSimple") {
    describe("nextSet") {
      it("provides next set reps by linear progression") {
        val startSet = RepSet(2)
        val endSet = RepSet(12)

        val result1 = LinearSimple.nextSet(startSet, endSet, 8)
        result1.reps shouldBe 3

        val result2 = LinearSimple.nextSet(startSet, endSet, 4)
        result2.reps shouldBe 4
      }
    }

    it("provides next set reps and weight by linear progression") {
      val startSet = RepSet(2, Some(10))
      val endSet = RepSet(12, Some(34))

      val result1 = LinearSimple.nextSet(startSet, endSet, 8)
      result1.reps shouldBe 3
      result1.weight shouldBe Some(13)

      val result2 = LinearSimple.nextSet(startSet, endSet, 4)
      result2.reps shouldBe 4
      result2.weight shouldBe Some(16)
    }

    it("considers a missing start weight as 0 for progressions") {
      val startSet = RepSet(2)
      val endSet = RepSet(12, Some(24))

      val result1 = LinearSimple.nextSet(startSet, endSet, 8)
      result1.weight shouldBe Some(3)

      val result2 = LinearSimple.nextSet(startSet, endSet, 4)
      result2.weight shouldBe Some(6)
    }

    it("provides the end set's values if start and end are equal") {
      val startSet = RepSet(12)
      val endSet = RepSet(12)

      val result1 = LinearSimple.nextSet(startSet, endSet, 8)
      result1.reps shouldBe 12

      val result2 = LinearSimple.nextSet(startSet, endSet, 4)
      result2.reps shouldBe 12
    }

    it("provides the end set's values if zero provided for increments") {
      val startSet = RepSet(12)
      val endSet = RepSet(12)

      val result1 = LinearSimple.nextSet(startSet, endSet, 0)
      result1.reps shouldBe 12
    }
  }
}
