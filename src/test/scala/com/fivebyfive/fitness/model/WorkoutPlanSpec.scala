package com.fivebyfive.fitness.model

import com.fivebyfive.fitness.BaseSpec
import com.fivebyfive.fitness.model.Exercise.{SitUps, LegExtensions, BicepCurls}

class WorkoutPlanSpec extends BaseSpec {
  describe("WorkoutPlan") {
    it("imports a sample workout") {
      val rawData = getClass.getResource("/fixtures/sample.csv")
      val workout = WorkoutPlan.fromCSV(rawData)

      workout.routines shouldBe Seq(
        Routine(BicepCurls, Seq(RepSet(10, Some(25.0)), RepSet(10, Some(25.0)), RepSet(10, Some(25.0)))),
        Routine(LegExtensions, Seq(RepSet(12, Some(40.0)), RepSet(12, Some(40.0)), RepSet(12, Some(40.0)), RepSet(12, Some(40.0)))),
        Routine(SitUps, Seq(RepSet(30, None), RepSet(30, None), RepSet(30, None)))
      )
    }
  }
}
