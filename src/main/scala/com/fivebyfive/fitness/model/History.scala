package com.fivebyfive.fitness.model

import java.net.URL

import com.github.nscala_time.time.OrderingImplicits.DateTimeOrdering
import org.joda.time.DateTime
import tabulate._
import tabulate.ops._

case class History(workouts: Iterable[Workout]) {
  val sortedWorkouts = workouts.toSeq.sortBy(_.date)

  lazy val routinesByExercise: Map[Exercise, Iterable[Routine]] = {
    mapByExercise { case (workout, routine) => routine }
  }

  lazy val latestExercises: Map[Exercise, DateTime] = {
    val datesForExercises = mapByExercise { case (workout, routine) => workout.date }
    datesForExercises.map { case (exercise, dates) => exercise -> dates.toList.sorted.head }
  }

  lazy val volumeHistoryByExercise: Map[Exercise, Iterable[(DateTime, Double)]] = {
    mapByExercise { case (workout, routine) => (workout.date, routine.volume) }
  }

  def latestRoutine(exercise: Exercise): Option[Routine] = {
    routinesByExercise.get(exercise).map(_.head)
  }

  def routineHistory(exercise: Exercise): Iterable[(DateTime, Routine)] = {
    sortedWorkouts.flatMap { w => w.routineForExercise(exercise).map { r => (w.date, r) }}
  }

  lazy val volumeHistoryByMuscle = {
    sortedWorkouts.foldLeft(Map[Muscle, Seq[(DateTime, Double)]]()) { case(memo, workout) =>
      workout.volumeByMuscle.map { case(m, volume) =>
        m -> (memo.getOrElse(m, Nil) :+ (workout.date, volume))
      }
    }
  }

  private[this] def mapByExercise[T](f: (Workout, Routine) => T): Map[Exercise, Iterable[T]] = {
    sortedWorkouts.foldLeft(Map[Exercise, Seq[T]]()) { (m, workout) =>
      workout.routines.foldLeft(m) { (m, routine) =>
        m + ((routine.exercise, m.getOrElse(routine.exercise, Nil) :+ f(workout, routine)))
      }
    }
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
            val name = exerciseName.replaceAll(" ", "")
            Routine(Exercise.withNameInsensitive(name), rows.map { r => RepSet(r.reps, r.weight) })
          }

        Workout(routines.toSeq, new DateTime(date))
      }

    History(workouts)
  }
}

