package com.fivebyfive.fitness.strategy.scoring.algorithms

import com.fivebyfive.fitness.model.{Exercise, History, Workout}
import com.fivebyfive.fitness.strategy.scoring.{Score, ScoringAlgorithm}

case class IgnoreExercises(ignoredExercises: Seq[Exercise]) extends ScoringAlgorithm {
  def score(history: History, workout: Workout): Score = {
    val unfilteredExercises =
      workout.exercises.filterNot { exercise =>
        ignoredExercises.contains(exercise)
      }

    Score(this, unfilteredExercises.size.toDouble / workout.exercises.size)
  }
}
