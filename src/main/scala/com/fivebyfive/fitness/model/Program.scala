package com.fivebyfive.fitness.model

import com.fivebyfive.fitness.strategy.routine.{MidRepBuilder, RoutineBuilder}
import com.fivebyfive.fitness.strategy.volume.VolumeGenerator
import org.joda.time.DateTime

case class Program(goal: Option[Goal] = None) {

  def nextWorkout(
      date: DateTime,
      history: History,
      routineBuilder: RoutineBuilder = new MidRepBuilder
  ): Workout = {
    val exercises = history.byExercise.keys
    val volumeGenerator = new VolumeGenerator(history, goal)

    val routines =
      exercises.map { e =>
        val newVolume = volumeGenerator.nextVolume(e, date)
        val lastRoutine = history.byExercise(e).last
        routineBuilder.buildRoutine(lastRoutine, newVolume)
      }

    Workout(routines.toSeq, date)
  }
}

