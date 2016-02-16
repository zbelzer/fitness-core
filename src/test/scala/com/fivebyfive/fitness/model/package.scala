package com.fivebyfive.fitness

import com.fivebyfive.fitness.model.Exercise._
import org.joda.time.DateTime

package object model {
  val workout1 =
    Workout(Seq(
      Routine(BicepCurls, Seq(RepSet(8, Some(10)), RepSet(8, Some(10)))),
      Routine(LegExtensions, Seq(RepSet(8, Some(20)), RepSet(8, Some(20)))),
      Routine(SitUps, Seq(RepSet(15), RepSet(15)))
    ), new DateTime("2016-01-01"))

  val workout2 =
    Workout(Seq(
      Routine(BicepCurls, Seq(RepSet(8, Some(15)), RepSet(8, Some(15)))),
      Routine(LegExtensions, Seq(RepSet(8, Some(30)), RepSet(8, Some(30)))),
      Routine(SitUps, Seq(RepSet(20), RepSet(20)))
    ), new DateTime("2016-01-08"))

  val workout3 =
    Workout(Seq(
      Routine(BicepCurls, Seq(RepSet(8, Some(20)), RepSet(8, Some(20)))),
      Routine(LegExtensions, Seq(RepSet(8, Some(40)), RepSet(8, Some(40)))),
      Routine(SitUps, Seq(RepSet(25), RepSet(25)))
    ), new DateTime("2016-01-15"))

  val goalWorkout =
    Workout(Seq(
      Routine(BicepCurls, Seq(RepSet(8, Some(22.5)), RepSet(8, Some(22.5)))),
      Routine(LegExtensions, Seq(RepSet(10, Some(40)), RepSet(10, Some(40)))),
      Routine(SitUps, Seq(RepSet(30), RepSet(30), RepSet(30)))
    ), new DateTime("2016-01-30"))

}
