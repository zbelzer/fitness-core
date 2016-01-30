package com.fivebyfive.fitness.model

import java.net.URL

import org.joda.time.DateTime
import tabulate.RowDecoder
import tabulate.ops._

case class History(workouts: Iterable[Workout]) {
  def byExercise: Map[Exercise, Seq[Routine]] = {
    workouts.foldLeft(Map[Exercise, Seq[Routine]]()) { (m, workout) =>
      workout.routines.foldLeft(m) { (m, routine) =>
        m + ((routine.exercise, m.getOrElse(routine.exercise, Nil) :+ routine))
      }
    }
  }

  def routineHistory(exercise: Exercise): Iterable[(DateTime, Routine)] = {
    workouts.flatMap { w => w.routinesForExercise(exercise).map { r => (w.date, r) }}
  }
}

object History {
  implicit val codec = scala.io.Codec.ISO8859

  def fromCSV(data: URL): History = {
    case class Row(date: String, exerciseName: String, reps: Int, weight: Option[Double] = None)
    implicit val rowDecoder = RowDecoder.decoder4(Row.apply)(0, 1, 2, 3)

    val results = data.asCsvReader[Row](',', header = true).map { w => w.get }.toList

    val workouts =
      results.groupBy(_.date).map { case (date, rows) =>
        val routines =
          rows.groupBy(_.exerciseName).map { case (exerciseName, rows) =>
            Routine(Exercise(exerciseName), rows.map { r => RepSet(r.reps, r.weight) })
          }

        Workout(routines.toSeq, new DateTime(date))
      }

    History(workouts)
  }
}

