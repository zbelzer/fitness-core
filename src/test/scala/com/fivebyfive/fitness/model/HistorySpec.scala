package com.fivebyfive.fitness.model

import com.fivebyfive.fitness.UnitSpec
import org.joda.time.DateTime

class HistorySpec extends UnitSpec {
  val history = History(Seq(workout1, workout2))

  describe("History") {
    describe("fromCSV") {
      it("imports workout history") {
        val rawData = getClass.getResource("/fixtures/twoWorkouts.csv")
        val history = History.fromCSV(rawData)

        history.workouts.size shouldBe 2

        val first = history.workouts.toSeq(0)
        first.date shouldBe new DateTime("2015-12-01")
        first.routines shouldBe Seq(
          Routine(Exercise("Bicep Curls"), Seq(RepSet(10, Some(25.0)), RepSet(10, Some(25.0)), RepSet(10, Some(25.0)))),
          Routine(Exercise("Leg Extensions"), Seq(RepSet(12, Some(40.0)), RepSet(12, Some(40.0)), RepSet(12, Some(40.0)), RepSet(12, Some(40.0)))),
          Routine(Exercise("Sit Ups"), Seq(RepSet(30, None), RepSet(30, None), RepSet(30, None)))
        )

        val second = history.workouts.toSeq(1)
        second.date shouldBe new DateTime("2015-12-08")
        second.routines shouldBe Seq(
          Routine(Exercise("Bicep Curls"), Seq(RepSet(10, Some(25.0)), RepSet(10, Some(25.0)), RepSet(10, Some(25.0)))),
          Routine(Exercise("Leg Extensions"), Seq(RepSet(12, Some(45.0)), RepSet(12, Some(45.0)), RepSet(12, Some(45.0)), RepSet(12, Some(45.0)))),
          Routine(Exercise("Sit Ups"), Seq(RepSet(35, None), RepSet(35, None), RepSet(35, None)))
        )
      }
    }
    describe("byExercise") {
      it("groups routines by exercise within history") {
        val result = history.byExercise

        val expectedCurls = Seq(
          Routine(curls, Seq(RepSet(8, Some(10)), RepSet(8, Some(10)))),
          Routine(curls, Seq(RepSet(8, Some(15)), RepSet(8, Some(15))))
        )
        val expectedLegs = Seq(
          Routine(legExtensions, Seq(RepSet(8, Some(20)), RepSet(8, Some(20)))),
          Routine(legExtensions, Seq(RepSet(8, Some(30)), RepSet(8, Some(30))))
        )
        val expectedSitups = Seq(
          Routine(sitUps, Seq(RepSet(15), RepSet(15))),
          Routine(sitUps, Seq(RepSet(20), RepSet(20)))
        )

        result.getOrElse(curls, Nil) shouldBe expectedCurls
        result.getOrElse(legExtensions, Nil) shouldBe expectedLegs
        result.getOrElse(sitUps, Nil) shouldBe expectedSitups
      }
    }
  }
}
