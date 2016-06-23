package com.fivebyfive.fitness.model

import org.joda.time.DateTime

case class Workout(routines: Seq[Routine], date: DateTime) extends WorkoutLike {
  lazy val exercises = routines.map(_.exercise)

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
