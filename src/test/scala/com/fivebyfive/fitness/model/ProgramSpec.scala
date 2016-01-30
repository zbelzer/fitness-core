package com.fivebyfive.fitness.model

import com.fivebyfive.fitness.UnitSpec
import org.joda.time.DateTime

class ProgramSpec extends UnitSpec {

  val history = History(Seq(workout1, workout2))
  val goal = Goal(goalWorkout)
  val program = new Program(Some(goal))

  describe("Program") {

    describe("nextWorkout") {
      val today = new DateTime("2016-01-15")
      val workout = program.nextWorkout(today, history)

      val expectedRoutines = Seq(
        Routine(curls, Seq(RepSet(8, Some(15.5)), RepSet(8, Some(15.5)))),
        Routine(legExtensions, Seq(RepSet(10, Some(30.5)), RepSet(10, Some(30.5)))),
        Routine(sitUps, Seq(RepSet(20), RepSet(20), RepSet(20)))
      )

      workout.routines shouldBe expectedRoutines
    }
  }
}
