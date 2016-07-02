package com.fivebyfive.fitness.strategy.scoring.algorithms

import com.fivebyfive.fitness.model.{History, Muscle, Workout}
import com.fivebyfive.fitness.strategy.scoring.{Score, ScoringAlgorithm}

case class IgnoreMuscles(ignoredMuscles: Seq[Muscle]) extends ScoringAlgorithm {
  def score(history: History, workout: Workout): Score = {
    val unfilteredExercises =
      workout.exercises.filterNot { exercise =>
        ignoredMuscles.exists { ignoredMuscle =>
          exercise.muscleMap.keys.toSeq.contains(ignoredMuscle)
        }
      }

    Score(this, unfilteredExercises.size.toDouble / workout.exercises.size)
  }
}
