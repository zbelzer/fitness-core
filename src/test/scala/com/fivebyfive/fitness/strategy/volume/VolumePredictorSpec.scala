package com.fivebyfive.fitness.strategy.volume

import com.fivebyfive.fitness.BaseSpec
import com.fivebyfive.fitness.model.Exercise.BicepCurls
import org.joda.time.DateTime

class VolumePredictorSpec extends BaseSpec {
  describe("VolumePredictor") {
    describe("next") {
      it("provides next value by linear progression") {
        val data = Seq(
          (new DateTime("2016-01-01"), 100.0),
          (new DateTime("2016-01-08"), 200.0),
          (new DateTime("2016-01-15"), 300.0)
        )

        val next = VolumePredictor.next(data, new DateTime("2016-01-22"))
        next shouldBe 400
      }
    }
  }
}
