package com.fivebyfive.fitness.model

import com.fivebyfive.fitness.UnitSpec

class ProgramSpec extends UnitSpec {

  val history = History(Seq(workout1, workout2, workout3))
  val goal = Goal(goalWorkout)

  describe("Program") {
    describe("nextWorkout") {
    }
  }
}
