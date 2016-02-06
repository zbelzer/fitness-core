package com.fivebyfive.fitness.strategy.volume

import com.fivebyfive.fitness.UnitSpec
import org.joda.time.DateTime

class VolumeGeneratorSpec extends UnitSpec {
  import com.fivebyfive.fitness.model._

  describe("VolumeGenerator") {
    describe("nextVolume") {
      it("provides next set reps by linear progression") {
        val history = History(Seq(workout1, workout2, workout3))
        val generator = new VolumeGenerator(history)

        val next = generator.nextVolume(curls, new DateTime("2016-01-22"))
        next shouldBe 400
      }
    }
  }
}
