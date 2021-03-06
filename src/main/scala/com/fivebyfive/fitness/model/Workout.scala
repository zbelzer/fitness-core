package com.fivebyfive.fitness.model

import java.util.concurrent.TimeUnit

import org.joda.time.DateTime

import scala.collection.immutable.ListMap
import scala.concurrent.duration.Duration

object Workout {
  def apply(routines: Iterable[Routine], date: String): Workout = {
    new Workout(routines, new DateTime(date))
  }

  def apply(routines: ListMap[Exercise, ListMap[Int, Int]], date: String): Workout = {
    new Workout(
      routines.map { case (e, r) => Routine(e, r)}
      , new DateTime(date))
  }

//  def apply(routines: ListMap[Exercise, Seq[Int]], date: String): Workout = {
//    new Workout(
//      routines.map { case (e, r) => Routine(e, r)}
//      , new DateTime(date))
//  }
}

case class Workout(routines: Iterable[Routine], date: DateTime) extends WorkoutLike {
  lazy val exercises = routines.map(_.exercise)

  lazy val duration: Duration = {
    val sum = routines.map(_.duration).sum
    Duration(sum, TimeUnit.SECONDS)
  }

  def routineForExercise(exercise: Exercise): Option[Routine] = {
    routines.find(r => r.exercise == exercise)
  }

  lazy val routinesByExercise: Map[Exercise, Routine] = {
    routines.map { r => (r.exercise, r)}.toMap
  }

  lazy val volumeByMuscle: Map[Muscle, Double] = {
    routines.foldLeft(Map[Muscle, Double]()) { case(l, r) =>
      r.volumeByMuscle.map { case(m, v) =>
        m -> (l.getOrElse(m, 0.0) + v)
      }
    }
  }

  override def toString: String = {
    val dateString = date.toString("Y-MM-dd")

    s"""Workout on $dateString
      |
      |${routines.map(_.toString).mkString("\n")}
    """.stripMargin
  }
}
