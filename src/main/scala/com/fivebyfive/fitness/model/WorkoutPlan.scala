package com.fivebyfive.fitness.model

import java.net.URL

import tabulate.RowDecoder
import tabulate.ops._

class WorkoutPlan(val routines: Iterable[Routine]) {
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
    implicit val rowDecoder = RowDecoder.decoder3(Row.apply)(0, 1, 2)

    val results = data.asCsvReader[Row](',', header = true).map { w => w.get }.toList

    val routines =
      results.groupBy(_.exerciseName).map { case (exerciseName, rows) =>
        val name = exerciseName.replaceAll(" ", "")
        Routine(Exercise.withNameInsensitive(name), rows.map { r => RepSet(r.reps, r.weight) })
      }

    new WorkoutPlan(routines)
  }
}
