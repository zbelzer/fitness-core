package com.fivebyfive.fitness.model

import com.fivebyfive.fitness.strategy.routine.{MidRepBuilder, RoutineBuilder}
import com.fivebyfive.fitness.strategy.volume.VolumePredictor
import org.joda.time.DateTime

case class Program(goal: Option[Goal] = None) {
  val MAX_PERMUTATIONS = 10
  val maxWorkoutDuration = 60 * 30

  def nextWorkout(
      date: DateTime,
      history: History,
      routineBuilder: RoutineBuilder = new MidRepBuilder
  ): Option[Workout] = {
    val routineHistory = history.volumeHistoryByExercise

    def generateWorkout(exerciseList: Seq[Exercise]): Option[Workout] = {
      var currentRoutines = List[Routine]()

      exerciseList.foreach { exercise =>
        val latestRoutineForExercise = history.latestRoutine(exercise).getOrElse(defaultRoutine(exercise))

        val data = routineHistory.getOrElse(exercise, Seq((date, latestRoutineForExercise.volume)))
        println(s"Data for $exercise is $data")
        val nextVolume = VolumePredictor.next(data, date)
        println(s"Next volume for $date is $nextVolume")

        val nextRoutine = routineBuilder.buildRoutine(latestRoutineForExercise, nextVolume)
        currentRoutines :+= nextRoutine

        if (currentRoutines.map(_.duration).sum > maxWorkoutDuration) {
          return Some(Workout(currentRoutines, date))
        }
      }

      None
    }

    Exercise.
      randomPermutations(MAX_PERMUTATIONS)
      .flatMap(generateWorkout)
      .map(scoreWorkout)
      .toSeq
      .sortBy(_._2)
      .headOption
      .map(_._1)
  }

  def scoreWorkout(workout: Workout) = {
    workout -> 1
  }

  def defaultRoutine(exercise: Exercise): Routine = {
    Routine(exercise, Seq(RepSet(8, Some(5)), RepSet(8, Some(5))))
  }
}

