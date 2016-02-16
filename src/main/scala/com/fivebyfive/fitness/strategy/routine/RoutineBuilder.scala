package com.fivebyfive.fitness.strategy.routine

import com.fivebyfive.fitness.Util
import com.fivebyfive.fitness.model.{Muscle, RepSet, Routine}

trait RoutineBuilder {
  import Util._

  def numSets: Int
  def numReps: Int

  def buildRoutine(previousRoutine: Routine, volume: Double): Routine = {
    val sets =
      if (previousRoutine.weightedRoutine && volume > 0) {
        val weight = volume / numSets / numReps

        Range(0, numSets).map { s => RepSet(numReps, Some(toHalf(weight))) }
      } else {
        val reps = toWhole(volume / numSets)
        Range(0, numSets).map { s => RepSet(reps, None) }
      }

    Routine(previousRoutine.exercise, sets)
  }
}
