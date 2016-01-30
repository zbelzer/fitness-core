package com.fivebyfive.fitness

import org.joda.time.DateTime

package object model {
  val curls = Exercise("Bicep Curls")
  val legExtensions = Exercise("Leg Extensions")
  val sitUps = Exercise("Sit Ups")

  val workout1 =
    Workout(Seq(
      Routine(curls, Seq(RepSet(8, Some(10)), RepSet(8, Some(10)))),
      Routine(legExtensions, Seq(RepSet(10, Some(20)), RepSet(10, Some(20)))),
      Routine(sitUps, Seq(RepSet(15), RepSet(15), RepSet(15)))
    ), new DateTime("2016-01-01"))

  val workout2 =
    Workout(Seq(
      Routine(curls, Seq(RepSet(8, Some(15)), RepSet(8, Some(15)))),
      Routine(legExtensions, Seq(RepSet(10, Some(30)), RepSet(10, Some(30)))),
      Routine(sitUps, Seq(RepSet(20), RepSet(20), RepSet(20)))
    ), new DateTime("2016-01-08"))

  val goalWorkout =
    Workout(Seq(
      Routine(curls, Seq(RepSet(8, Some(22.5)), RepSet(8, Some(22.5)))),
      Routine(legExtensions, Seq(RepSet(10, Some(40)), RepSet(10, Some(40)))),
      Routine(sitUps, Seq(RepSet(30), RepSet(30), RepSet(30)))
    ), new DateTime("2016-01-30"))

}
