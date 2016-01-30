package com.fivebyfive.fitness.model

import com.fivebyfive.fitness.UnitSpec

class WorkoutPlanSpec extends UnitSpec {
  describe("WorkoutPlan") {
    it("imports a sample workout") {
      val rawData = getClass.getResource("/fixtures/sample.csv")
      val workout = WorkoutPlan.fromCSV(rawData)

      workout.routines shouldBe Seq(
        Routine(Exercise("Bicep Curls"), Seq(RepSet(10, Some(25.0)), RepSet(10, Some(25.0)), RepSet(10, Some(25.0)))),
        Routine(Exercise("Leg Extensions"), Seq(RepSet(12, Some(40.0)), RepSet(12, Some(40.0)), RepSet(12, Some(40.0)), RepSet(12, Some(40.0)))),
        Routine(Exercise("Sit Ups"), Seq(RepSet(30, None), RepSet(30, None), RepSet(30, None)))
      )
    }
  }
}
