package com.fivebyfive.fitness.strategy.routine

import com.fivebyfive.fitness.Util
import com.fivebyfive.fitness.model.{Muscle, RepSet, Routine}
import com.typesafe.scalalogging.LazyLogging

trait RoutineBuilder extends LazyLogging {
  import Util._

  def numSets: Int
  def numReps: Int

  def buildRoutine(previousRoutine: Routine, volume: Double): Routine = {
    val (exercise, adjustedVolume) =
      if (volume > 0 && previousRoutine.inverted) {
        previousRoutine.inversion.map((_, volume)).getOrElse {
          logger.info("Volume greater than zero for exercise without non inverted version")
          (previousRoutine.exercise, 0.0)
        }
      } else if (volume < 0 && !previousRoutine.inverted) {
        previousRoutine.inversion.map(e => (e, volume)).getOrElse {
          logger.info("Volume greater less than zero for exercise without inverted version")
          (previousRoutine.exercise, 0.0)
        }
      } else {
        (previousRoutine.exercise, volume)
      }

    val sets =
      if (previousRoutine.weightedRoutine && adjustedVolume.abs > 0) {
        val weight = adjustedVolume / numSets / numReps

        Range(0, numSets).map { s => RepSet(numReps, Some(toHalf(weight))) }
      } else {
        val reps = toWhole(adjustedVolume / numSets)
        Range(0, numSets).map { s => RepSet(reps, None) }
      }

    Routine(exercise, sets)
  }
}
