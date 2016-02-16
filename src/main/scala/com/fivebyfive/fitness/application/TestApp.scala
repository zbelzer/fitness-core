package com.fivebyfive.fitness.application

import com.fivebyfive.fitness.Util._
import com.fivebyfive.fitness.model.Exercise._
import com.fivebyfive.fitness.model._
import org.joda.time.{DateTime, Period}

object TestApp extends App {
  val workout1 =
    Workout(Seq(
      Routine(BicepCurls, Seq(RepSet(8, Some(10)), RepSet(8, Some(10)), RepSet(8, Some(10)))),
      Routine(LegExtensions, Seq(RepSet(8, Some(20)), RepSet(8, Some(20)), RepSet(8, Some(20)))),
      Routine(SitUps, Seq(RepSet(15), RepSet(15), RepSet(15)))
    ), new DateTime("2016-01-08"))

  val workout2 =
    Workout(Seq(
      Routine(BicepCurls, Seq(RepSet(8, Some(15)), RepSet(8, Some(15)), RepSet(8, Some(15)))),
      Routine(LegExtensions, Seq(RepSet(8, Some(30)), RepSet(8, Some(30)), RepSet(8, Some(30)))),
      Routine(SitUps, Seq(RepSet(20), RepSet(20), RepSet(20)))
    ), new DateTime("2016-01-15"))

  val goalWorkout =
    Workout(Seq(
      Routine(BicepCurls, Seq(RepSet(8, Some(22.5)), RepSet(8, Some(22.5)), RepSet(8, Some(22.5)))),
      Routine(LegExtensions, Seq(RepSet(8, Some(40)), RepSet(8, Some(40)), RepSet(8, Some(40)))),
      Routine(SitUps, Seq(RepSet(30), RepSet(30), RepSet(30)))
    ), new DateTime("2016-01-30"))

  val history = History(Seq(workout1, workout2))
  val goal = Goal(goalWorkout)
  val program = new Program(Some(goal))


  val workout =
    program.nextWorkout(new DateTime("2016-01-22"), history).getOrElse {
      throw new Exception("A Workout could not be generated")
    }
  //  val workoutPlan =
  //    dateRange(workout2.date, goalWorkout.date, Period.weeks(1)).drop(1).flatMap { date =>
  //      program.nextWorkout(date, history)
  //    }

  println("Example Workout")
  println(workout)
  println(s"Routines ${workout.routines.size}")
  println(s"Total duration ${workout.duration}")
//  workoutPlan.foreach { workout =>
//    println(workout)
//  }
}