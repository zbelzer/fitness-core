package com.fivebyfive.fitness.model

import com.fivebyfive.fitness.UnitSpec

class GoalSpec extends UnitSpec {
  describe("Goal") {
    describe("date") {
      it("returns the date from the workout") {
        val goal = Goal(workout1)
        goal.date shouldBe workout1.date
      }
    }
  }

}
