package com.fivebyfive.fitness.model

import com.fivebyfive.fitness.UnitSpec
import com.fivebyfive.fitness.strategy.routine.RoutineBuilder

class ProgramSpec extends UnitSpec {

  val history = History(Seq(workout1, workout2, workout3))
  val goal = Goal(goalWorkout)

  val testBuilder = new RoutineBuilder {
    override def numReps: Int = 8
    override def numSets: Int = 2
  }

  describe("Program") {
    describe("nextWorkout") {
    }
  }
}
