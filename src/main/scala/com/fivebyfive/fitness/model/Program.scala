package com.fivebyfive.fitness.model

import com.fivebyfive.fitness.strategy.routine.{HypertrophicBuilder, RoutineBuilder}
import com.fivebyfive.fitness.strategy.scoring.Scoring
import com.fivebyfive.fitness.strategy.volume.VolumePredictor
import org.joda.time.DateTime

case class Program(
    scoring: Scoring,
    goal: Option[Goal] = None,
    settings: WorkoutSettings
) {
  def nextWorkout(
      date: DateTime,
      history: History,
      routineBuilder: RoutineBuilder = new HypertrophicBuilder,
      seed: Option[Long] = None
  ): Option[ScoredWorkout] = {
    val routineHistory = history.volumeHistoryByExercise

    def generateWorkout(exerciseList: Seq[Exercise]): Option[Workout] = {
      var currentRoutines = List[Routine]()

      exerciseList.foreach { exercise =>
        val latestRoutineForExercise = history.latestRoutine(exercise).getOrElse(defaultRoutine(exercise))

        val data = routineHistory.getOrElse(exercise, Seq((date, latestRoutineForExercise.volume)))
        val nextVolume = VolumePredictor.next(data, date)

        val nextRoutine = routineBuilder.buildRoutine(latestRoutineForExercise, nextVolume)
        currentRoutines :+= nextRoutine

        if (currentRoutines.map(_.duration).sum > settings.targetDuration.toMinutes) {
          return Some(Workout(currentRoutines, date))
        }
      }

      None
    }

    def scoreWorkout(workout: Workout): ScoredWorkout = {
      scoring.run(history, workout)
    }

    val exercises = Exercise.randomPermutations(settings.iterations, seed)

    val scoredWorkouts =
      exercises
      .flatMap(generateWorkout)
      .map(scoreWorkout)

     scoredWorkouts
      .sortBy(_.totalScore)(Ordering[Double].reverse)
      .headOption
  }

  def defaultRoutine(exercise: Exercise): Routine = {
    Routine(exercise, Seq(RepSet(8, Some(5)), RepSet(8, Some(5))))
  }
}

