package com.fivebyfive.fitness.model

import java.net.URL

import kantan.csv.RowDecoder
import kantan.csv.ops._


class WorkoutPlan(val routines: Iterable[Routine]) extends WorkoutLike {
  def routinesForExercise(exercise: Exercise): Iterable[Routine] = {
    routines.filter(r => r.exercise == exercise)
  }

  override def toString: String = {
    s"""Workout
        |
      |${routines.map(_.toString).mkString("\n")}
    """.stripMargin

  }
}

object WorkoutPlan {
  def fromCSV(data: URL): WorkoutPlan = {
    case class Row(exerciseName: String, reps: Int, weight: Option[Double] = None)
    implicit val rowDecoder = RowDecoder.decoder(0, 1, 2)(Row.apply)

    val results = data.asCsvReader[Row](',', header = true).map { w => w.get }.toList

    val routines =
      results.groupBy(_.exerciseName).map { case (exerciseName, rows) =>
        val name = exerciseName.replaceAll(" ", "")
        Routine(Exercise.lookup(name), rows.map { r => RepSet(r.reps, r.weight) })
      }

    new WorkoutPlan(routines)
  }
}
