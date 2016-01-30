package com.fivebyfive.fitness.model

import com.fivebyfive.fitness.strategy.{RoutineGenerator, LinearSimple}
import org.joda.time.{DateTime, Days}

case class Program(goal: Option[Goal] = None) {
  def nextWorkout(date: DateTime, history: History): Workout = {
    val routines = history.byExercise.flatMap { case (e, h) => generateRoutine(date, e, h) }
    Workout(routines.toSeq, date)
  }

  def generateRoutine(date: DateTime, exercise: Exercise, routineHistory: Seq[Routine]): Option[Routine] = {
    goal.map { g =>
      val daysLeft = Days.daysBetween(date, g.date).getDays

      g.workout.routinesForExercise(exercise).map { goalRoutine =>
        LinearSimple.nextRoutine(routineHistory, goalRoutine, daysLeft)
      }.headOption
    }.getOrElse(None)
  }
}

