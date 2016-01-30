package com.fivebyfive.fitness.strategy

import com.fivebyfive.fitness.model.{Routine, RepSet}

trait RoutineGenerator {
  def toHalf(x: Double) = {
    Math.round(x * 2.0) / 2.0
  }
}

/**
  * Calculates the next routine by picking the next linear step between
  * the last set for each routine and the final set.
  */
object LinearSimple extends RoutineGenerator {
  def nextRoutine(routineHistory: Seq[Routine], targetRoutine: Routine, increments: Int): Routine = {
    val startRoutine = routineHistory.last

    val newSets =
      targetRoutine.sets.zip(startRoutine.sets).map { case (endSet, startSet) =>
        nextSet(startSet, endSet, increments)
      }
    Routine(targetRoutine.exercise, newSets.toSeq)
  }

  def nextSet(startSet: RepSet, endSet: RepSet, increments: Int): RepSet = {
    if (increments == 0) {
      endSet
    } else {
      val repIncrement = (endSet.reps - startSet.reps) / increments
      val newReps = startSet.reps + repIncrement

      val newWeight =
        endSet.weight.map { endWeight =>
          val lastWeight = startSet.weight.getOrElse(0.0)
          val weightIncrement = (endWeight - lastWeight) / increments

          toHalf(lastWeight + weightIncrement)
        }

      RepSet(newReps, newWeight)
    }
  }

}
