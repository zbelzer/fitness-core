package com.fivebyfive.fitness.model

import com.fivebyfive.fitness.UnitSpec
import com.fivebyfive.fitness.strategy.routine.{RoutineBuilder, MidRepBuilder}
import org.joda.time.DateTime

class ProgramSpec extends UnitSpec {

  val history = History(Seq(workout1, workout2, workout3))
  val goal = Goal(goalWorkout)

  val testBuilder = new RoutineBuilder {
    override def numReps: Int = 8
    override def numSets: Int = 2
  }

  describe("Program") {
    describe("nextWorkout") {
      it("builds the same workout as in history") {
        val program = new Program
        val today = new DateTime("2016-01-15")
        val workout = program.nextWorkout(today, history, testBuilder)

        workout shouldBe workout3
      }

      it("extrapolates a new workout") {
        val program = new Program
        val today = new DateTime("2016-01-22")
        val workout = program.nextWorkout(today, history, testBuilder)

        val expectedRoutines = Seq(
          Routine(curls, Seq(RepSet(8, Some(25)), RepSet(8, Some(25)))),
          Routine(legExtensions, Seq(RepSet(8, Some(50)), RepSet(8, Some(50)))),
          Routine(sitUps, Seq(RepSet(30), RepSet(30)))
        )
        val foo = Workout(expectedRoutines, today)

        workout shouldBe foo
//        workout.routines.toList shouldBe expectedRoutines
      }
    }
  }
}
