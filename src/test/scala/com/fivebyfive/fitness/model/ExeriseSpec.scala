package com.fivebyfive.fitness.model

import com.fivebyfive.fitness.UnitSpec
import com.fivebyfive.fitness.model.Exercise.FloorRearNeckBridge

class ExeriseSpec extends UnitSpec {
  describe("Exercise") {
    describe("prettyName") {
      it("Gives a palatable name to exercises") {
        FloorRearNeckBridge.prettyName shouldBe "Floor Rear Neck Bridge"
      }

    }
  }

}
