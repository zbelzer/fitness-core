package com.fivebyfive.fitness.model

import com.fivebyfive.fitness.UnitSpec

class RepSetSpec extends UnitSpec {
  describe("RepSet") {
    describe("inverted") {
      it("is true when weight is given and less than 0") {
        RepSet(1, Some(-10)).inverted shouldBe true
      }

      it("is false when weight is given and greater than 0") {
        RepSet(1, Some(10)).inverted shouldBe false
      }

      it("is false when weight is given and equal to 0") {
        RepSet(1, Some(0)).inverted shouldBe false
      }

      it("is false when weight is not given") {
        RepSet(1, None).inverted shouldBe false
      }
    }

  }
}
