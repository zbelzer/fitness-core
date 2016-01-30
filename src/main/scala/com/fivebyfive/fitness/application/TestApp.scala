package com.fivebyfive.fitness.application

import com.fivebyfive.fitness.model._
import org.joda.time.{Days, Period, DateTime}

object TestApp extends App {
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

  val history = History(Seq(workout1, workout2))
  val goal = Goal(goalWorkout)
  val program = new Program(Some(goal))

  val workoutPlan =
    dateRange(workout2.date, goalWorkout.date, Period.days(1)).map { date =>
      program.nextWorkout(date, history)
    }

  workoutPlan.foreach { workout =>
    println(workout)
  }

  def dateRange(start: DateTime, end: DateTime, step: Period): Iterator[DateTime] =
    Iterator.iterate(start)(_.plus(step)).takeWhile(!_.isAfter(end))
}